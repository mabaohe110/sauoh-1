package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Data
@TableName("medicine_order")
public class MedicineOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 
	 */
	private Integer medicalRecordId;
	/**
	 * 
	 */
	private Integer medicineId;
	/**
	 * 
	 */
	private Integer medicineNum;

}
