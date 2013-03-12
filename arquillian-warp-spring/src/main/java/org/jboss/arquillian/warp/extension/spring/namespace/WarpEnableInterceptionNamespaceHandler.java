package org.jboss.arquillian.warp.extension.spring.namespace;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class WarpEnableInterceptionNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("enable-interception", new EnableInterceptionBeanDefinitionParser());
    }
}
