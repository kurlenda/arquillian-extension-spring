package org.jboss.arquillian.warp.extension.spring.namespace;

import org.jboss.arquillian.warp.extension.spring.interceptor.WarpInterceptor;
import org.springframework.beans.factory.FactoryBean;

public class WarpInterceptorFactoryBean implements FactoryBean {

    @Override
    public Object getObject() throws Exception {
        return new WarpInterceptor();
    }

    @Override
    public Class getObjectType() {
        return WarpInterceptor.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
