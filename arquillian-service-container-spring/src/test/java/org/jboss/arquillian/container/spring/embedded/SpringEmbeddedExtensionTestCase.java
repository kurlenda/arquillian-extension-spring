/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
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

package org.jboss.arquillian.container.spring.embedded;

import org.jboss.arquillian.container.spi.client.container.DeployableContainer;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.arquillian.spring.integration.client.SpringIntegrationConfigurationProducer;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * <p>Tests {@link SpringEmbeddedExtension} class.</p>
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 */
public class SpringEmbeddedExtensionTestCase {

    /**
     * <p>Represents the instance of tested class.</p>
     */
    private SpringEmbeddedExtension instance;

    /**
     * <p>Sets up the test environment.</p>
     */
    @Before
    public void setUp() {

        instance = new SpringEmbeddedExtension();
    }

    /**
     * <p>Tests the {@link SpringEmbeddedExtension#register(LoadableExtension.ExtensionBuilder)} method.</p>
     */
    @Test
    public void testRegister() {

        LoadableExtension.ExtensionBuilder mockExtensionBuilder = mock(LoadableExtension.ExtensionBuilder.class);
        when(mockExtensionBuilder.service(any(Class.class), any(Class.class))).thenReturn(mockExtensionBuilder);
        when(mockExtensionBuilder.observer(any(Class.class))).thenReturn(mockExtensionBuilder);

        instance.register(mockExtensionBuilder);

        verify(mockExtensionBuilder).service(DeployableContainer.class, SpringEmbeddedContainer.class);
        verify(mockExtensionBuilder).observer(SpringEmbeddedApplicationContextProducer.class);


        verifyNoMoreInteractions(mockExtensionBuilder);
    }
}
