from aws_cdk import (
    Stack,
    aws_codepipeline as codepipeline,
    aws_ecr as ecr,
    Stage,
    aws_codebuild as codebuild
)

from aws_cdk.pipelines import (
    CodePipeline,
    ShellStep,
    CodePipelineSource,
    CodeBuildStep,
)
from aws_cdk.aws_codepipeline_actions import CodeBuildAction
from aws_cdk.aws_codebuild import LocalCacheMode

from constructs import Construct


class BuildAndPushStack(Stack):
    def __init__(self, scope: Construct, id: str, **kwargs):
        super().__init__(scope, id, **kwargs)
        repoName = "test-blossom-ecr"

        self.repository = ecr.Repository(self, repoName, repository_name=repoName)

        self.build_spec = codebuild.PipelineProject(
            self, "BuildSpec",
            environment=codebuild.BuildEnvironment(
                build_image=codebuild.LinuxBuildImage.AMAZON_LINUX_2_5(),
                privileged=True
            ),
            environment_variables={
                "ECR_REPOSITORY": {
                    "value": self.repository.repository_name + ":latest"

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


class BuildAndPushStage(Stage):
    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        self.build_and_push_stack = BuildAndPushStack(self, "BuildAndPushStack")
