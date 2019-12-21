package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Doctor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Date;

/**
 * @author nullptr
 * @date 2019/12/21 10:59
 */
@Slf4j
@SpringBootTest
public class DoctorMapperTests {

    private DoctorMapper mapper;

    @Autowired
    public void setMapper(DoctorMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    @Transactional
    @Rollback
    void insertTest() {
        Doctor one = Doctor.builder().userId(18).name("张医生").sex("male").phone("12345678901")
                .workedTime(Date.from(Instant.now())).level("普通医生").hospital("沈阳医院").departmentId(1).checked(true).build();

        Assert.isTrue(mapper.insert(one) == 1, "改变了过多的行数！");

        Doctor two = Doctor.builder().userId(21).name("李医生").sex("male").phone("12345678901")
                .workedTime(Date.from(Instant.now())).level("普通医生").hospital("沈阳医院").departmentId(1).checked(true).build();
        mapper.insertDoctor(two);
        Assert.notNull(two.getId(), "生成主键未回填！");
    }

    @Test
    void selectTest() {
        mapper.selectAll().forEach((Doctor d) ->
                log.debug(d.toString()));
        log.debug(mapper.selectByPrimaryKey(1).toString());
    }

    @Test
    @Transactional
    @Rollback
    void deleteTest() {
        Assert.notNull(mapper.selectByPrimaryKey(1), "删除测试前数据已不存在！");
        mapper.deleteByPrimaryKey(1);
        Assert.isNull(mapper.selectByPrimaryKey(1), "删除测试后数据仍然存在！");
    }

    @Test
    @Transactional
    @Rollback
    void updateTest(){
        Doctor one = mapper.selectByPrimaryKey(1);
        Assert.notNull(one, "修改测试前数据已不存在！");
        one.setLevel("专家");
        mapper.updateByPrimaryKey(one);
        Assert.isTrue(mapper.selectByPrimaryKey(1).getLevel().equals("专家"),"修改数据未执行！");
    }
}
