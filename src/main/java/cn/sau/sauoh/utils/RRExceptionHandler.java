package cn.sau.sauoh.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 异常处理器
 */
@Component
public class RRExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        try (PrintWriter writer = response.getWriter())
        {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            int eCode = 500;
            String eMsg = "未知错误，请联系管理员";

            if (ex instanceof RRException) {
                eCode = ((RRException) ex).getCode();
                eMsg = ((RRException) ex).getMsg();
            }//数据完整性校验不通过
            else if (ex instanceof DataIntegrityViolationException) {
                eCode = 400;
                eMsg = ex.getMessage();
            }// SQL 语句参数错误
            else if (ex instanceof BadSqlGrammarException) {
                eCode = 400;
                eMsg = ((BadSqlGrammarException) ex).getSQLException().getMessage();
            }//表单绑定错误
            else if (ex instanceof MethodArgumentNotValidException) {
                eCode = 400;
                eMsg = ((MethodArgumentNotValidException) ex).getMessage();
            }

            R r = new R();
            r.put("code", eCode);
            r.put("msg", eMsg);
            String json = JSON.toJSONString(r);
            writer.print(json);
            writer.flush();
            response.sendError(eCode, eMsg);
            //记录异常日志
            logger.error(ex.getMessage(), ex);
        } catch (Exception e) {
            logger.error("RRExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }
}
