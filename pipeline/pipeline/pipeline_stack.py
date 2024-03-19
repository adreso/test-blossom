from aws_cdk import (
    Stack,
    aws_codepipeline as codepipeline,
    aws_ecr as ecr,
    Stage
)
from constructs import Construct
from aws_cdk.pipelines import (
    CodePipeline,
    ShellStep,
    CodePipelineSource,
)
from aws_cdk.aws_codepipeline import PipelineType
from .build_stage import BuildAndPushStage


class PipelineStack(Stack):

    def __init__(self, scope: Construct, construct_id: str, **kwargs) -> None:
        super().__init__(scope, construct_id, **kwargs)

        source = CodePipelineSource.git_hub(

            repo_string="adreso/test-blossom",
            branch="main"

        )

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

        build_and_push_stage = BuildAndPushStage(self, "BuildAndPushStage")

        pipeline.add_stage(build_and_push_stage)


