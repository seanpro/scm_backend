package com.xinyirun.scm.core.system.mapper.business.wms.outplan;

import java.util.LinkedHashSet;
import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyirun.scm.bean.entity.business.wms.outplan.BOutPlanDetailEntity;

/**
 * <p>
 * 出库计划明细 Mapper 接口
 * </p>
 *
 * @author system
 * @since 2025-06-19
 */
@Repository
public interface BOutPlanDetailMapper extends BaseMapper<BOutPlanDetailEntity> {

    /**
     * 根据出库计划ID删除明细数据
     */
    @Delete("""
            DELETE FROM b_out_plan_detail t where t.out_plan_id =  #{out_plan_id}            
            """)
    void deleteByOutPlanId(Integer out_plan_id);

    /**
     * 根据出库计划ID查询明细数据
     */
    @Select("""
            select * FROM b_out_plan_detail t where t.out_plan_id =  #{out_plan_id}            
            """)
    List<BOutPlanDetailEntity> selectByOutPlanId(Integer out_plan_id);

    /**
     * 根据合同ID查询出库计划明细数据
     */
    @Select("""
            select * FROM b_out_plan_detail t where t.contract_id = #{contractId}            
            """)
    List<BOutPlanDetailEntity> selectByContractId(Integer contractId);

    /**
     * 根据出库计划ID查询合同ID
     */
    @Select("""
            select DISTINCT t.contract_id FROM b_out_plan_detail t where t.out_plan_id = #{outPlanId}            
            """)
    List<Integer> selectContractIdsByOutPlanId(Integer outPlanId);

    /**
     * 根据出库计划ID更新处理数量统计
     */
    @Update("""
            <script>                                                                                           
            UPDATE b_out_plan_detail t1                                                                         
            JOIN (                                                                                             
                SELECT                                                                                         
                    t2.plan_detail_id,                                                                         
                    SUM(IFNULL(t2.plan_qty, 0)) AS sum_processing_qty,                                        
                    SUM(IFNULL(t2.plan_weight, 0)) AS sum_processing_weight,                                  
                    SUM(IFNULL(t2.plan_volume, 0)) AS sum_processing_volume,                                  
                    SUM(IFNULL(t2.actual_qty, 0)) AS sum_unprocessed_qty,                                     
                    SUM(IFNULL(t2.actual_weight, 0)) AS sum_unprocessed_weight,                               
                    SUM(IFNULL(t2.actual_volume, 0)) AS sum_unprocessed_volume,                               
                    SUM(IFNULL(t2.qty, 0)) AS sum_processed_qty,                                              
                    SUM(IFNULL(t2.actual_weight, 0)) AS sum_processed_weight,                                 
                    SUM(IFNULL(t2.actual_volume, 0)) AS sum_processed_volume,                                 
                    SUM(IFNULL(t2.cancel_qty, 0)) AS sum_cancel_qty,                                          
                    SUM(IFNULL(t2.cancel_weight, 0)) AS sum_cancel_weight,                                    
                    SUM(IFNULL(t2.cancel_volume, 0)) AS sum_cancel_volume                                     
                FROM b_out t2                               
                JOIN b_out_plan_detail t4 ON t2.plan_detail_id = t4.id
                WHERE t2.is_del = 0                             
                  AND t4.out_plan_id IN
                 <foreach item='item' collection='out_plan_ids' open='(' separator=',' close=')'>  
                   #{item}
                </foreach>
                GROUP BY t2.plan_detail_id                                                                     
            ) t3 ON t1.id = t3.plan_detail_id                                                                 
            SET                                                                                                
                t1.processing_qty = t3.sum_processing_qty,                                                    
                t1.processing_weight = t3.sum_processing_weight,                                              
                t1.processing_volume = t3.sum_processing_volume,                                              
                t1.unprocessed_qty = t3.sum_unprocessed_qty,                                                  
                t1.unprocessed_weight = t3.sum_unprocessed_weight,                                            
                t1.unprocessed_volume = t3.sum_unprocessed_volume,                                            
                t1.processed_qty = t3.sum_processed_qty,                                                      
                t1.processed_weight = t3.sum_processed_weight,                                                
                t1.processed_volume = t3.sum_processed_volume,                                                
                t1.cancel_qty = t3.sum_cancel_qty,                                                            
                t1.cancel_weight = t3.sum_cancel_weight,                                                      
                t1.cancel_volume = t3.sum_cancel_volume                                                       
                where t1.out_plan_id in                                                                         
                <foreach item='item' collection='out_plan_ids' open='(' separator=',' close=')'>               
                    #{item}                                                                                    
                </foreach>                                                                                     
            </script>                                                                                          
            """)
    void updateProcessingQtyByOutPlanId(LinkedHashSet<Integer> out_plan_ids);

}