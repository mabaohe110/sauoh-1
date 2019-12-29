package cn.sau.sauoh.service;

import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.sau.sauoh.entity.Doctor;

/**
 * 
 *
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface DoctorService extends IService<Doctor> {

    boolean save(DoctorVM vm);
}

