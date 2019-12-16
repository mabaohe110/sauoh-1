package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

/**
 * 测试 MBG 自动生成的代码
 *
 * @author nullptr
 * @date 2019/12/16 10:34
 */
@Slf4j
@SpringBootTest
class UserMapperTests {

    private UserMapper mapper;

    @Autowired
    public void setMapper(UserMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void selectTest(){
        User one = mapper.selectByPrimaryKey(1);
        log.info(one.toString());

        //非空
        Assert.notNull(one.getId(),"field id is null");
        // 有值
        Assert.hasText(one.getUsername(), "field username is empty");
        Assert.hasText(one.getPassword(), "field password is empty");
    }
}
