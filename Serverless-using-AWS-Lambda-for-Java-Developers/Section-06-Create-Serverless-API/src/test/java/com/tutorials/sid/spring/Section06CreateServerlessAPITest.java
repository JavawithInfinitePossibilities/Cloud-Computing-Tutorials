package com.tutorials.sid.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kunmu On 10-09-2026
 */
@SpringBootTest(classes = Section06CreateServerlessAPI.class)
class Section06CreateServerlessAPITest {
    @Test
    void contextLoads() {
        System.out.println("Spring Boot Application Started Successfully");
    }
}