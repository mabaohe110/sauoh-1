package cn.sau.sauoh.config;

import cn.sau.sauoh.security.exception.JwtAccessDeniedHandler;
import cn.sau.sauoh.security.exception.JwtAuthenticationEntryPoint;
import cn.sau.sauoh.security.filter.JwtAuthenticationFilter;
import cn.sau.sauoh.security.filter.JwtAuthorizationFilter;
import cn.sau.sauoh.security.service.UserDetailsServiceImpl;
import cn.sau.sauoh.utils.R;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author nullptr
 * @date 2019/12/29 18:25
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AuthenticationSuccessHandler, AuthenticationFailureHandler, LogoutSuccessHandler {

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailServiceImpl).passwordEncoder(passwordEncoder());
    }

    //开发阶段接口调试用这个
//    @Override
//    protected void configure(HttpSecurity http) throws Exception{
//        http.authorizeRequests().anyRequest().permitAll();
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //不启用 CSRF
                .cors().and().csrf().disable()
                //对所有的request进行权限判断
                .authorizeRequests()
                //开放登陆url的权限（注册的url在filter中）
                .antMatchers(HttpMethod.POST, "/auth/login").permitAll()
                //所有的资源必须登陆后才能查看
                .antMatchers("/api/**").authenticated()
                //管理员才有删除资源的资格
                .antMatchers(HttpMethod.DELETE, "/api/**").hasAnyRole("ROLE_PROVINCE_ADMIN", "ROLE_CITY_ADMIN")
                //静态资源全部放行
                .anyRequest().permitAll().and()

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager()))
                // 使用了 Jwt 方式进行鉴权，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                // 授权异常处理 （权限相关的异常）
                .exceptionHandling().authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .accessDeniedHandler(new JwtAccessDeniedHandler());

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        try (PrintWriter writer = response.getWriter()) {
            //设置响应报文的响应头、响应体和编码
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_OK);
            //响应体内容
            R r = new R();
            r.put("code", HttpServletResponse.SC_OK);
            r.put("msg", "login success");
            String json = JSON.toJSONString(r);
            //写入响应体内容
            writer.println(json);
            writer.flush();
        }
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        try (PrintWriter writer = response.getWriter()) {
            //设置响应报文的响应头、响应体和编码
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //响应体内容
            R r = new R();
            r.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            r.put("msg", exception.getMessage());
            String json = JSON.toJSONString(r);
            //写入响应体内容
            writer.println(json);
            writer.flush();
        }
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

}
