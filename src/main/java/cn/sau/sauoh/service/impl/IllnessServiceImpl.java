package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.Illness;
import cn.sau.sauoh.repository.IllnessMapper;
import cn.sau.sauoh.service.IllnessService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author nullptr
 * @date 2020/1/6 11:51
 */
@Service("illnessService")
public class IllnessServiceImpl extends ServiceImpl<IllnessMapper, Illness> implements IllnessService {
}
