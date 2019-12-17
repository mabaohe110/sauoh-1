package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.PatientList;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @author nullptr
 * @date 2019/12/17 22:27
 */
@Slf4j
@SpringBootTest
public class PatientListTest {

    private PatientListMapper mapper;

    @Autowired
    public void setMapper(PatientListMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void test() {
        List<PatientList> lists = mapper.selectAll();
        lists.forEach((PatientList p) -> log.info(p.toString()));
    }
}
