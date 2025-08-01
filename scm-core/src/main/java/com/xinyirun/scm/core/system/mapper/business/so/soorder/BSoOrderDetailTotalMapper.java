package com.xinyirun.scm.core.system.mapper.business.so.soorder;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyirun.scm.bean.entity.business.so.soorder.BSoOrderDetailTotalEntity;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.LinkedHashSet;

/**
 * <p>
 * 销售订单明细汇总表 Mapper 接口
 * </p>
 *
 * @author xinyirun
 * @since 2025-07-23
 */
@Mapper
public interface BSoOrderDetailTotalMapper extends BaseMapper<BSoOrderDetailTotalEntity> {

    /**
     * 更新出库汇总数据
     * 根据出库计划明细数据更新销售订单明细汇总表的出库相关字段
     * 
     * @param soOrderId 销售订单ID集合
     * @return 更新记录数
     */
    @Update("""
        <script>
        UPDATE b_so_order_detail_total t1
        JOIN (
            SELECT
                t2.order_detail_id,
                SUM(IFNULL(t2.processing_qty, 0)) AS sum_processing_qty,
                SUM(IFNULL(t2.processing_weight, 0)) AS sum_processing_weight,
                SUM(IFNULL(t2.processing_volume, 0)) AS sum_processing_volume,
                SUM(IFNULL(t2.unprocessed_qty, 0)) AS sum_unprocessed_qty,
                SUM(IFNULL(t2.unprocessed_weight, 0)) AS sum_unprocessed_weight,
                SUM(IFNULL(t2.unprocessed_volume, 0)) AS sum_unprocessed_volume,
                SUM(IFNULL(t2.processed_qty, 0)) AS sum_processed_qty,
                SUM(IFNULL(t2.processed_weight, 0)) AS sum_processed_weight,
                SUM(IFNULL(t2.processed_volume, 0)) AS sum_processed_volume,
                SUM(IFNULL(t2.cancel_qty, 0)) AS sum_cancel_qty,
                SUM(IFNULL(t2.cancel_weight, 0)) AS sum_cancel_weight,
                SUM(IFNULL(t2.cancel_volume, 0)) AS sum_cancel_volume,
                SUM(IFNULL(t2.qty, 0)) AS sum_plan_qty
            FROM b_out_plan_detail t2
            WHERE t2.order_detail_id IN (
                SELECT sod.id 
                FROM b_so_order_detail sod 
                WHERE sod.so_order_id IN 
                <foreach collection="so_order_id" item="id" open="(" close=")" separator=",">
                    #{id}
                </foreach>
            )
            GROUP BY t2.order_detail_id
        ) t3 ON t1.so_order_detail_id = t3.order_detail_id
        SET
            t1.outbound_processing_qty_total = t3.sum_processing_qty,
            t1.outbound_processing_weight_total = t3.sum_processing_weight,
            t1.outbound_processing_volume_total = t3.sum_processing_volume,
            t1.outbound_unprocessed_qty_total = t3.sum_unprocessed_qty,
            t1.outbound_unprocessed_weight_total = t3.sum_unprocessed_weight,
            t1.outbound_unprocessed_volume_total = t3.sum_unprocessed_volume,
            t1.outbound_processed_qty_total = t3.sum_processed_qty,
            t1.outbound_processed_weight_total = t3.sum_processed_weight,
            t1.outbound_processed_volume_total = t3.sum_processed_volume,
            t1.outbound_cancel_qty_total = t3.sum_cancel_qty,
            t1.outbound_cancel_weight_total = t3.sum_cancel_weight,
            t1.outbound_cancel_volume_total = t3.sum_cancel_volume,
            t1.inventory_out_total = (t3.sum_processed_qty - t3.sum_cancel_qty),
            t1.inventory_out_plan_total = t3.sum_plan_qty
        </script>
        """)
    int updateOutboundTotalBySoOrderIds(@Param("so_order_id") LinkedHashSet<Integer> soOrderId);


    /**
     * 更新待结算数量汇总
     * 计算公式：待结算数量 = 实际出库汇总 - 应结算数量汇总
     * 
     * @param so_order_id 销售订单ID集合
     * @return 更新记录数
     */
    @Update("""
        <script>
        UPDATE b_so_order_detail_total
        SET settle_can_qty_total = IFNULL(cargo_right_transferred_qty_total,0) + IFNULL(inventory_out_total, 0) - IFNULL(settle_planned_qty_total, 0)
        WHERE so_order_id IN
        <foreach collection='so_order_id' item='id' open='(' separator=',' close=')'>
            #{id}
        </foreach>
        </script>
        """)
    int updateSettleCanQtyTotal(@Param("so_order_id") LinkedHashSet<Integer> so_order_id);

    /**
     * 确保明细汇总记录存在
     * 如果不存在则插入，避免更新时找不到记录
     * 
     * @param so_order_id 销售订单ID集合
     * @return 插入记录数
     */
    @Insert("""
        <script>
        INSERT INTO b_so_order_detail_total (so_order_id, so_order_detail_id)
        SELECT t2.so_order_id, t2.id
        FROM b_so_order_detail t2
        LEFT JOIN b_so_order_detail_total t3 ON t2.id = t3.so_order_detail_id
        WHERE t2.so_order_id IN
        <foreach collection='so_order_id' item='id' open='(' separator=',' close=')'>
            #{id}
        </foreach>
        AND t3.so_order_detail_id IS NULL
        </script>
        """)
    int insertMissingRecords(@Param("so_order_id") LinkedHashSet<Integer> so_order_id);

    /**
     * 更新货权转移汇总数据
     * 根据货权转移明细数据按状态分类更新销售订单明细汇总表的货权转移相关字段
     * 状态说明：0,3,4=未转移；1=转移中；2,6=已转移；5=转移取消
     * 
     * @param so_order_id 销售订单ID集合
     * @return 更新记录数
     */
    @Update("""
        <script>
        UPDATE b_so_order_detail_total t1
        JOIN (
            SELECT
                t2.so_order_detail_id,
                SUM(CASE WHEN t3.status IN (0,3,4) THEN IFNULL(t2.transfer_qty, 0) ELSE 0 END) AS sum_untransfer_qty,
                SUM(CASE WHEN t3.status = 1 THEN IFNULL(t2.transfer_qty, 0) ELSE 0 END) AS sum_transfering_qty,
                SUM(CASE WHEN t3.status IN (2,6) THEN IFNULL(t2.transfer_qty, 0) ELSE 0 END) AS sum_transferred_qty,
                SUM(CASE WHEN t3.status = 5 THEN IFNULL(t2.transfer_qty, 0) ELSE 0 END) AS sum_cancel_qty
            FROM b_so_cargo_right_transfer_detail t2
            JOIN b_so_cargo_right_transfer t3 ON t2.cargo_right_transfer_id = t3.id
            WHERE t2.so_order_detail_id IN (
                SELECT sod.id 
                FROM b_so_order_detail sod 
                WHERE sod.so_order_id IN 
                <foreach collection="so_order_id" item="id" open="(" close=")" separator=",">
                    #{id}
                </foreach>
            )
            GROUP BY t2.so_order_detail_id
        ) t3 ON t1.so_order_detail_id = t3.so_order_detail_id
        SET
            t1.cargo_right_untransfer_qty_total = t3.sum_untransfer_qty,
            t1.cargo_right_transfering_qty_total = t3.sum_transfering_qty,
            t1.cargo_right_transferred_qty_total = t3.sum_transferred_qty,
            t1.cargo_right_transfer_cancel_qty_total = t3.sum_cancel_qty
        </script>
        """)
    int updateCargoRightTransferTotalBySoOrderIds(@Param("so_order_id") LinkedHashSet<Integer> soOrderId);
}