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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class WarpEnableInterceptionForAllHandlersTestCase {

    @Autowired
    WarpInterceptorInitializingBean warpInterceptorInitializingBean;

    @Autowired
    @Qualifier("urlHandlerMappingSpy")
    SimpleUrlHandlerMapping simpleUrlHandlerMapping;

    @Autowired
    @Qualifier("beanNameHandlerMappingSpy")
    BeanNameUrlHandlerMapping beanNameUrlHandlerMapping;

    @Test
    public void shouldCreateWarpInterceptorInitializingBean() {
        assertThat(warpInterceptorInitializingBean).isNotNull();
    }

    @Test
    public void shouldAddWarpInterceptorToUrlHandlerMappings() {
        verify(simpleUrlHandlerMapping, times(1)).setInterceptors(argThat(new SetInterceptorsArgumentMatcher()));
        verify(beanNameUrlHandlerMapping, times(1)).setInterceptors(argThat(new SetInterceptorsArgumentMatcher()));
    }
}
