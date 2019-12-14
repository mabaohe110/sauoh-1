package cn.sau.sauoh.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Set;

/**
 * @author nullptr
 * @date 2019/12/11 11:01
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter implements AuthenticationSuccessHandler {
    /**
     * 身份类型字段
     */
    private static final String ROLE_PROVINCE_ADMIN = "ROLE_PROVINCE_ADMIN";
    private static final String ROLE_CITY_ADMIN = "ROLE_CITY_ADMIN";
    private static final String ROLE_DOCTOR = "ROLE_DOCTOR";

    private DataSource dataSource;

    @Autowired
    public SecurityConfig(DataSource dataSource) {
        // SpringBoot 自带 HikariCP
        this.dataSource = dataSource;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                // 暂时保留默认的登陆页面
                .formLogin().successHandler(this).and()
                //配置退出后重定向到首页
                .logout().logoutSuccessUrl("/").and()
                .authorizeRequests()
                // 任意权限都可以访问
                // ps:hasRole 函数会自动为参数加上 'ROLE_' 前缀，也就是说实际的权限名为 ROLE_PATIENT
                .antMatchers("/user", "/user/**").hasAnyRole("PATIENT", "DOCTOR", "CITY_ADMIN", "PROVINCE_ADMIN")
                // 需要 PATIENT 权限
                .antMatchers("/patient", "/patient/**").hasRole("PATIENT")
                // 需要 DOCTOR 权限
                .antMatchers("/doctor", "/doctor/**").hasRole("DOCTOR")
                // 需要 ADMIN 权限
                .antMatchers("/admin", "/admin/**").hasAnyRole("CITY_ADMIN", "PROVINCE_ADMIN")
                // 其他所有访问不需要权限
                .anyRequest().permitAll();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        //基于数据库的认证
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                //这个 SQL 的查询结果集应该包括 用户名、密码和是否启用信息
                .usersByUsernameQuery(
                        "SELECT username, password, true enable FROM user WHERE username = ?;")
                //这个 SQL 的查询结果集应该包括 用户名、权限名
                .authoritiesByUsernameQuery(
                        "SELECT user.username username, role.name authority " +
                                "FROM USER INNER JOIN user_role INNER JOIN role WHERE" +
                                " user.id = user_role.user_id AND user_role.role_id = role.id AND user.username = ?;")
                //开发阶段暂时不对密码进行加密
                .passwordEncoder(NoOpPasswordEncoder.getInstance());
    }

    /**
     * 登陆成功后调用，根据用户身份跳转到指定页面
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication) throws IOException {

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        //有 ADMIN 权限先跳转到 admin、有 DOCTOR 权限跳转到 doctor、有 PATIENT 权限跳转到 patient
        if (roles.contains(ROLE_PROVINCE_ADMIN) || roles.contains(ROLE_CITY_ADMIN)) {
            response.sendRedirect("/admin");
        } else if (roles.contains(ROLE_DOCTOR)) {
            response.sendRedirect("/doctor");
        } else {
            response.sendRedirect("/patient");
        }
    }
}