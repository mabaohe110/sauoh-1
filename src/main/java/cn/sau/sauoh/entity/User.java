package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Data
@Builder
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
    private String checkCode;
    /**
     *
     */
    private Date createTime;

}
