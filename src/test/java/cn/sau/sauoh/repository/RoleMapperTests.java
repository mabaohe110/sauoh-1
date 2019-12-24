package cn.sau.sauoh.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author nullptr
 * @date 2019/12/24 21:26
 */
@Slf4j
@SpringBootTest
public class RoleMapperTests {

    private RoleMapper mapper;
    @Autowired
    public void setMapper(RoleMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void selectTest(){
      log.info(mapper.selectAllByUserId(55).toString());
    }
}
