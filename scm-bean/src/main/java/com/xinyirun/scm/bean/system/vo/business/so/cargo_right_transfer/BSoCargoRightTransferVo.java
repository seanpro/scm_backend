package com.xinyirun.scm.bean.system.vo.business.so.cargo_right_transfer;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.xinyirun.scm.bean.system.vo.business.bpm.OrgUserVo;
import com.xinyirun.scm.bean.system.vo.business.project.BProjectVo;
import com.xinyirun.scm.bean.system.vo.common.condition.PageCondition;
import com.xinyirun.scm.bean.system.vo.master.enterprise.MEnterpriseVo;
import com.xinyirun.scm.bean.system.vo.master.user.MUserVo;
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
 * 销售货权转移主表VO类
 * 
 * @author system
 * @since 2025-07-27
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class BSoCargoRightTransferVo implements Serializable {

    @Serial
    private static final long serialVersionUID = -8915379284144468713L;

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 货权转移单号
     */
    private String code;

    /**
     * 货权转移日期
     */
    private LocalDateTime transfer_date;

    /**
     * 状态(0-待审批,1-审批中,2-执行中,3-驳回,4-作废审批中,5-已作废,6-已完成)
     */
    private String status;

    /**
     * 状态名称
     */
    private String status_name;

    /**
     * 销售订单ID
     */
    private Integer so_order_id;

    /**
     * 销售订单号
     */
    private String so_order_code;

    /**
     * 销售合同ID
     */
    private Integer so_contract_id;

    /**
     * 销售合同号
     */
    private String so_contract_code;

    /**
     * 项目编码
     */
    private String project_code;

    /**
     * 客户ID
     */
    private Integer customer_id;

    /**
     * 客户编码
     */
    private String customer_code;

    /**
     * 客户名称
     */
    private String customer_name;

    /**
     * 销售员ID
     */
    private Integer seller_id;

    /**
     * 销售员编码
     */
    private String seller_code;

    /**
     * 销售员姓名
     */
    private String seller_name;

    /**
     * 货权转移地点
     */
    private String transfer_location;

    /**
     * 备注
     */
    private String remark;

    /**
     * 转移总数量
     */
    private BigDecimal total_qty;

    /**
     * 转移总金额
     */
    private BigDecimal total_amount;

    /**
     * 未处理中数量
     */
    private BigDecimal cargo_right_untransfer_qty_total;

    /**
     * 处理中数量
     */
    private BigDecimal cargo_right_transfering_qty_total;

    /**
     * 已处理移数量
     */
    private BigDecimal cargo_right_transferred_qty_total;

    /**
     * 已作废数量
     */
    private BigDecimal cargo_right_transfer_cancel_qty_total;

    /**
     * 下一审批人
     */
    private String next_approve_name;

    /**
     * BPM实例ID
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
     * BPM取消实例ID
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
     * 是否删除(0-否,1-是)
     */
    private Boolean is_del;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime c_time;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime u_time;

    /**
     * 创建人ID
     */
    private Long c_id;

    /**
     * 更新人ID
     */
    private Long u_id;

    /**
     * 数据版本号
     */
    private Integer dbversion;

    // ========== 扩展字段 ==========

    /**
     * 创建人信息
     */
    private MUserVo createUser;

    /**
     * 更新人信息
     */
    private MUserVo updateUser;

    /**
     * 客户信息
     */
    private MEnterpriseVo customer;

    /**
     * 销售员信息
     */
    private MUserVo seller;

    /**
     * 项目信息
     */
    private BProjectVo project;

    /**
     * 明细列表数据
     */
    private List<BSoCargoRightTransferDetailVo> detailListData;

    /**
     * 附件列表数据
     */
    private List<BSoCargoRightTransferAttachVo> attachListData;

    /**
     * 汇总数据
     */
    private BSoCargoRightTransferTotalVo totalData;

    // ========== 附件相关字段 ==========

    /**
     * 其他附件ID
     */
    private Integer doc_att_file;

    /**
     * 其他附件列表
     */
    private List<SFileInfoVo> doc_att_files;

    // ========== 作废相关字段 ==========

    /**
     * 作废理由
     */
    private String cancel_reason;

    /**
     * 作废附件ID
     */
    private Integer cancel_file;

    /**
     * 作废附件列表
     */
    private List<SFileInfoVo> cancel_files;

    /**
     * 作废附件列表（用于详情显示）
     */
    private List<SFileInfoVo> cancel_doc_att_files;

    /**
     * 作废提交人
     */
    private String cancel_name;

    /**
     * 作废时间
     */
    private LocalDateTime cancel_time;

    // ========== BPM流程相关字段 ==========

    /**
     * 初始化审批流程
     */
    private String initial_process;

    /**
     * 表单数据
     */
    private JSONObject form_data;

    /**
     * 自选数据
     */
    private Map<String, List<OrgUserVo>> process_users;

    // ========== 打印相关字段 ==========

    /**
     * 报表编号
     */
    private String print_url;

    /**
     * 二维码
     */
    private String qr_code;

    // ========== 查询条件字段 ==========

    /**
     * 状态列表（查询条件）
     */
    private String[] statusList;

    /**
     * 客户ID列表（查询条件）
     */
    private Integer[] customerIdList;

    /**
     * 销售员ID列表（查询条件）
     */
    private Integer[] sellerIdList;

    /**
     * 创建时间范围开始
     */
    private LocalDateTime createTimeStart;

    /**
     * 创建时间范围结束
     */
    private LocalDateTime createTimeEnd;

    /**
     * 转移日期范围开始
     */
    private LocalDateTime transferDateStart;

    /**
     * 转移日期范围结束
     */
    private LocalDateTime transferDateEnd;

    /**
     * 分页条件
     */
    private PageCondition pageCondition;

    /**
     * 查询关键字（模糊查询单号、客户名称等）
     */
    private String searchKeyword;

    /**
     * 校验类型
     */
    private String check_type;

    /**
     * 单据状态列表
     */
    private String[] status_list;

    /**
     * 物料名称或编码
     */
    private String goods_code;
    private String goods_name;

    /**
     * 审批流实例化code
     */
    private String process_code;
}