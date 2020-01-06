package cn.sau.sauoh.utils;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.*;

/**
 * 异常处理器 、在响应报文中添加错误信息
 */
@Component
@RestControllerAdvice
public class RRExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {

        try (PrintWriter writer = response.getWriter()) {
            response.setContentType("application/json;charset=utf-8");
            response.setCharacterEncoding("utf-8");

            int eCode = 500;
            String eMsg = "未知错误，请联系管理员";
            //一些自定义异常
            if (ex instanceof RRException) {
                eCode = ((RRException) ex).getCode();
                eMsg = ((RRException) ex).getMsg();
            }
            //各种唯一值约束限制和数据库完整性约束
            else if (ex instanceof DataIntegrityViolationException) {
                eCode = 400;
                eMsg = handlDataIntegrityViolationException((DataIntegrityViolationException) ex);
            }

            R r = new R();
            r.put("code", eCode);
            r.put("msg", eMsg);
            response.sendError(eCode, eMsg);
            String json = JSON.toJSONString(r);
            writer.print(json);
            writer.flush();

            //记录异常日志
            logger.error(ex.getMessage(), ex);
        } catch (Exception e) {
            logger.error("RRExceptionHandler 异常处理失败", e);
        }
        return new ModelAndView();
    }

    //表单绑定错误
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map bindException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<String> errors = new ArrayList<>();
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errors.add(fieldError.getField() + ":" + fieldError.getDefaultMessage());
        }
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("code", "400");
        responseBody.put("msg", errors);
        return responseBody;
    }

    private String handlDataIntegrityViolationException(DataIntegrityViolationException e) {
        return Objects.requireNonNull(e.getRootCause()).getLocalizedMessage();
    }
}
