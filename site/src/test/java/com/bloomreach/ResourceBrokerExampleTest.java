package com.bloomreach;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onehippo.cms7.crisp.api.broker.ResourceServiceBroker;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.core.resource.MapResourceResolverProvider;
import org.onehippo.cms7.crisp.core.resource.jdom.SimpleJdomRestTemplateResourceResolver;
import org.onehippo.cms7.services.HippoServiceRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ResourceBrokerExampleTest extends AbstractCrispTest {

    private static final MockWebServer MOCK_WEB_SERVER = new MockWebServer();
    private String BASE_URL;
    private static final String SERVICE_RESOURCE_PATH = "mock-responses/products.xml";
    private static final String RESOURCE_RESOLVER_NAME = "productCatalogResourceResolver";

    @BeforeClass
    public static void setUpClass() throws Exception {
        MOCK_WEB_SERVER.setDispatcher(new LocalResourceDispatcher());
        MOCK_WEB_SERVER.start();
    }

    @Before
    public void setUp() {
        BASE_URL = "http://localhost:" + MOCK_WEB_SERVER.getPort();
        resourceResolver.setBaseUri(BASE_URL);
    }

    @Autowired
    @Qualifier(RESOURCE_RESOLVER_NAME)
    SimpleJdomRestTemplateResourceResolver resourceResolver;

    @PostConstruct
    public void postConstruct() {
        MapResourceResolverProvider mapResourceResolverProvider = (MapResourceResolverProvider) resourceResolverProvider;
        mapResourceResolverProvider.setResourceResolverMap(Collections.singletonMap(RESOURCE_RESOLVER_NAME, resourceResolver));
        HippoServiceRegistry.registerService(cacheableResourceServiceBroker, ResourceServiceBroker.class);
    }

    @PreDestroy
    public void preDestroy() {
        HippoServiceRegistry.unregisterService(cacheableResourceServiceBroker, ResourceServiceBroker.class);
    }

    @Test
    public void test() {
        ResourceServiceBroker resourceServiceBroker = HippoServiceRegistry.getService(ResourceServiceBroker.class);
        Resource resources = resourceServiceBroker.findResources(RESOURCE_RESOLVER_NAME, "");
        Assert.assertNotNull(resources);
        Assert.assertTrue(resources.getChildCount() > 0);
    }


    protected static class LocalResourceDispatcher extends Dispatcher {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            URL productsResource = Resources.getResource(SERVICE_RESOURCE_PATH);
            try {
                MockResponse mockResponse = new MockResponse();
                return mockResponse.setBody(Resources.toString(productsResource, Charsets.UTF_8));
            } catch (IOException e) {
                throw new InterruptedException(e.getMessage());
            }
        }
    }

}
