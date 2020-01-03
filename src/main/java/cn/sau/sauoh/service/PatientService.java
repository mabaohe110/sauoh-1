package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.web.vm.PatientVM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface PatientService extends IService<Patient> {

    boolean saveVm(PatientVM vm);

    boolean saveVmBatch(List<PatientVM> vmList);
}

