package com.bloomreach;

import org.junit.Test;
import org.onehippo.cms7.crisp.core.resource.jackson.SimpleJacksonRestTemplateResourceResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.Assert;

public class MarketoCrispTest extends AbstractCrispTest {

    private static final String RESOURCE_RESOLVER_NAME = "marketoResourceResolver";

    @Autowired
    @Qualifier(RESOURCE_RESOLVER_NAME)
    SimpleJacksonRestTemplateResourceResolver marketoResourceResolver;

    @Test
    public void test() {
        Assert.notNull(marketoResourceResolver, "marketoResource was null");
    }

}
