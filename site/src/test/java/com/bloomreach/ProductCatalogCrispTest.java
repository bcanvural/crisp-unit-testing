package com.bloomreach;

import java.io.IOException;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.core.resource.jdom.SimpleJdomRestTemplateResourceResolver;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

public class ProductCatalogCrispTest extends AbstractCrispTest {

    private static final MockWebServer MOCK_WEB_SERVER = new MockWebServer();
    private String BASE_URL;
    private static final String SERVICE_PATH = "/products";
    private static final String SERVICE_RESOURCE_PATH = "mock-responses/products.xml";
    private static final String RESOURCE_RESOLVER_NAME = "productCatalogResourceResolver";

    @BeforeClass
    public static void setUpClass() throws Exception {
        MOCK_WEB_SERVER.setDispatcher(new ProductCatalogCrispTest.LocalResourceDispatcher());
        MOCK_WEB_SERVER.start();
    }

    @Before
    public void setUp() {
        BASE_URL = "http://localhost:" + MOCK_WEB_SERVER.getPort();
    }


    @Test
    public void test() {
        SimpleJdomRestTemplateResourceResolver resourceResolver = applicationContext.getBean(RESOURCE_RESOLVER_NAME, SimpleJdomRestTemplateResourceResolver.class);
        resourceResolver.setBaseUri(BASE_URL);
        Resource resources = resourceResolver.findResources(SERVICE_PATH);
        Assert.assertNotNull(resources);
        Assert.assertTrue(resources.getChildCount() > 0);
    }

    protected static class LocalResourceDispatcher extends Dispatcher {

        @Override
        public MockResponse dispatch(RecordedRequest request) throws InterruptedException {
            if (SERVICE_PATH.equalsIgnoreCase(request.getPath())) {
                URL locationsResource = Resources.getResource(SERVICE_RESOURCE_PATH);
                try {
                    MockResponse mockResponse = new MockResponse();
                    return mockResponse.setBody(Resources.toString(locationsResource, Charsets.UTF_8));
                } catch (IOException e) {
                    throw new InterruptedException(e.getMessage());
                }
            }
            return null;
        }
    }
}
