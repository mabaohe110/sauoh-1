package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;


/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("doctor")
public class Doctor implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @NotNull
    private Integer userId;
    /**
     *
     */
    @NotNull
    private String name;
    /**
     *
     */
    @NotNull
    private String sex;
    /**
     *
     */
    @NotNull
    private String phone;
    /**
     *
     */
    @NotNull
    private Date workedTime;
    /**
     *
     */
    @NotNull
    private String level;
    /**
     *
     */
    @NotNull
    private String hospital;
    /**
     *
     */
    @NotNull
    private Integer departmentId;
    /**
     *
     */
    private Integer checked;

}
