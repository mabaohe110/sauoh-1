package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
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
@TableName("patient")
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;
    @NotNull
    private Integer userId;
    @NotNull
    private String name;
    @NotNull
    private String sex;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    @NotNull
    private String phone;
}
