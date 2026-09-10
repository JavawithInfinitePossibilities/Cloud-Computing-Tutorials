package com.tutorials.sid.spring;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tutorials.sid.spring.dto.Order;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;


/**
 * {
 *     "id": 1234567,
 *     "itemName": "Apple-Mobile",
 *     "quantity": 3
 * }
 * @author kunmu On 10-09-2026
 */
public class CreateOrderLambda {
    private final ObjectMapper objectMapper = new ObjectMapper();
    // SDK v2 clients — equivalent to your old AmazonDynamoDBClientBuilder.defaultClient()
    private final DynamoDbClient dynamoDbClient = DynamoDbClient.create();
    private final DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
            .dynamoDbClient(dynamoDbClient)
            .build();
    public APIGatewayProxyResponseEvent createOrder(APIGatewayProxyRequestEvent request)
            throws Exception {
        Order order = objectMapper.readValue(request.getBody(), Order.class);
        // Equivalent to dynamoDB.getTable(...)
        DynamoDbTable<Order> table = enhancedClient.table(
                System.getenv("ORDERS_TABLE"),
                TableSchema.fromBean(Order.class)
        );
        // Equivalent to table.putItem(item)
        table.putItem(order);
        return new APIGatewayProxyResponseEvent()
                .withStatusCode(200)
                .withBody("Order ID: " + order.getId());
    }
}
