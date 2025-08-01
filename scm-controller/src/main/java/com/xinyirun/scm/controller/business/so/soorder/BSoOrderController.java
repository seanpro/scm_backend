package com.xinyirun.scm.controller.business.so.soorder;


import cn.idev.excel.EasyExcel;
import cn.idev.excel.write.metadata.WriteSheet;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xinyirun.scm.bean.system.ao.result.CheckResultAo;
import com.xinyirun.scm.bean.system.ao.result.InsertResultAo;
import com.xinyirun.scm.bean.system.ao.result.JsonResultAo;
import com.xinyirun.scm.bean.system.result.utils.v1.ResultUtil;
import com.xinyirun.scm.bean.system.vo.business.so.soorder.BSoOrderDetailVo;
import com.xinyirun.scm.bean.system.vo.business.so.soorder.BSoOrderExportVo;
import com.xinyirun.scm.bean.system.vo.business.so.soorder.BSoOrderVo;
import com.xinyirun.scm.bean.system.vo.business.wo.BWoExportUtilVo;
import com.xinyirun.scm.common.annotations.RepeatSubmitAnnotion;
import com.xinyirun.scm.common.annotations.SysLogAnnotion;
import com.xinyirun.scm.common.exception.system.BusinessException;
import com.xinyirun.scm.common.exception.system.InsertErrorException;
import com.xinyirun.scm.common.exception.system.UpdateErrorException;
import com.xinyirun.scm.common.utils.DateTimeUtil;
import com.xinyirun.scm.core.system.service.business.so.soorder.IBSoOrderService;
import com.xinyirun.scm.excel.export.CustomMergeStrategy;
import com.xinyirun.scm.excel.export.EasyExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 销售订单表 前端控制器
 * </p>
 *
 * @author xinyirun
 * @since 2025-07-23
 */
@RestController
@RequestMapping("/api/v1/soorder")
public class BSoOrderController {

    @Autowired
    private IBSoOrderService service;

    /**
     * 销售订单  新增
     */
    @PostMapping("/insert")
    @SysLogAnnotion("销售订单 新增")
    @RepeatSubmitAnnotion
    public ResponseEntity<JsonResultAo<BSoOrderVo>> insert(@RequestBody BSoOrderVo searchCondition) {
        InsertResultAo<BSoOrderVo> resultAo = service.startInsert(searchCondition);
        if (resultAo.isSuccess()) {
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectById(searchCondition.getId()),"新增成功"));
        } else {
            throw new InsertErrorException("新增成功，请编辑后重新新增。");
        }
    }

    @SysLogAnnotion("销售订单校验")
    @PostMapping("/validate")
    @ResponseBody
    @RepeatSubmitAnnotion
    public ResponseEntity<JsonResultAo<String>> checkLogic(@RequestBody(required = false) BSoOrderVo searchCondition) {
        CheckResultAo checkResultAo = service.checkLogic(searchCondition, searchCondition.getCheck_type());
        if (!checkResultAo.isSuccess()) {
            throw new BusinessException(checkResultAo.getMessage());
        }else {
            return ResponseEntity.ok().body(ResultUtil.OK("OK"));
        }
    }

    @SysLogAnnotion("根据查询条件，获取销售订单集合信息")
    @PostMapping("/pagelist")
    public ResponseEntity<JsonResultAo<IPage<BSoOrderVo>>> list(@RequestBody(required = false) BSoOrderVo searchCondition) {
        IPage<BSoOrderVo> list = service.selectPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }

    @SysLogAnnotion("销售订单管理，按应收退款条件获取列表信息")
    @PostMapping("/pagelist/byarrefund")
    @ResponseBody
    public ResponseEntity<JsonResultAo<IPage<BSoOrderVo>>> selectPagelistByArrefund(@RequestBody(required = false) BSoOrderVo searchCondition) {
        IPage<BSoOrderVo> list = service.selectPageByArrefund(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }

    @SysLogAnnotion("按销售订单合计")
    @PostMapping("/sum")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> querySum(@RequestBody(required = false) BSoOrderVo searchCondition) {
        BSoOrderVo result = service.querySum(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(result));
    }

    @SysLogAnnotion("销售订单管理，按应收退款条件汇总查询")
    @PostMapping("/sum/arrefund")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> querySumByArrefund(@RequestBody(required = false) BSoOrderVo searchCondition) {
        BSoOrderVo result = service.querySumByArrefund(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(result));
    }

    @SysLogAnnotion("根据查询条件，获取销售订单集合信息-结算信息")
    @PostMapping("/settle/pagelist")
    public ResponseEntity<JsonResultAo<IPage<BSoOrderVo>>> selectOrderListWithSettlePage(@RequestBody(required = false) BSoOrderVo searchCondition) {
        IPage<BSoOrderVo> list = service.selectOrderListWithSettlePage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }

    @SysLogAnnotion("按销售订单合计-结算信息")
    @PostMapping("/settle/sum")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> queryOrderListWithSettlePageSum(@RequestBody(required = false) BSoOrderVo searchCondition) {
        BSoOrderVo result = service.queryOrderListWithSettlePageSum(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(result));
    }

    @SysLogAnnotion("货权转移专用-根据查询条件，获取销售订单集合信息")
    @PostMapping("/pagelist/cargorighttransfer")
    public ResponseEntity<JsonResultAo<IPage<BSoOrderVo>>> selectOrderListForCargoRightTransferPage(@RequestBody(required = false) BSoOrderVo searchCondition) {
        IPage<BSoOrderVo> list = service.selectOrderListForCargoRightTransferPage(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }

    @SysLogAnnotion("货权转移专用-按销售订单合计")
    @PostMapping("/sum/cargorighttransfer")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> queryOrderListForCargoRightTransferPageSum(@RequestBody(required = false) BSoOrderVo searchCondition) {
        BSoOrderVo result = service.queryOrderListForCargoRightTransferPageSum(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(result));
    }

    @SysLogAnnotion("根据查询条件，获取销售订单信息")
    @PostMapping("/get")
    public ResponseEntity<JsonResultAo<BSoOrderVo>> get(@RequestBody(required = false) BSoOrderVo searchCondition) {
        BSoOrderVo vo = service.selectById(searchCondition.getId());
        return ResponseEntity.ok().body(ResultUtil.OK(vo));
    }

    @SysLogAnnotion("销售订单更新保存")
    @PostMapping("/save")
    @RepeatSubmitAnnotion
    public ResponseEntity<JsonResultAo<BSoOrderVo>> save(@RequestBody(required = false) BSoOrderVo searchCondition) {
        if(service.startUpdate(searchCondition).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(service.selectById(searchCondition.getId()),"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLogAnnotion("根据查询条件，销售订单逻辑删除")
    @PostMapping("/delete")
    public ResponseEntity<JsonResultAo<BSoOrderVo>> delete(@RequestBody(required = false) List<BSoOrderVo> searchCondition) {
        if(service.delete(searchCondition).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(null,"删除成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLogAnnotion("获取报表系统参数，并组装打印参数")
    @PostMapping("/print")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> print(@RequestBody(required = false) BSoOrderVo searchCondition) {
        BSoOrderVo printInfo = service.getPrintInfo(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(printInfo));
    }

    @SysLogAnnotion("销售订单，作废")
    @PostMapping("/cancel")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> cancel(@RequestBody(required = false) BSoOrderVo searchCondition) {
        if(service.cancel(searchCondition).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(null,"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }



    @SysLogAnnotion("销售订单，完成")
    @PostMapping("/finish")
    @ResponseBody
    public ResponseEntity<JsonResultAo<BSoOrderVo>> finish(@RequestBody(required = false) BSoOrderVo searchCondition) {
        if(service.finish(searchCondition).isSuccess()){
            return ResponseEntity.ok().body(ResultUtil.OK(null,"更新成功"));
        } else {
            throw new UpdateErrorException("保存的数据已经被修改，请查询后重新编辑更新。");
        }
    }

    @SysLogAnnotion("货权转移专用-获取销售订单明细数据")
    @PostMapping("/list/detail")
    public ResponseEntity<JsonResultAo<List<BSoOrderDetailVo>>> selectDetailData(@RequestBody(required = false) BSoOrderVo searchCondition) {
        List<BSoOrderDetailVo> list = service.selectDetailData(searchCondition);
        return ResponseEntity.ok().body(ResultUtil.OK(list));
    }

    @PostMapping("/export")
    @SysLogAnnotion("导出")
    public void export(@RequestBody(required = false) BSoOrderVo param, HttpServletResponse response) throws IOException {
        List<BSoOrderVo> result = service.selectExportList(param);
        // 创建导出的数据列表
        List<BSoOrderExportVo> exportDataList = new ArrayList<>();

        for (BSoOrderVo soOrderVo : result) {
            List<BSoOrderDetailVo> productList = JSON.parseArray(soOrderVo.getDetailListData().toString(), BSoOrderDetailVo.class);

            for (int i = 0; i < productList.size(); i++) {
                BSoOrderDetailVo BSoOrderDetailVo = productList.get(i);
                BSoOrderExportVo BSoOrderExportVo = new BSoOrderExportVo();
                BeanUtils.copyProperties(soOrderVo, BSoOrderExportVo);
                BeanUtils.copyProperties(BSoOrderDetailVo, BSoOrderExportVo);
                exportDataList.add(BSoOrderExportVo);
            }
        }

        List<String> strategy_1 = exportDataList.stream().map(BSoOrderExportVo::getCode).collect(Collectors.toList());
        List<BWoExportUtilVo> strategy_2 = exportDataList.stream().map(item -> new BWoExportUtilVo(item.getCode(), item.getSku_code())).collect(Collectors.toList());

        // 写sheet的时候注册相应的自定义合并单元格策略
        WriteSheet writeSheet = EasyExcel.writerSheet("销售订单").head(BSoOrderExportVo.class)
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 0))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 1))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 2))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 3))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 4))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 5))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 6))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 7))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 8))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 9))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 10))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 11))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 12))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 13))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 14))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 15))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 16))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 17))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 18))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 19))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 20))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 21))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 22))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 23))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 24))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 25))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 26))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 27,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 28,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 29,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 30,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 31,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 32,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_2, 33,"1"))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 34))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 35))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 36))
                .registerWriteHandler(new CustomMergeStrategy(strategy_1, 37))
                .build();
        new EasyExcelUtil<>(BSoOrderExportVo.class).exportExcel("销售订单" + DateTimeUtil.getDate(), exportDataList, response, writeSheet);
    }


}