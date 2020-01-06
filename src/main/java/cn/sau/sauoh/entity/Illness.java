package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author nullptr
 * @date 2020/1/6 11:46
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("ills")
public class Illness {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String departmentName;
    private String name;
    private String content;
}
