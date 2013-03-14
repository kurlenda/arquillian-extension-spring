package org.jboss.arquillian.warp.extension.spring.namespace;

import org.jboss.arquillian.warp.extension.spring.interceptor.WarpInterceptor;
import org.mockito.ArgumentMatcher;
import org.springframework.web.servlet.HandlerInterceptor;

class SetInterceptorsArgumentMatcher extends ArgumentMatcher<HandlerInterceptor[]> {
    @Override
    public boolean matches(Object o) {
        HandlerInterceptor[] handlerInterceptors = (HandlerInterceptor[]) o;

        return handlerInterceptors.length == 1 &&
                handlerInterceptors[0] instanceof WarpInterceptor;
    }
}
