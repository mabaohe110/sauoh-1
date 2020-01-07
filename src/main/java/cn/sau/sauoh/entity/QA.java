package cn.sau.sauoh.entity;

import cn.sau.sauoh.utils.DateUtils;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author nullptr
 * @date 2020/1/6 23:14
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("qa")
public class QA implements Serializable {
    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private String question;
    private String answer;
    @JsonFormat(pattern = DateUtils.DATE_TIME_PATTERN)
    private Date createTime;
}
