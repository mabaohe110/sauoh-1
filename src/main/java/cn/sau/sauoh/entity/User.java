package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    private String username;
    /**
     *
     */
    private String email;
    /**
     *
     */
    @JsonIgnore
    private String password;
    /**
     *
     */
    @JsonIgnore
    @TableField(fill = FieldFill.UPDATE)
    private String checkCode;
    /**
     *
     */
    private Date createTime;

}
