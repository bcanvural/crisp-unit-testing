package com.bloomreach;

import org.junit.Test;
import org.onehippo.cms7.crisp.core.resource.jackson.SimpleJacksonRestTemplateResourceResolver;
import org.springframework.util.Assert;

public class MarketoCrispTest extends AbstractCrispTest {

    private static final String RESOURCE_RESOLVER_NAME = "marketoResourceResolver";

    @Test
    public void test() {
        SimpleJacksonRestTemplateResourceResolver marketoResourceResolver = applicationContext.getBean(RESOURCE_RESOLVER_NAME, SimpleJacksonRestTemplateResourceResolver.class);
        Assert.notNull(marketoResourceResolver, "marketoResource was null");
    }

}
