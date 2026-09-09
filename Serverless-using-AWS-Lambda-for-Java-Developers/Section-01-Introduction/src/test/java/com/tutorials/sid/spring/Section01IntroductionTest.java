package com.tutorials.sid.spring;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kunmu On 09-09-2026
 */
@SpringBootTest(classes = Section01Introduction.class)
class Section01IntroductionTest {
    @Test
    void contextLoad() {
        System.out.println("Hello world");
    }
}