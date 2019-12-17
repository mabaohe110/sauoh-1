package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

/**
 * @author nullptr
 * @date 2019/12/17 11:02
 */
@SpringBootTest
public class UserRoleMapperTests {

    private UserRoleMapper mapper;

    @Autowired
    public void setMapper(UserRoleMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void selectTest() {
        List<UserRole> list = mapper.selectAll();
        Assert.notNull(list, "没有数据！");

        list = mapper.selectAllByUser(1);
        Assert.notNull(list, "没有数据！");
    }


    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        List<UserRole> lists = mapper.selectAllByUser(1);
        Assert.notEmpty(lists,"delete 测试前已无数据");

        int count = mapper.deleteByUser(1);
        lists = mapper.selectAllByUser(1);
        Assert.state(lists.isEmpty(), "delete 未成功删除数据");
    }
}
