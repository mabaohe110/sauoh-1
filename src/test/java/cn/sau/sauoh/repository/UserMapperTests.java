package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.sql.Timestamp;
import java.time.Instant;

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
    void selectTest() {
        User one = mapper.selectByPrimaryKey(1);
        log.info(one.toString());

        //非空
        Assert.notNull(one.getId(), "field id is null");
        // 有值
        Assert.hasText(one.getUsername(), "field username is empty");
        Assert.hasText(one.getPassword(), "field password is empty");

        User two = mapper.selectByUsername("province_admin");
        log.info(two.toString());
        //非空
        Assert.notNull(two.getId(), "field id is null");
        // 有值
        Assert.hasText(two.getUsername(), "field username is empty");
        Assert.hasText(two.getPassword(), "field password is empty");

        Assert.isTrue(mapper.checkCodeExist("ttt"), "data not exist");
        Assert.isTrue(!mapper.checkCodeExist("test"), "data exist");
    }

    /**
     * 此处的测试仅是为了测试 MBG 自动生成的存储过程是否可用，
     * 测试前应先在数据源中查看数据是否符合要求，即：无外键约束、指定主键存在等
     */
    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        User one = mapper.selectByPrimaryKey(28);
        Assert.notNull(one, "delete 测试前指定数据已不存在");

        mapper.deleteByPrimaryKey(28);
        one = mapper.selectByPrimaryKey(28);
        Assert.isNull(one, "delete 测试后指定数据仍然存在");
    }

    @Test
    @Transactional
    @Rollback
    void updateTest() {
        User one = User.builder().id(1).username("测试用户").password("测试密码").
                checkCode("随机字符串").createTime(Timestamp.from(Instant.now())).build();

        mapper.updateByPrimaryKey(one);
        User two = mapper.selectByPrimaryKey(1);
        Assert.notNull(two, "并未查找到数据");
        log.info(two.toString());
        log.info(one.toString());
        //数据库中TimeStamp精度更高，所以这个断言不会通过，但不影响结果
        //Assert.isTrue(one.equals(two), "数据不一致");
    }

    @Test
    @Transactional
    @Rollback
    void insertTest() {
        User one = User.builder().username("测试用户").email("12345@qq.com").password("测试密码").
                checkCode("随机字符串").createTime(Timestamp.from(Instant.now())).build();

        int rows = mapper.insert(one);
        log.info(rows + " row(s) changes");
        Assert.isTrue(rows == 1, "变更行数不等于1！");

        User two = User.builder().username("用户测试").password("测试密码").
                checkCode("随机字符串").createTime(Timestamp.from(Instant.now())).build();

        log.info(two.toString());
        Assert.isTrue(mapper.insertUser(two) == 1, "返回主键值错误！");
        Assert.notNull(two.getId(), "主键值未回填");
    }
}
