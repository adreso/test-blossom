from aws_cdk import (
    Stack,
    aws_codepipeline as codepipeline,
    aws_ecr as ecr,
    Stage,
    aws_codebuild as codebuild,
    SecretValue
)
from constructs import Construct
from aws_cdk.pipelines import (
    CodePipeline,
    ShellStep,
    CodePipelineSource,
)
from aws_cdk.aws_codepipeline import PipelineType
from .build_stage import BuildAndPushStage
from aws_cdk.aws_codebuild import LocalCacheMode
from aws_cdk.aws_iam import ManagedPolicy
from aws_cdk.aws_codepipeline import (Artifact, Pipeline)
from aws_cdk.aws_codepipeline_actions import (
    GitHubSourceAction,
    CodeBuildAction,
    EcsDeployAction
)


class PipelineStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        source = CodePipelineSource.git_hub(repo_string="adreso/test-blossom", branch="main")
        oauth_token: SecretValue = SecretValue.secrets_manager('github/oauth/token')

        code_pipeline = codepipeline.Pipeline(
            self, "Pipeline",
            pipeline_name="test-pipeline-blossom",
            cross_account_keys=False,
            pipeline_type=PipelineType.V2,
        )

        synth_step = ShellStep(
            id="Synth",
            commands=["cd pipeline", "pip install -r requirements.txt", "npx cdk synth"],
            input=source,
            primary_output_directory="pipeline/cdk.out"
        )

        pipeline = CodePipeline(
            self, "pipeline-Code",
            synth=synth_step,
            self_mutation=True,
            code_pipeline=code_pipeline,
        )

        repoName = "test-blossom-ecr"
        self.repository = ecr.Repository(self, repoName, repository_name=repoName)

        pipeline_project = self.PipelineProject(self.repository, repoName)
        pipeline_project.role.add_managed_policy(
            ManagedPolicy.from_aws_managed_policy_name("AmazonEC2ContainerRegistryPowerUser"))

        sourceOutput = Artifact()
        buildOutput = Artifact()

        github_source_action = self.CreateGithubSourceAction(sourceOutput, oauth_token)

        pipeline.add_stage(
            Stage()
        )

        pipeline_project_action = Pipeline(
            self, "PipelineProjectAction",
            pipeline_name="PipelineProjectAction",
            stages=[{
                "stage_name": "Build",
                "actions": [github_source_action]
            }]
        )

    def PipelineProject(self, repository: ecr.Repository, repoName) -> codebuild.PipelineProject:
        build_spec = codebuild.PipelineProject(
            self,
            id="BuildSpec",
            environment=codebuild.BuildEnvironment(
                build_image=codebuild.LinuxBuildImage.AMAZON_LINUX_2_5,

                privileged=True
            ),
            environment_variables={
                "ECR_REPOSITORY": {
                    "value": repository.repository_name + ":latest"

                }
            },
            build_spec=codebuild.BuildSpec.from_object(
                {
                    "version": "0.2",
                    "phases": {
                        "install": {
                            "commands": [
                                "#apt-get update -y",
                            ],
                            "finally": [
                                "echo 'This is the install phase'"
                            ]
                        },
                        "pre_build": {
                            "commands": [
                                'echo Logging in to Amazon ECR...',
                                '$(aws ecr get-login --no-include-email)',
                                'COMMIT_HASH=$(echo $CODEBUILD_RESOLVED_SOURCE_VERSION | cut -c 1-7)',
                                'IMAGE_TAG=${COMMIT_HASH:=latest}'
                            ],
                        },
                        "build": {
                            "commands": [
                                'echo Build started on `date`',
                                './gradlew bootJar',
                                'echo Building Docker Image $ECR_REPO:latest',
                                'docker build -f docker/Dockerfile -t $ECR_REPO:latest .',
                                'echo Tagging Docker Image $ECR_REPO:latest with $ECR_REPO:$IMAGE_TAG',
                                'docker tag $ECR_REPO:latest $ECR_REPO:$IMAGE_TAG',
                                'echo Pushing Docker Image to $ECR_REPO:latest and $ECR_REPO:$IMAGE_TAG',
                                'docker push $ECR_REPO:latest',
                                'docker push $ECR_REPO:$IMAGE_TAG'
                            ],
                            "finally": [
                                "echo 'This is the build phase'"
                            ]
                        },
                        "post_build": {
                            "commands": [
                                "echo creating imagedefinitions.json dynamically",
                                "printf '[{\"name\":\"" + repoName + "\",\"imageUri\": \"" + self.repository.repository_uri_for_tag() + ":latest\"}]' > imagedefinitions.json",
                                "echo Build completed on `date`"
                            ]
                        }
                    },
                    "artifacts": {
                        "files": [
                            "imagedefinitions.json"
                        ]
                    },
                    "cache": {
                        "paths": [
                            "/root/.gradle/caches",
                            "/root/.gradle/wrapper"
                        ]
                    }
                },

            ),
            cache=codebuild.Cache.local(LocalCacheMode.DOCKER_LAYER, LocalCacheMode.CUSTOM),
        )
        return build_spec

    def CreateGithubSourceAction(self, sourceOutput: Artifact, oauthToken: SecretValue) -> GitHubSourceAction:
        return GitHubSourceAction(
            action_name="GitHub_Source",
            owner="adreso",
            repo="test-blossom",
            oauth_token=oauthToken,
            output=sourceOutput,
            branch="main",
        )
