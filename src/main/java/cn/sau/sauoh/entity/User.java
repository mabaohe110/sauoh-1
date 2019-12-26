package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
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
    @TableId
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
