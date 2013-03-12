package org.jboss.arquillian.warp.extension.spring.namespace;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import static org.fest.assertions.Assertions.assertThat;

public class WarpEnableInterceptionNamespaceHandlerTest {

    @Test
    public void test() {

        ClassPathXmlApplicationContext classPathXmlApplicationContext = new ClassPathXmlApplicationContext("classpath*:org/jboss/arquillian/warp/extension/spring/namespace/WarpEnableInterceptionNamespaceHandlerTest-context.xml");

        SimpleUrlHandlerMapping handlerMapping = (SimpleUrlHandlerMapping) classPathXmlApplicationContext.getBean("warpInterceptors");

        assertThat(handlerMapping).isNotNull();

    }
}
