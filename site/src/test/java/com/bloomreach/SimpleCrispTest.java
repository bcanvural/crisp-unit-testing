package com.bloomreach;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.onehippo.cms7.crisp.api.resource.Resource;
import org.onehippo.cms7.crisp.core.resource.jackson.SimpleJacksonRestTemplateResourceResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.base.Charsets;
import com.google.common.io.Resources;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;

@RunWith(SpringJUnit4ClassRunner.class)
public class SimpleCrispTest extends AbstractCrispTest {

    private static final MockWebServer MOCK_WEB_SERVER = new MockWebServer();
    private String BASE_URL;
    private static final String SERVICE_PATH = "/people";
    private static final String SERVICE_RESOURCE_PATH = "mock-responses/people.json";

    @BeforeClass
    public static void setUpClass() throws Exception {
        MOCK_WEB_SERVER.setDispatcher(new LocalResourceDispatcher());
        MOCK_WEB_SERVER.start();
    }

    @After
    public void tearDown(){

    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        MOCK_WEB_SERVER.shutdown();
    }

    @Before
    public void setUp(){
        BASE_URL = "http://localhost:" + MOCK_WEB_SERVER.getPort();
    }

    @Test
    public void peopleTest() {
        SimpleJacksonRestTemplateResourceResolver resourceResolver = applicationContext.getBean(SimpleJacksonRestTemplateResourceResolver.class);
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
                    return new MockResponse().setBody(Resources.toString(locationsResource, Charsets.UTF_8));
                } catch (IOException e) {
                    throw new InterruptedException(e.getMessage());
                }
            }
            return null;
        }
    }
}