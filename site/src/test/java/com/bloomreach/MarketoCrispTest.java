package com.bloomreach;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration(classes = MarketoCrispTest.TestConfiguration.class)
public class MarketoCrispTest extends AbstractCrispTest {

    @Configuration
    @ImportResource("classpath:crisp-resource-resolvers/marketo.xml")
    public static class TestConfiguration {

    }

}
