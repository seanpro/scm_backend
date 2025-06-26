package com.xinyirun.scm.core.tenant.serviceimpl.business.quartz;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyirun.scm.bean.entity.tenant.manager.quartz.SJobManagerEntity;
import com.xinyirun.scm.bean.system.ao.result.InsertResultAo;
import com.xinyirun.scm.bean.system.bo.tenant.manager.quartz.SJobManagerBo;
import com.xinyirun.scm.bean.system.result.utils.v1.InsertResultUtil;
import com.xinyirun.scm.core.tenant.mapper.business.quartz.SJobManagerMapper;
import com.xinyirun.scm.core.tenant.service.business.quartz.ISJobManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 定时任务 服务实现类
 * </p>
 */
@Slf4j
@Service
public class SJobManagerServiceImpl extends ServiceImpl<SJobManagerMapper, SJobManagerEntity> implements ISJobManagerService {

    @Autowired
    private SJobManagerMapper mapper;

    /**
     * 保存定时任务
     *
     * @param bo 定时任务实体
     * @return 插入结果
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResultAo<SJobManagerBo> insert(SJobManagerBo bo) {
        SJobManagerEntity entity = new SJobManagerEntity();
        BeanUtils.copyProperties(bo, entity);
        mapper.insert(entity);
        bo.setId(entity.getId());
        return InsertResultUtil.OK(bo);
    }
}
