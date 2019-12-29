package cn.sau.sauoh.security.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author nullptr
 * @date 2019/12/29 21:12
 */
public class CustomLoginHandler extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


}
