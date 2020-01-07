package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("medicine_order")
public class MedicineOrder implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Integer id;
    /**
     *
     */
    private Integer medicalRecordId;
    /**
     *
     */
    private String medicineName;
    /**
     *
     */
    private Integer medicineNum;

}
