package cn.sau.sauoh.config;

import cn.sau.sauoh.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Spring Security 配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements LogoutSuccessHandler {

    private UserDetailsServiceImpl userDetailServiceImpl;

    @Autowired
    public void setUserDetailService(UserDetailsServiceImpl userDetailServiceImpl) {
        this.userDetailServiceImpl = userDetailServiceImpl;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        //todo 部署前替换
//        return new BCryptPasswordEncoder();
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * 配置认证过程
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //账户来源细节的服务和密码编码器
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoder());
    }

    //开发阶段接口调试用这个
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable().
                authorizeRequests().anyRequest().permitAll();
    }

    //部署后、验证权限用这个
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //不启用 CSRF
//                .cors().and().csrf().disable()
//                //对所有的 request 进行权限判断
//                .authorizeRequests()
//                //开放登陆 url 的权限（注册的url在filter中）
//                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
//                //所有的资源必须登陆后才能查看
//                .antMatchers("/api/**").authenticated()
//                //管理员才有删除资源的资格
//                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ROLE_PROVINCE_ADMIN", "ROLE_CITY_ADMIN")
//                //静态资源全部放行
//                .anyRequest().permitAll().and()
//                // 认证和授权的 Filter
//                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
//                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
//                // 使用了 Jwt 方式进行鉴权，所以不需要session
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
//                // 授权异常处理 （权限相关的异常）
//                .exceptionHandling()
//                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
//                .accessDeniedHandler(new JwtAccessDeniedHandler());
//
//    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
