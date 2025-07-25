//package com.xinyirun.scm.core.system.service.base.v1.common.inventory;
//
//import com.baomidou.mybatisplus.extension.service.IService;
//import com.xinyirun.scm.bean.entity.master.inventory.MInventoryEntity;
//import com.xinyirun.scm.bean.entity.master.inventory.v2.MInventoryV2Entity;
//import com.xinyirun.scm.bean.system.bo.inventory.commonlogic.adjust.StockAdjustBo;
//import com.xinyirun.scm.bean.system.bo.inventory.commonlogic.in.StockInBo;
//import com.xinyirun.scm.bean.system.bo.inventory.commonlogic.in.StockInV2Bo;
//import com.xinyirun.scm.bean.system.bo.inventory.commonlogic.out.StockOutBo;
//import com.xinyirun.scm.common.enums.InventoryBusinessTypeV2Enum;
//
///**
// * wms模块核心代码：库存的更新
// *
// * @author
// */
//public interface ICommonInventoryLogicV2Service extends IService<MInventoryV2Entity> {
//
//    /**
//     *  修改库存信息：入库
//     * @param inBo 入库
//     */
//    public void  updWmsStock(StockInV2Bo inBo);
//
//    /**
//     *  修改库存信息：出库
//     * @param outBo 出库
//     */
//    //public void  updWmsStock(StockOutBo outBo);
//
//    /**
//     *  修改库存信息：库存调整
//     * @param adjBo 库存调整
//     */
//    //public void  updWmsStock(StockAdjustBo adjBo);
//
//    /**
//     * 通过调整单据，进行调整
//     * @param adjust_id 调整单id
//     */
//    //public void updWmsStockByAdjustBill(Integer adjust_id);
//
//    /**
//     * 通过入库单据，进行入库
//     * @param in_id 入库单id
//     * @param type 操作类型
//     */
//    public void updWmsStockByInBill(Integer in_id, InventoryBusinessTypeV2Enum type);
//
//    /**
//     * 通过出库单据，进行出库
//     * @param out_id 出库单id
//     */
//    //public void updWmsStockByOutBill(Integer out_id);
//
//}