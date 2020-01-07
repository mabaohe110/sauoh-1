package cn.sau.sauoh.service;

import cn.sau.sauoh.entity.Doctor;
import cn.sau.sauoh.web.vm.DoctorRecordVM;
import cn.sau.sauoh.web.vm.DoctorVM;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
public interface DoctorService extends IService<Doctor> {

    boolean saveVm(DoctorVM vm);

    boolean saveVmBatch(List<DoctorVM> vmList);

    DoctorRecordVM getVmById(Integer id);
}

