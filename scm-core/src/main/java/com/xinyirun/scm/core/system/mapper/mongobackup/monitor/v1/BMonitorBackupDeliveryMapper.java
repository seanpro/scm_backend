package com.xinyirun.scm.core.system.mapper.mongobackup.monitor.v1;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyirun.scm.bean.entity.business.monitor.BMonitorDeliveryEntity;
import com.xinyirun.scm.bean.system.vo.business.bkmonitor.v1.BBkMonitorLogDetailVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author htt
 * @since 2021-09-23
 */
@Repository
public interface BMonitorBackupDeliveryMapper extends BaseMapper<BMonitorDeliveryEntity> {

    @Select("select 1 from b_monitor_delivery where id = #{p1.monitor_delivery_id}  for update")
    List<Integer> selectForUpdate(@Param("p1") BBkMonitorLogDetailVo vo);
}
