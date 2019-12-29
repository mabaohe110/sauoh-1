package cn.sau.sauoh.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 
 * 
 * @author nullptr
 * @date 2019-12-25 19:33:28
 */
@Data
@TableName("medicine")
public class Medicine implements Serializable {
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
	private String name;
	/**
	 * 
	 */
	private BigDecimal price;


	private String description;
}
