package com.xinyirun.scm.bean.entity.business.allocate;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 库存调拨明细
 * </p>
 *
 * @author wwl
 * @since 2021-12-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("b_allocate_detail")
public class BAllocateDetailEntity implements Serializable {


    private static final long serialVersionUID = 1114644605681388425L;
    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 单据状态
     */
    @TableField("status")
    private String status;

    /**
     * 调拨单id
     */
    @TableField("allocate_id")
    private Integer allocate_id;

    /**
     * 物料id
     */
    @TableField("sku_id")
    private Integer sku_id;

    /**
     * 物料id
     */
    @TableField("sku_code")
    private String sku_code;

    /**
     * 数量
     */
    @TableField("qty")
    private BigDecimal qty;

    /**
     * 出库计划明细id
     */
    @TableField("out_plan_id")
    private Integer out_plan_id;

    /**
     * 入库计划明细id
     */
    @TableField("in_plan_id")
    private Integer in_plan_id;

    /**
     * 审核人id
     */
    @TableField("e_id")
    private Integer e_id;

    /**
     * 审核时间
     */
    @TableField("e_dt")
    private LocalDateTime e_dt;

    /**
     * 创建时间
     */
    @TableField(value="c_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_EMPTY)
    private LocalDateTime c_time;

    /**
     * 修改时间
     */
    @TableField(value="u_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime u_time;

    /**
     * 创建人id
     */
    @TableField(value="c_id", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NOT_EMPTY)
    private Long c_id;

    /**
     * 修改人id
     */
    @TableField(value="u_id", fill = FieldFill.INSERT_UPDATE)
    private Long u_id;


    /**
     * 数据版本，乐观锁使用
     */
    @Version
    @TableField(value="dbversion")
    private Integer dbversion;


}
