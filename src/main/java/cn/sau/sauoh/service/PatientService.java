package cn.sau.sauoh.service;

import cn.sau.sauoh.web.vm.PatientVM;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.sau.sauoh.entity.Patient;

import java.util.Map;

/**
 * 
 *
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface PatientService extends IService<Patient> {

    boolean save(PatientVM vm);
}

