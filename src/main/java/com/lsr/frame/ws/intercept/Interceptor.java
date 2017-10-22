package com.lsr.frame.ws.intercept;

import java.util.Map;

import com.lsr.frame.ws.control.WSActionInvocation;

/**
 * 拦截器接口
 * @author kuaihuolin
 *
 */
public interface Interceptor {

    void destroy();

    void init();

    Map intercept(WSActionInvocation invocation) throws Exception;
}
