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

import org.jboss.arquillian.warp.extension.spring.interceptor.WarpInterceptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.*;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.AbstractUrlHandlerMapping;

import javax.faces.bean.ReferencedBean;
import java.util.Map;

/**
 * Class responsible for setting Warp interception on defined Url Handler Mappings.
 *
 * By default, all instances of {@link org.springframework.web.servlet.handler.AbstractUrlHandlerMapping} class will be intercepted.
 *
 * If interceptedHandlerNames array was specified, only selected handler mappings will be intercepted.
 *
 * @author <a href="mailto:kurlenda@gmail.com">Jakub Kurlenda</a>
 */
public class WarpInterceptorInitializingBean implements BeanFactoryPostProcessor {

    private ApplicationContext applicationContext;

    /**
     * Array containing names of beans of type {@link AbstractUrlHandlerMapping} that will be intercepted by WarpInterceptor.
     */
    private String[] interceptedHandlerNames;

    /**
     * Array with WarpInterceptor added to selected AbstractUrlHandlerMapping beans.
     *
     * {@see org.springframework.web.servlet.handler.AbstractHandlerMapping#setInterceptors()}
     */
    private final HandlerInterceptor[] WARP_INTERCEPTOR_ARRAY = new HandlerInterceptor[]{new WarpInterceptor()};

    private void setWarpInterceptorOnAllUrlHandlerMappings() {
        Map abstractHandlerMappings = applicationContext.getBeansOfType(AbstractUrlHandlerMapping.class);

        for (Object handlerMappingKey : abstractHandlerMappings.keySet()) {
            AbstractUrlHandlerMapping abstractUrlHandlerMapping = (AbstractUrlHandlerMapping) abstractHandlerMappings.get(handlerMappingKey);
            abstractUrlHandlerMapping.setInterceptors(WARP_INTERCEPTOR_ARRAY);
        }
    }

    private void setWarpInterceptorOnSelectedHandlerMappings() {
        for (String interceptedHandlerName : interceptedHandlerNames) {
            AbstractUrlHandlerMapping abstractUrlHandlerMapping = (AbstractUrlHandlerMapping) applicationContext.getBean(interceptedHandlerName,
                    AbstractUrlHandlerMapping.class);
            abstractUrlHandlerMapping.setInterceptors(WARP_INTERCEPTOR_ARRAY);
        }
    }

    private boolean shouldInterceptOnlySelectedHandlerMappings() {
        return interceptedHandlerNames != null && interceptedHandlerNames.length > 0;
    }

    public void setInterceptedHandlerNames(String[] interceptedHandlerNames) {
        this.interceptedHandlerNames = interceptedHandlerNames;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        configurableListableBeanFactory.registerSingleton("warpInterceptor", new WarpInterceptor());

        String[] beanNamesForType = configurableListableBeanFactory.getBeanNamesForType(AbstractUrlHandlerMapping.class);
        for (String beanName : beanNamesForType) {
            BeanDefinition beanDefinition = configurableListableBeanFactory.getBeanDefinition(beanName);
            ManagedList propertyValue = new ManagedList();
            RuntimeBeanReference runtimeBeanReference = new RuntimeBeanReference("warpInterceptor");
            propertyValue.add(runtimeBeanReference);
            beanDefinition.getPropertyValues().addPropertyValue("interceptors", propertyValue);
        }
    }
}
