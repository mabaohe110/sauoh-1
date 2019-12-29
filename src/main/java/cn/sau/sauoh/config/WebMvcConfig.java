package cn.sau.sauoh.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.text.DateFormat;

/**
 * @author nullptr
 * @date 2019/12/24 16:21
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder;

    @Autowired
    public void setJackson2ObjectMapperBuilder(Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder) {
        this.jackson2ObjectMapperBuilder = jackson2ObjectMapperBuilder;
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                //暴露header中的其他属性给客户端应用程序
                //如果不设置这个属性前端无法通过response header获取到Authorization也就是token
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600);
    }

    @Bean
    public MappingJackson2HttpMessageConverter mappingJsonpHttpMessageConverter() {

        ObjectMapper mapper = jackson2ObjectMapperBuilder.build();
        DateFormat dateFormat = mapper.getDateFormat();
        mapper.setDateFormat(new MyDateFormat(dateFormat));
        return new MappingJackson2HttpMessageConverter(mapper);
    }
}
