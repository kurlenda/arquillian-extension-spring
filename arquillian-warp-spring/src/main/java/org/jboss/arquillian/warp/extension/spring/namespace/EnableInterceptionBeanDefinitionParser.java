/**
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.warp.extension.spring.namespace;

import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * {@link org.springframework.beans.factory.xml.BeanDefinitionParser} that parses a {@code enable-interception} element to register
 * {@link org.jboss.arquillian.warp.extension.spring.namespace.WarpInterceptorInitializingBean}
 *
 * @author <a href="mailto:kurlenda@gmail.com">Jakub Kurlenda</a>
 *
 */
public class EnableInterceptionBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    public static final String WARP_INTERCEPTOR_INITIALIZER_BEAN_NAME = "warpInterceptorInitializingBean";

    public static final String WARP_INTERCEPTOR_INITIALIZER_HANDLERS_ATTRIBUTE_NAME = "handlers";

    @Override
    protected Class getBeanClass(Element element) {
        return WarpInterceptorInitializingBean.class;
    }

    @Override
    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        element.setAttribute(ID_ATTRIBUTE, WARP_INTERCEPTOR_INITIALIZER_BEAN_NAME);

        if (element.hasAttribute(WARP_INTERCEPTOR_INITIALIZER_HANDLERS_ATTRIBUTE_NAME)) {
            String handlers = element.getAttribute(WARP_INTERCEPTOR_INITIALIZER_HANDLERS_ATTRIBUTE_NAME);
            bean.addPropertyValue("interceptedHandlerNames", handlers.split(","));
        }
    }
}
