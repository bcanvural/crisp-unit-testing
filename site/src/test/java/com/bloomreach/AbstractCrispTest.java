package com.bloomreach;

import org.junit.runner.RunWith;
import org.onehippo.cms7.crisp.api.resource.ResourceResolverProvider;
import org.onehippo.cms7.crisp.core.resource.MapResourceResolverProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AbstractCrispTest.TestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public abstract class AbstractCrispTest {

    @Autowired
    ApplicationContext applicationContext;

    @Configuration
    @ComponentScan(basePackageClasses = AppConfig.class)
    @ImportResource("classpath:crisp-spring-assembly/*.xml")
    public static class TestConfiguration {

        @Bean("resourceResolverProvider")
        public ResourceResolverProvider getResourceResolverProvider() {
            return new MapResourceResolverProvider();
        }

        @Bean("ehCacheManager")
        public EhCacheManagerFactoryBean getEhCacheManagerFactoryBean() {
            return new EhCacheManagerFactoryBean();
        }
    }
}