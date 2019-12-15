package cn.sau.sauoh.entity;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author nullptr
 */
@Data
public class Medicine {
    private Integer id;
    private String name;
    private BigDecimal price;
}