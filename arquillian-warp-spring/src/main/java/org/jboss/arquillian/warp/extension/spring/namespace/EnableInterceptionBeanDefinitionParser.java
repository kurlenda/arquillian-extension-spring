package org.jboss.arquillian.warp.extension.spring.namespace;

import org.jboss.arquillian.warp.extension.spring.interceptor.WarpInterceptor;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.xml.AbstractBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.w3c.dom.Element;

public class EnableInterceptionBeanDefinitionParser extends AbstractBeanDefinitionParser {

    @Override
    protected AbstractBeanDefinition parseInternal(Element element, ParserContext parserContext) {
        element.setAttribute("id", "warpInterceptors");
        BeanDefinitionBuilder factory = BeanDefinitionBuilder.rootBeanDefinition(SimpleUrlHandlerMapping.class);

        ManagedList managedList = new ManagedList(1);
        BeanDefinitionBuilder beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(WarpInterceptor.class);
        managedList.add(beanDefinition.getBeanDefinition());
        factory.addPropertyValue("interceptors", managedList);
        return factory.getBeanDefinition();
    }
}
