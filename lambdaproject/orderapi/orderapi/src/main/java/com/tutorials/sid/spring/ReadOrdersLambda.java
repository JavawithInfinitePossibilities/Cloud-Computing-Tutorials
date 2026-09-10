package com.tutorials.sid.spring;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorials.sid.spring.dto.Order;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author kunmu On 10-09-2026
 */
public class ReadOrdersLambda {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final DynamoDbClient dynamoDbClient = DynamoDbClient.create();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();

    public APIGatewayProxyResponseEvent getOrders(APIGatewayProxyRequestEvent request)
            throws Exception {

        DynamoDbTable<Order> table = enhancedClient.table(
                System.getenv("ORDERS_TABLE"),
                TableSchema.fromBean(Order.class)
        );

        // Equivalent to ScanRequest — scans all items in the table
        List<Order> orders = table.scan().items().stream()
                .collect(Collectors.toList());

        String jsonOutput = objectMapper.writeValueAsString(orders);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody(jsonOutput);
    }
}
