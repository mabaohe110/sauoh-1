package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("role")
public class Role implements Serializable, GrantedAuthority {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    private String name;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return name;
    }
}
