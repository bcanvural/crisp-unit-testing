# Unit Testing with CRISP API

* Tests are at site/src/test
* AbstractCrispTest class is the base testing class

### Testing the resource resolver defined in your Hippo project:

* See com.bloomreach.MarketoCrispTest for a minimal setup
* Copy paste from 
/hippo:configuration/hippo:modules/crispregistry/hippo:moduleconfig/crisp:resourceresolvercontainer/<your_resource_resolver_name>/@crisp:beandefinition
to an xml file at src/main/test/resources/crisp-resource-resolvers/<your_bean_definition_in_console>.xml

* If you have more than one resource resolver xml definitions, give each of them a unique id so you can fetch it with @Qualifier annotation. Otherwise Spring does not know which bean to autowire since there are multiple of same type.

* Then you can fetch your resource resolver beans like:
```java
 @Autowired
 @Qualifier(RESOURCE_RESOLVER_NAME)
 SimpleJacksonRestTemplateResourceResolver marketoResourceResolver;
```

* It is also possible to autowire the Spring application context like:

```java
@Autowired
ApplicationContext applicationContext;
```
