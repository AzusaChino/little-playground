package cn.az.code.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author az
 * @since 09/01/20
 */
@EnableAsync
@Configuration
public class AsyncConfig {

    @Bean
    @Qualifier("exportExecutor")
    public Executor executor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors() << 1);
        executor.setQueueCapacity(Integer.MAX_VALUE);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("export-");
        executor.setTaskDecorator(r -> {
            // 获取当前上下文
            RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
            return () ->{
                try {
                    // 传递到异步线程
                    RequestContextHolder.setRequestAttributes(requestAttributes);
                    r.run();
                } finally {
                    // 用完销毁
                    RequestContextHolder.resetRequestAttributes();
                }
            };
        });

        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}