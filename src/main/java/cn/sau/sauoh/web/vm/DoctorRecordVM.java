package cn.sau.sauoh.web.vm;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 患者看到的医生的信息
 *
 * @author nullptr
 * @date 2020/1/5 12:57
 */
@Data
@Builder
public class DoctorRecordVM {

    private Integer id;

    private String doctorName;
    private String phone;
    private String level;
    private String hospital;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date workTime;

    private Integer recordNums;
    private Float applauseRase;
}
