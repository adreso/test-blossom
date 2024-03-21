package co.com.blossom.configs.security.services;

import co.com.blossom.configs.security.model.TokenDetail;
import com.google.gson.Gson;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class JWTProcessorImpl implements JWTProcessor {

    @Value("${security.lambda-arn:}")
    private String functionName;

    @Value("${cloud.aws.region.static:}")
    private String region;

    private final Gson gson = new Gson();

    @Override
    public TokenDetail deserializeToken(String token) {
//        Payload payLoad = new Payload();
//        payLoad.setToken(token);
//
//        String inputJSON = gson.toJson(payLoad);
//
//        InvokeRequest lmbRequest = new InvokeRequest()
//                .withFunctionName(functionName)
//                .withPayload(inputJSON);
//
//        lmbRequest.setInvocationType(InvocationType.RequestResponse);
//
//        AWSLambda lambda = AWSLambdaClientBuilder.standard().withRegion(region).build();
//
//        InvokeResult lmbResult = lambda.invoke(lmbRequest);
//        String outputJSON = new String(lmbResult.getPayload().array(), StandardCharsets.UTF_8);
//        ResponsePayload response = gson.fromJson(outputJSON, ResponsePayload.class);
//
//        if(response.isError()) {
//            throw new RuntimeException(response.getMessage());
//        }

        TokenDetail tokenDetail = TokenDetail.builder().build();
        ResponsePayload response = new ResponsePayload();
        response.setData(new Data());

        if(response.getData().getRoles() != null && !response.getData().getRoles().isEmpty()) {
            tokenDetail.setRoles(Arrays.asList(response.getData().getRoles().split(",")));
        }

        return tokenDetail;
    }

    @Setter
    @Getter
    private static class Payload {
        private String token;
        private String clientid;
    }

    @Setter
    @Getter
    private static class ResponsePayload {
        private boolean error;
        private String message;
        private Data data;
    }

    @Setter
    @Getter
    private static class Data {
        private String roles;
    }
}
