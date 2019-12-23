package cn.sau.sauoh.controller;

import cn.sau.sauoh.entity.Patient;
import cn.sau.sauoh.entity.PatientList;
import cn.sau.sauoh.service.ProvinceAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/admin")
public class AdminController {

    private ProvinceAdminService provinceAdminService;

    @Autowired
    public void setProvinceAdminService(ProvinceAdminService provinceAdminService){ this.provinceAdminService = provinceAdminService; }

    @GetMapping("/selectPatient")
    public @ResponseBody List<PatientList> queryPatient(){
        return provinceAdminService.selectPatient();
    }

    @PutMapping("/updatePatient")
    public int updatePatient(@RequestBody Patient patient){
        System.out.println(patient.toString());
        return provinceAdminService.updatePatient(patient);
    }

}
