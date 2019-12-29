package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.MedicineOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Mapper
@Repository
public interface MedicineOrderMapper extends BaseMapper<MedicineOrder> {

    /**
     * 删除一次问诊记录中的所有药品订单
     * @param recordId
     * @return
     */
    int deleteAllByRecordId(Integer recordId);
}
