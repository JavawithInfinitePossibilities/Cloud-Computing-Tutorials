package com.tutorials.sid.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kunmu On 10-09-2026
 */
@SpringBootTest(classes = Section07AsynchronousUsecase.class)
class Section07AsynchronousUsecaseTest {
    @Test
    void contextLoads() {
        System.out.println("context load");
    }
}