package cn.sau.sauoh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author nullptr
 */
@SpringBootApplication
@MapperScan("cn.sau.sauoh.repository")
public class SauohApplication {

	public static void main(String[] args) {
		SpringApplication.run(SauohApplication.class, args);
	}

}
