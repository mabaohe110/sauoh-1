package cn.sau.sauoh.security.filter;

import cn.sau.sauoh.security.SecurityConstants;
import cn.sau.sauoh.security.entity.JwtUser;
import cn.sau.sauoh.security.entity.LoginUser;
import cn.sau.sauoh.security.entity.RegisterUser;
import cn.sau.sauoh.security.utils.JwtTokenUtils;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 基于JWT方式的登陆
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private ThreadLocal<Boolean> rememberMe = new ThreadLocal<>();
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        //监控 /auth/login 路径下的请求
        super.setFilterProcessesUrl(SecurityConstants.AUTH_LOGIN_URL);
    }

    /**
     * 处理登陆
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        try {
            // 从输入流中获取到登录的信息
            LoginUser loginUser = objectMapper.readValue(request.getInputStream(), LoginUser.class);
            rememberMe.set(loginUser.getRememberMe());
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                    loginUser.getUsername(), loginUser.getPassword());
            return authenticationManager.authenticate(authRequest);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果验证成功，就生成token并返回
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) {
        //从登陆的数据中获取registerUser，用于生成JwtUser
        RegisterUser registerUser = (RegisterUser) authentication.getPrincipal();
        JwtUser jwtUser = new JwtUser(registerUser, registerUser.getAuthorities());

        List<String> roles = jwtUser.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        // 创建 Token
        String token = JwtTokenUtils.createToken(jwtUser.getUsername(), roles, rememberMe.get());
        // Http Response Header 中返回 Token
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader(SecurityConstants.TOKEN_HEADER, token);
        try (PrintWriter writer = response.getWriter()) {
            //在响应报文中也添加登陆成功的消息
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("code", HttpServletResponse.SC_OK);
            result.put("msg", "success");
            result.put("roles", roles);
            String json = JSON.toJSONString(result);
            writer.write(json);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException authenticationException) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
        try (PrintWriter writer = response.getWriter()) {
            //在响应报文中添加登陆失败的消息
            Map<String, Object> result = new LinkedHashMap<>();
            result.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            result.put("msg", "fail:" + authenticationException.getMessage());
            String json = JSON.toJSONString(result);
            writer.write(json);
        }
    }
}
