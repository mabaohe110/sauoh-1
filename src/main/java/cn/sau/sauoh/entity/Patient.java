package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Data
@Builder
@TableName("patient")
public class Patient implements Serializable {
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

    private Date birthday;

    private String phone;
}
