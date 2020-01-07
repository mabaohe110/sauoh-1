package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.Doctor;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Mapper
@Repository
public interface DoctorMapper extends BaseMapper<Doctor> {

    Doctor selectByUserId(Integer userId);
}
