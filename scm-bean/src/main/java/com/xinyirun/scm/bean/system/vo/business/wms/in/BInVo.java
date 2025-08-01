package com.xinyirun.scm.bean.system.vo.business.wms.in;

import com.alibaba.fastjson2.JSONObject;
import com.xinyirun.scm.bean.system.vo.business.bpm.OrgUserVo;
import com.xinyirun.scm.bean.system.vo.common.condition.PageCondition;
import com.xinyirun.scm.bean.system.vo.sys.file.SFileInfoVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 入库单
 * </p>
 *
 * @author xinyirun
 * @since 2025-06-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BInVo implements Serializable {


    @Serial
    private static final long serialVersionUID = 2196009004356626516L;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 入库单号
     */
    private String code;

    /**
     * 计划单号
     */
    private String plan_code;
    /**
     * 序号
     */
    private String plan_no;

    /**
     * 合同编号
     */
    private String contract_code;

    /**
     * 订单编号
     */
    private String order_code;

    /**
     * 计划时间
     */
    private LocalDateTime plan_time;

    private String goods_name;
    private String sku_name;

    /**
     * 入库类型：0=采购入库 1=调拨入库 2=退货入库 3=监管入库 4=普通入库 5=生产入库
     */
    private String type;
    /**
     * 类型名称
     */
    private String type_name;

    /**
     * 入库状态
     */
    private String status;
    /**
     * 状态名称
     */
    private String status_name;

    /**
     * 合同id
     */
    private Integer contract_id;

    /**
     * 订单id
     */
    private Integer order_id;

    /**
     * 下一个审批人姓名
     */
    private String next_approve_name;

    /**
     * 货主id
     */
    private Integer owner_id;

    /**
     * 货主编码
     */
    private String owner_code;
    private String owner_name;

    /**
     * 入库计划单id
     */
    private Integer plan_id;

    /**
     * 入库计划明细单id
     */
    private Integer plan_detail_id;

    /**
     * 委托方id
     */
    private Integer consignor_id;

    /**
     * 委托方编码
     */
    private String consignor_code;
    
    /**
     * 委托方名称
     */
    private String consignor_name;

    /**
     * 供应商id
     */
    private Integer supplier_id;

    /**
     * 供应商编码
     */
    private String supplier_code;

    /**
     * 供应商名称
     */
    private String supplier_name;

    /**
     * 批次
     */
    private String lot;

    /**
     * 备注
     */
    private String remark;

    /**
     * 入库仓库id
     */
    private Integer warehouse_id;
    /**
     * 仓库名称
     */
    private String warehouse_name;

    /**
     * 入库库区id
     */
    private Integer location_id;
    private String location_name;

    /**
     * 入库库位id
     */
    private Integer bin_id;
    private String bin_name;

    /**
     * 物料id
     */
    private Integer sku_id;

    /**
     * 物料编码
     */
    private String sku_code;

    /**
     * 计划数量
     */
    private BigDecimal plan_qty;

    /**
     * 计划重量
     */
    private BigDecimal plan_weight;

    /**
     * 计划体积
     */
    private BigDecimal plan_volume;

    /**
     * 实际数量（净重）
     */
    private BigDecimal actual_qty;

    /**
     * 入库数量
     */
    private BigDecimal qty;

    /**
     * 实际重量
     */
    private BigDecimal actual_weight;

    /**
     * 实际体积
     */
    private BigDecimal actual_volume;

    /**
     * 单价
     */
    private BigDecimal price;

    /**
     * 入库金额
     */
    private BigDecimal amount;

    /**
     * 入库单位
     */
    private Integer unit_id;

    /**
     * 入库时间
     */
    private LocalDateTime inbound_time;

    /**
     * 处理中数量
     */
    private BigDecimal processing_qty;

    /**
     * 处理中重量
     */
    private BigDecimal processing_weight;

    /**
     * 处理中体积
     */
    private BigDecimal processing_volume;

    /**
     * 待处理数量
     */
    private BigDecimal unprocessed_qty;

    /**
     * 待处理重量
     */
    private BigDecimal unprocessed_weight;

    /**
     * 待处理体积
     */
    private BigDecimal unprocessed_volume;

    /**
     * 已处理(出/入)库数量
     */
    private BigDecimal processed_qty;

    /**
     * 已处理(出/入)库重量
     */
    private BigDecimal processed_weight;

    /**
     * 已处理(出/入)库体积
     */
    private BigDecimal processed_volume;

    /**
     * 作废数量
     */
    private BigDecimal cancel_qty;

    /**
     * 作废重量
     */
    private BigDecimal cancel_weight;

    /**
     * 作废体积
     */
    private BigDecimal cancel_volume;

    /**
     * 库存流水id
     */
    private Integer inventory_sequence_id;

    /**
     * 车牌号
     */
    private String vehicle_no;

    /**
     * 皮重
     */
    private BigDecimal tare_weight;

    /**
     * 毛重
     */
    private BigDecimal gross_weight;

    /**
     * 原发数量
     */
    private BigDecimal original_qty;

    /**
     * 实收车数
     */
    private Integer cart_count;

    /**
     * 是否异常:false否;true:是
     */
    private Boolean is_exception;

    /**
     * 异常描述
     */
    private String exception_comment;

    /**
     * 物料是否合格:true合格,false不合格
     */
    private Boolean stock_status;

    /**
     * BPM实例id
     */
    private Integer bpm_instance_id;

    /**
     * BPM实例编码
     */
    private String bpm_instance_code;

    /**
     * BPM流程名称
     */
    private String bpm_process_name;

    /**
     * BPM取消实例id
     */
    private Integer bpm_cancel_instance_id;

    /**
     * BPM取消实例编码
     */
    private String bpm_cancel_instance_code;

    /**
     * BPM取消流程名称
     */
    private String bpm_cancel_process_name;

    /**
     * 创建人id
     */
    private Long c_id;

    /**
     * 创建时间
     */
    private LocalDateTime c_time;

    /**
     * 修改人id
     */
    private Long u_id;

    /**
     * 修改时间
     */
    private LocalDateTime u_time;

    /**
     * 数据版本，乐观锁使用
     */
    private Integer dbversion;

    /**
     * 删除0-未删除，1-已删除
     */
    private Boolean is_del;

    /**
     * 校验类型
     */
    private String check_type;


    /**
     * 单据状态列表
     */
    private String[] status_list;

    /**
     * 类型列表
     */
    private String[] type_list;

    /**
     * 表头数据：合计区域
     */
    private BigDecimal qty_total;
    private BigDecimal amount_total;

    /**
     * 换页条件
     */
    private PageCondition pageCondition;

    /**
     * 初始化流程节点key(用于启动流程节点)
     */
    private String initial_process;
    /**
     * 流程数据
     */
    private JSONObject form_data;

    /**
     * 自选数据
     */
    private Map<String, List<OrgUserVo>> process_users;

    /**
     * 组织用户vo
     */
    private OrgUserVo orgUserVo;

    private List<SFileInfoVo> one_file;
    private List<SFileInfoVo> two_file;
    private List<SFileInfoVo> three_file;
    private List<SFileInfoVo> four_file;

    /**
     * 创建人
     */
    private String c_name;

    /**
     * 修改人
     */
    private String u_name;

    private String unit_name;

    // ============ 采购合同相关字段 ============
    
    /**
     * 采购合同类型
     */
    private String po_contract_type;
    
    /**
     * 采购合同类型名称
     */
    private String po_contract_type_name;
    
    /**
     * 主体企业（采购方名称）
     */
    private String purchaser_name;
    
    /**
     * 采购合同付款方式
     */
    private String po_contract_payment_type;
    
    /**
     * 付款方式名称
     */
    private String payment_type_name;
    
    /**
     * 采购合同结算方式
     */
    private String po_contract_settle_type;
    
    /**
     * 结算方式名称
     */
    private String settle_type_name;
    
    /**
     * 交货日期
     */
    private LocalDateTime delivery_date;
    
    /**
     * 交货地点
     */
    private String delivery_location;

    // ============ 采购订单相关字段 ============
    
    /**
     * 采购订单状态
     */
    private String po_order_status;
    
    /**
     * 采购订单状态名称
     */
    private String po_order_status_name;
    
    /**
     * 采购订单运输方式
     */
    private String po_order_delivery_type;
    
    /**
     * 运输方式名称
     */
    private String delivery_type_name;

    // ============ 作废相关字段 ============
    
    /**
     * 作废理由
     */
    private String cancel_reason;
    
    /**
     * 作废人姓名
     */
    private String cancel_name;
    
    /**
     * 作废时间
     */
    private LocalDateTime cancel_time;
    
    /**
     * 作废附件文件列表
     */
    private List<SFileInfoVo> cancel_doc_att_files;

    // ============ 附件相关字段 ============
    
    /**
     * 第一个附件文件ID
     */
    private Integer doc_one_file;

    /**
     * 第二个附件文件ID
     */
    private Integer doc_two_file;

    /**
     * 第三个附件文件ID
     */
    private Integer doc_three_file;

    /**
     * 第四个附件文件ID
     */
    private Integer doc_four_file;

    /**
     * 作废附件文件列表
     */
    private List<SFileInfoVo> cancel_files;
}

