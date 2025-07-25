package com.xinyirun.scm.bean.entity.busniess.wms.in.order;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 入库订单
 * </p>
 *
 * @author htt
 * @since 2021-10-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("b_in_order")
public class BInOrderEntity implements Serializable {

    private static final long serialVersionUID = -1015970178431107247L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 状态 0执行中 1正常 -1停用
     */
    @TableField("status")
    private String status;

    /**
     * 订单编号
     */
    @TableField("order_no")
    private String order_no;

    /**
     * 单据类型
     */
    @TableField("bill_type")
    private String bill_type;

    /**
     * 合同编号
     */
    @TableField("contract_no")
    private String contract_no;

    /**
     * 船名
     */
    @TableField("ship_name")
    private String ship_name;

    /**
     * 合同日期
     */
    @TableField("contract_dt")
    private LocalDateTime contract_dt;

    /**
     * 合同截止日期
     */
    @TableField("contract_expire_dt")
    private LocalDateTime contract_expire_dt;

    /**
     * 合同量
     */
    @TableField("contract_num")
    private BigDecimal contract_num;

    /**
     * 供应商
     */
    @TableField("supplier_id")
    private Integer supplier_id;

    /**
     * 供应商code
     */
    @TableField("supplier_code")
    private String supplier_code;

    /**
     * 货主id
     */
    @TableField("owner_id")
    private Integer owner_id;

    /**
     * 货主code
     */
    @TableField("owner_code")
    private String owner_code;

    /**
     * 业务板块ID
     */
    @TableField("business_type_id")
    private Integer business_type_id;

    /**
     * 业务板块code
     */
    @TableField("business_type_code")
    private String business_type_code;

    /**
     * 是否数量浮动管控
     */
    @TableField("over_inventory_policy")
    private Boolean over_inventory_policy;

    /**
     * 上浮百分比
     */
    @TableField("over_inventory_upper")
    private BigDecimal over_inventory_upper;

    /**
     * 下浮百分比
     */
    @TableField("over_inventory_lower")
    private BigDecimal over_inventory_lower;

    @TableField("source_type")
    private String source_type;

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
