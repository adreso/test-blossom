from aws_cdk import (
    Stack,
    aws_codepipeline as codepipeline,
    aws_ecr as ecr,
    Stage
)

from aws_cdk.pipelines import (
    CodePipeline,
    ShellStep,
    CodePipelineSource,
    CodeBuildStep
)

from constructs import Construct


class BuildAndPushStack(Stack):
    def __init__(self, scope: Construct, id: str, **kwargs):
        super().__init__(scope, id, **kwargs)

        self.repository = ecr.Repository(self, "test-blossom-ecr", repository_name="test-blossom-ecr")

        self.build_and_push_step = ShellStep(
            "BuildAndPush",
            commands=[
                "docker build -t test-blossom-image .",
                f"docker tag test-blossom-image:latest {self.repository.repository_uri}:latest",
                f"docker push {self.repository.repository_uri}:latest"
            ],
            env={
                'AWS_DEFAULT_REGION': self.region,
                'AWS_ACCOUNT_ID': self.account,
                'ECR_REPOSITORY': self.repository.repository_name,
                'IMAGE_TAG': 'latest'
            }
        )


class BuildAndPushStage(Stage):
    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        self.build_and_push_stack = BuildAndPushStack(self, "BuildAndPushStack")
