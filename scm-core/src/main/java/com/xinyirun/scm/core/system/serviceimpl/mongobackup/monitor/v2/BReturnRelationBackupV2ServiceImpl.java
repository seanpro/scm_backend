package com.xinyirun.scm.core.system.serviceimpl.mongobackup.monitor.v2;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyirun.scm.bean.entity.business.returnrelation.BReturnRelationEntity;
import com.xinyirun.scm.bean.entity.mongo2mysql.monitor.v2.BReturnRelationRestoreV2Entity;
import com.xinyirun.scm.bean.system.vo.business.bkmonitor.v2.BBkMonitorLogDetailVo;
import com.xinyirun.scm.core.system.mapper.mongobackup.monitor.v2.BReturnRelationBackupV2Mapper;
import com.xinyirun.scm.core.system.service.mongobackup.monitor.v2.IBReturnRelationBackupV2Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Wang Qianfeng
 * @Description
 * @date 2023/2/16 17:16
 */
@Service
public class BReturnRelationBackupV2ServiceImpl extends ServiceImpl<BReturnRelationBackupV2Mapper, BReturnRelationEntity> implements IBReturnRelationBackupV2Service {

    @Autowired
    private BReturnRelationBackupV2Mapper mapper;

    /**
     * 死锁, 行级锁
     *
     * @param vo
     */
    @Override
    public void selectForUpdate(BBkMonitorLogDetailVo vo) {
        baseMapper.selectForUpdate(vo);
    }

    @Override
    public BReturnRelationRestoreV2Entity selectByMonitorId(Integer monitorId) {
        return mapper.selectByMonitorId(monitorId);
    }

    @Override
    public void deleteByMonitorId(Integer monitorId) {
        mapper.deleteByMonitorId(monitorId);
    }
}
