package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.UserRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:27
 */
@Mapper
@Repository
public interface UserRoleMapper extends BaseMapper<UserRole> {

    List<UserRole> selectAllByUserId(Integer userId);

    int deleteAllByUserId(Integer userId);

}
