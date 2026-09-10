package com.tutorials.sid.spring.sqs;

import com.amazonaws.services.lambda.runtime.events.SQSEvent;

/**
 * @author kunmu On 10-09-2026
 */
public class ClaimManagementLamdba {
    public void handler(SQSEvent event) {
        event.getRecords().forEach(message -> {
            System.out.println(message.getBody());
        });
    }
}
