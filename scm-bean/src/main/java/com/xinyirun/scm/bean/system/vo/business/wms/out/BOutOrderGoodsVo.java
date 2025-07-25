package com.xinyirun.scm.bean.system.vo.business.wms.out;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 
 * </p>
 *
 * @author xinyirun
 * @since 2022-02-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BOutOrderGoodsVo implements Serializable {

    private static final long serialVersionUID = 95007177794922083L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 订单id
     */
    private Integer order_id;

    /**
     * 规格id
     */
    private Integer sku_id;

    /**
     * 规格code
     */
    private String sku_code;

    /**
     * 规格name
     */
    private String sku_name;

    /**
     * 规格name
     */
    private String goods_name;

    /**
     * 品名
     */
    private String pm;

    /**
     * 规格
     */
    private String spec;

    /**
     * 单位id
     */
    private Integer unit_id;

    /**
     * 单位code
     */
    private String unit_code;

    /**
     * 单位名称
     */
    private String unit_name;

    /**
     * 单价(含税)
     */
    private BigDecimal price;

    /**
     * 数量
     */
    private BigDecimal num;

    /**
     * 金额(含税)
     */
    private BigDecimal amount;

    /**
     * 税率
     */
    private BigDecimal rate;

    /**
     * 交货日期
     */
    private LocalDateTime delivery_date;

    /**
     * 交货方式(1-自提;1-物流)
     */
    private String delivery_type;

    /**
     * 交货方式(1-自提;1-物流)·
     */
    private String delivery_type_name;

    /**
     * 已出库数量
     */
    private BigDecimal out_actual_count;
}
