package com.xinyirun.scm.bean.system.vo.business.so.socontract;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.xinyirun.scm.common.annotations.DataChangeLabelAnnotation;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Description: 销售合同明细信息
 * @CreateTime : 2025/1/22 15:48
 */

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BSoContractDetailVo implements Serializable {


    @Serial
    private static final long serialVersionUID = -290392252055459509L;

    /**
     * 商品id
     */
    private Integer goods_id;

    /**
     * 商品编号
     */
    private String goods_code;

    /**
     * 商品名称
     */
    private String goods_name;

    /**
     * 规格编号
     */
    private String sku_code;

    /**
     * 规格名称
     */
    private String sku_name;

    /**
     * 物料Id,商品id
     */
    private Integer sku_id;

    /**
     * 单位ID
     */
    private Integer unit_id;

    /**
     * 规格
     */
    private String spec;

    /**
     * 产地
     */
    private String origin;

    /**
     * 数量
     */
    private BigDecimal qty;

    /**
     * 单价（含税）
     */
    private BigDecimal price;

    /**
     * 总额
     */
    private BigDecimal amount;

    /**
     * 税额
     */
    private BigDecimal tax_amount;

    /**
     * 销售合同id
     */
    private Integer so_contract_id;

    /**
     * 税率
     */
    private BigDecimal tax_rate;

    /**
     * 创建时间
     */
    private LocalDateTime c_time;

    /**
     * 修改时间
     */
    private LocalDateTime u_time;

    /**
     * 创建人id
     */
    private Long c_id;

    /**
     * 修改人id
     */
    private Long u_id;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;

}