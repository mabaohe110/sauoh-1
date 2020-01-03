package cn.sau.sauoh.security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @author nullptr
 * @date 2019/12/29 22:54
 */
public class JwtUser implements UserDetails {

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser() {
    }

    /**
     * 通过 user 对象创建jwtUser
     */
    public JwtUser(RegisterUser registerUser, Collection<? extends GrantedAuthority> roles) {
        username = registerUser.getUsername();
        password = registerUser.getPassword();
        authorities = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "JwtUser{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authorities=" + authorities +
                '}';
    }

}
