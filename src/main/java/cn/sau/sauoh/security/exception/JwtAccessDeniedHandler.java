package cn.sau.sauoh.security.exception;

import com.alibaba.fastjson.JSON;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author nullptr
 * @date 2019/12/29 23:08
 */
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    /**
     * 当用户尝试访问需要权限才能的REST资源而权限不足的时候，
     * 将调用此方法发送401响应以及错误信息
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        accessDeniedException = new AccessDeniedException("权限不足！");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
        try (PrintWriter writer = response.getWriter()) {
            Map<String, Object> result = new HashMap<>(5);
            result.put("code", HttpServletResponse.SC_FORBIDDEN);
            result.put("msg", "fail:" + accessDeniedException.getMessage());
            String json = JSON.toJSONString(result);
            writer.write(json);
        }
    }
}
