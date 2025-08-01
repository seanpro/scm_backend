package com.xinyirun.scm.core.system.mapper.business.po.aprefundpay;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyirun.scm.bean.entity.business.po.aprefundpay.BApReFundPayAttachEntity;
import com.xinyirun.scm.bean.system.vo.business.po.aprefundpay.BApReFundPayAttachVo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 付款单附件表 Mapper 接口
 * </p>
 *
 * @author xinyirun
 * @since 2025-02-26
 */
@Repository
public interface BApReFundPayAttachMapper extends BaseMapper<BApReFundPayAttachEntity> {

    @Select("""
            -- 根据退款单支付主表ID查询退款单支付附件信息
            SELECT * FROM b_ap_refund_pay_attach 
            -- #{id}: 退款单支付主表ID
            WHERE ap_refund_pay_id = #{id}
            """)
    BApReFundPayAttachVo selectByBApId(Integer id);
}
