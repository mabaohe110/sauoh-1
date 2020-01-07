package cn.sau.sauoh.repository;

import cn.sau.sauoh.entity.MedicineOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author nullptr
 * @email justitacsl@outlook.com
 * @date 2019-12-25 19:33:28
 */
@Mapper
@Repository
public interface MedicineOrderMapper extends BaseMapper<MedicineOrder> {


    /**
     * 查询一个问诊记录下所有的行（实际就是一个药品订单）
     *
     * @param mrId 问诊记录ID
     */
    List<MedicineOrder> selectAllByMrId(Integer mrId);

    /**
     * 删除一次问诊记录中的药品订单
     */
    int deleteAllByMrId(Integer mrId);

}
