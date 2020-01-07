package cn.sau.sauoh.service.impl;

import cn.sau.sauoh.entity.QA;
import cn.sau.sauoh.repository.QAMapper;
import cn.sau.sauoh.service.QAService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author nullptr
 * @date 2020/1/6 23:19
 */
@Service("qaService")
public class QAServiceImpl extends ServiceImpl<QAMapper, QA> implements QAService {

}
