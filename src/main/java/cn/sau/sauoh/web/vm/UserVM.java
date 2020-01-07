package cn.sau.sauoh.web.vm;

import cn.sau.sauoh.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * view model for user(with role)
 */
@Data
@Builder
public class UserVM {

    private Integer id;
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private List<String> roles;

    public static UserVM buildeWithUserAndRole(User user, List<String> roles) {
        return UserVM.builder().id(user.getId()).username(user.getUsername()).
                email(user.getEmail()).password(user.getPassword()).roles(roles).build();
    }

    @JsonIgnore
    public User getUser() {
        return User.builder().id(id).username(username).email(email)
                .password(password).build();
    }
}
