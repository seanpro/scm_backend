package com.xinyirun.scm.bean.entity.business.todo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 已办事项
 * </p>
 *
 * @author wwl
 * @since 2021-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("b_already_do")
public class BAlreadyDoEntity implements Serializable {

    private static final long serialVersionUID = 3557055301544467938L;

    /**
     * 主键id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 关联单号类型
     */
    @TableField("serial_type")
    private String serial_type;

    /**
     * 关联单号id
     */
    @TableField("serial_id")
    private Integer serial_id;

    /**
     * 员工id
     */
    @TableField("staff_id")
    private Long staff_id;

    /**
     * 页面id
     */
    @TableField("page_id")
    private Long page_id;

    /**
     * 页面code
     */
    @TableField("page_code")
    private String page_code;

    /**
     * 权限标识
     */
    @TableField("perms")
    private String perms;

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
    @TableField(value="dbversion")
    private Integer dbversion;
}
