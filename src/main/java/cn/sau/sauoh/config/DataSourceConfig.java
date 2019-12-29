package cn.sau.sauoh.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nullptr
 * @date 2019/12/25 20:18
 */
@Configuration
@MapperScan("cn.sau.sauoh.repository")
public class DataSourceConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
         paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量
         paginationInterceptor.setLimit(100);
        return paginationInterceptor;
    }
}
