package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author nullptr
 * @date 2019-12-25 19:33:27
 */
@Builder
@Data
@TableName("user_role")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer userId;
    private Integer roleId;

}
