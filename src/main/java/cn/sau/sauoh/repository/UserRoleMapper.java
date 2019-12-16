package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleMapper {
    int insert(UserRole record);

    List<UserRole> selectAll();
}