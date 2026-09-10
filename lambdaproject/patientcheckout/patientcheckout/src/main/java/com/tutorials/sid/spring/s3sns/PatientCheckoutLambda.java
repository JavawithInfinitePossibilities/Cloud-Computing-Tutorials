package com.tutorials.sid.spring.s3sns;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author kunmu On 10-09-2026
 */
public class PatientCheckoutLambda {
    private static final String PATIENT_CHECKOUT_TOPIC = System.getenv("PATIENT_CHECKOUT_TOPIC");
    private final S3Client s3 = S3Client.create();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final SnsClient sns = SnsClient.create();

    public void handler(S3Event event, Context context) {
        Logger logger = LoggerFactory.getLogger(PatientCheckoutLambda.class);

        event.getRecords().forEach(record -> {
            // SDK v2: use GetObjectRequest and ResponseInputStream
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(record.getS3().getBucket().getName())
                    .key(record.getS3().getObject().getKey())
                    .build();
            ResponseInputStream<GetObjectResponse> s3InputStream = s3.getObject(getObjectRequest);
            try {
                logger.info("Reading data from s3");
                List<PatientCheckoutEvent> patientCheckoutEvents = Arrays
                        .asList(objectMapper.readValue(s3InputStream, PatientCheckoutEvent[].class));
                logger.info(patientCheckoutEvents.toString());
                s3InputStream.close();
                logger.info("Message being published to SNS");
                publishMessageToSNS(patientCheckoutEvents);
            } catch (IOException e) {
                logger.error("Exception while processing S3 event:", e);
                throw new RuntimeException("Error while processing S3 event", e);
            }
        });
    }

    private void publishMessageToSNS(List<PatientCheckoutEvent> patientCheckoutEvents) {
        patientCheckoutEvents.forEach(checkoutEvent -> {
            try {
                // SDK v2: use PublishRequest builder instead of sns.publish(topic, message)
                PublishRequest publishRequest = PublishRequest.builder()
                        .topicArn(PATIENT_CHECKOUT_TOPIC)
                        .message(objectMapper.writeValueAsString(checkoutEvent))
                        .build();
                sns.publish(publishRequest);
            } catch (JsonProcessingException e) {
                throw new RuntimeException("Error serializing checkout event to JSON", e);
            }
        });
    }
}
