package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Department;
import cn.sau.sauoh.repository.DepartmentMapper;
import cn.sau.sauoh.service.DepartmentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service("departmentService")
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements DepartmentService {

}