package com.hmall.api.config;

import com.hmall.api.client.fallback.ItemClientFallback;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;
import com.hmall.common.utils.UserContext;

public class DefaultFeignConfig {

    @Bean
    public Logger.Level feignLoggerLevel() {
        // 控制openfegin日志打印
        return Logger.Level.NONE;
    }

    @Bean
    public RequestInterceptor userInfoRequestInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                // 获取登录用户
                Long userId = UserContext.getUser();
                if(userId == null) {
                    // 如果为空则直接跳过
                    return;
                }
                // 如果不为空则放入请求头中，传递给下游微服务
                template.header("user-info", userId.toString());
            }
        };
    }

    @Bean
    public ItemClientFallback itemClientFallback(){
        return new ItemClientFallback();
    }
}
