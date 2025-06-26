package com.xinyirun.scm.quartz.mapper.master;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyirun.scm.bean.entity.tenant.manager.quartz.SJobManagerEntity;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 定时任务 Mapper 接口
 * </p>
 */
@Repository
public interface SJobManagerQuartzMapper extends BaseMapper<SJobManagerEntity> {

}
