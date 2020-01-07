package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("medical_record")
public class MedicalRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer patientId;
    /**
     *
     */
    @TableField(fill = FieldFill.UPDATE)
    private Integer doctorId;
    /**
     *
     */
    private String patientStatus;
    /**
     *
     */
    private String doctorAdvice;

    private Integer patientAppraise;
}
