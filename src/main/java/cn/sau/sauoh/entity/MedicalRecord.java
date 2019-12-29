package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * 
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Data
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
	private Integer patientId;
	/**
	 * 
	 */
	private Integer doctorId;
	/**
	 * 
	 */
	private String patientStatus;
	/**
	 * 
	 */
	private String doctorAdvice;

}
