package cn.sau.sauoh.web.vm;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * @author nullptr
 * @date 2019/12/29 15:51
 */
@Data
public class DoctorVM {

    private Integer userId;
    private Integer doctorId;
    private Integer departmentId;

    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String sex;

    @NotNull
    private String phone;

    @NotNull
    private Date workedTime;

    @NotNull
    private String level;

    @NotNull
    private String hospital;

    @JsonIgnore
    public User getUser() {
        return User.builder().id(userId).username(username).password(password).email(email).build();
    }

    @JsonIgnore
    public Doctor getDoctor() {
        return Doctor.builder().id(doctorId).userId(userId).name(name)
                .sex(sex).phone(phone).workedTime(workedTime).level(level)
                .hospital(hospital).departmentId(departmentId).build();
    }
}
