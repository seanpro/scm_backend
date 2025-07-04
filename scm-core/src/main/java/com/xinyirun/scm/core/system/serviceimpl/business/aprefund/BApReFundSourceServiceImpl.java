package com.xinyirun.scm.core.system.serviceimpl.business.aprefund;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyirun.scm.bean.entity.busniess.ap.BApSourceEntity;
import com.xinyirun.scm.bean.entity.busniess.aprefund.BApRefundSourceEntity;
import com.xinyirun.scm.core.system.mapper.business.ap.BApSourceMapper;
import com.xinyirun.scm.core.system.mapper.business.aprefund.BApReFundSourceMapper;
import com.xinyirun.scm.core.system.service.business.ap.IBApSourceService;
import com.xinyirun.scm.core.system.service.business.aprefund.IBApReFundSourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 应付账款关联单据表-源单 服务实现类
 * </p>
 *
 * @author xinyirun
 * @since 2025-02-26
 */
@Service
public class BApReFundSourceServiceImpl extends ServiceImpl<BApReFundSourceMapper, BApRefundSourceEntity> implements IBApReFundSourceService {

}
