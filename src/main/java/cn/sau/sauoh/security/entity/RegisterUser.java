package cn.sau.sauoh.security.entity;

import cn.sau.sauoh.entity.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

/**
 * Spring Security 用 UserDetails 抽象用户账号的细节
 * 实现该接口自定义了账号细节
 */
@Data
public class RegisterUser implements UserDetails {

    @NotNull
    private String username;
    @NotNull
    @Email
    private String email;
    @NotNull
    private String password;
    @JsonIgnore
    private Boolean enable;
    @JsonIgnore
    private List<Role> roles;

    public RegisterUser(@NotNull String username, @NotNull @Email String email,
                        @NotNull String password, Boolean enable, List<Role> roles) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.roles = roles;
    }

    /**
     *  返回用户的所有身份
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    /**
     *  账户是否失效
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     *  账户是否被锁定
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     *  认证资格是否过期
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 是否启用
     */
    @Override
    public boolean isEnabled() {
        return enable;
    }
}
