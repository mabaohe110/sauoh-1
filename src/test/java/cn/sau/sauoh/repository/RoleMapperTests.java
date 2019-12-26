package cn.sau.sauoh.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author nullptr
 * @date 2019/12/26 16:35
 */
@Slf4j
@SpringBootTest
public class RoleMapperTests {

    @Autowired
    private RoleMapper mapper;

    @Test
    void seleteTest() {
        mapper.selectAllByUserId(1).forEach(role -> log.info(role.toString()));
    }
}
