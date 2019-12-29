package cn.sau.sauoh.web.vm;

import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * patient + user
 *
 * @author nullptr
 * @date 2019/12/28 21:12
 */
@Data
public class PatientVM {

    private Integer userId;
    private Integer patientId;

    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    @Email
    private String email;

    @NotNull
    private String patientName;
    @NotNull
    private String sex;
    @NotNull
    private String phone;
    @NotNull
    private Date birthday;

    @JsonIgnore
    public User getUser() {
        return User.builder().id(userId).username(username).password(password).email(email).build();
    }

    @JsonIgnore
    public Patient getPatient() {
        return Patient.builder().id(patientId).userId(userId).
                name(patientName).sex(sex).phone(phone).birthday(birthday).build();
    }
}
