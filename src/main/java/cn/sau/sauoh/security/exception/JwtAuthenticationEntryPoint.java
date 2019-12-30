package cn.sau.sauoh.security.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nullptr
 * @date 2019/12/29 23:10
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    /**
     * 当用户尝试访问需要权限才能的REST资源而不提供Token或者Token错误或者过期时，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        try (PrintWriter writer = response.getWriter()) {
            Map<String, Object> result = new HashMap<>(5);
            result.put("code", HttpServletResponse.SC_UNAUTHORIZED);
            result.put("msg", "fail:" + authException.getMessage());
            String json = JSON.toJSONString(result);
            writer.write(json);
        }
    }
}