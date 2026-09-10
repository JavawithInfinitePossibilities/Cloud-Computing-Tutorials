package com.tutorials.sid.spring.s3sns.errorhandling;

import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kunmu On 10-09-2026
 */
public class ErrorHandler {
    public void handler(SNSEvent event) {
        Logger logger = LoggerFactory.getLogger(ErrorHandler.class);
        event.getRecords().forEach(record -> logger.info("Dead Letter Queue Event" + record.toString()));
    }
}
