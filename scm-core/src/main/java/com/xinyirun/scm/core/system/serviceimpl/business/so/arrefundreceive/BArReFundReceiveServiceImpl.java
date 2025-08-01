package com.xinyirun.scm.core.system.serviceimpl.business.so.arrefundreceive;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyirun.scm.bean.entity.business.so.arrefund.BArReFundEntity;
import com.xinyirun.scm.bean.entity.business.so.arrefundreceive.*;
import com.xinyirun.scm.bean.entity.sys.file.SFileEntity;
import com.xinyirun.scm.bean.entity.sys.file.SFileInfoEntity;
import com.xinyirun.scm.bean.system.ao.result.CheckResultAo;
import com.xinyirun.scm.bean.system.ao.result.InsertResultAo;
import com.xinyirun.scm.bean.system.ao.result.UpdateResultAo;
import com.xinyirun.scm.bean.system.result.utils.v1.CheckResultUtil;
import com.xinyirun.scm.bean.system.result.utils.v1.InsertResultUtil;
import com.xinyirun.scm.bean.system.result.utils.v1.UpdateResultUtil;
import com.xinyirun.scm.bean.system.vo.business.so.arrefund.BArReFundSourceAdvanceVo;
import com.xinyirun.scm.bean.system.vo.business.so.arrefund.BArReFundSourceVo;
import com.xinyirun.scm.bean.system.vo.business.so.arrefund.BArReFundTotalVo;
import com.xinyirun.scm.bean.system.vo.business.so.arrefundreceive.BArReFundReceiveAttachVo;
import com.xinyirun.scm.bean.system.vo.business.so.arrefundreceive.BArReFundReceiveDetailVo;
import com.xinyirun.scm.bean.system.vo.business.so.arrefundreceive.BArReFundReceiveVo;
import com.xinyirun.scm.bean.system.vo.master.cancel.MCancelVo;
import com.xinyirun.scm.bean.system.vo.sys.file.SFileInfoVo;
import com.xinyirun.scm.common.constant.DictConstant;
import com.xinyirun.scm.common.constant.SystemConstants;
import com.xinyirun.scm.common.exception.system.BusinessException;
import com.xinyirun.scm.common.utils.bean.BeanUtilsSupport;
import com.xinyirun.scm.core.system.mapper.business.so.arrefund.BArReFundMapper;
import com.xinyirun.scm.core.system.mapper.business.so.arrefund.BArReFundSourceAdvanceMapper;
import com.xinyirun.scm.core.system.mapper.business.so.arrefund.BArReFundSourceMapper;
import com.xinyirun.scm.core.system.mapper.business.so.arrefund.BArReFundTotalMapper;
import com.xinyirun.scm.core.system.mapper.business.so.arrefundreceive.*;
import com.xinyirun.scm.core.system.mapper.sys.file.SFileInfoMapper;
import com.xinyirun.scm.core.system.mapper.sys.file.SFileMapper;
import com.xinyirun.scm.core.system.service.business.so.arrefundreceive.IBArReFundReceiveService;
import com.xinyirun.scm.core.system.service.master.cancel.MCancelService;
import com.xinyirun.scm.core.system.serviceimpl.base.v1.common.fund.CommonFundServiceImpl;
import com.xinyirun.scm.core.system.serviceimpl.common.autocode.BArReFundReceiveAutoCodeServiceImpl;
import com.xinyirun.scm.core.system.utils.mybatis.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 退款单表 服务实现类
 * </p>
 *
 * @author xinyirun
 * @since 2025-02-26
 */
@Service
public class BArReFundReceiveServiceImpl extends ServiceImpl<BArRefundReceiveMapper, BArReFundReceiveEntity> implements IBArReFundReceiveService {

    @Autowired
    private BArRefundReceiveMapper mapper;

    @Autowired
    private BArReFundReceiveDetailMapper bArReFundReceiveDetailMapper;

    @Autowired
    private BArReFundReceiveAttachMapper bArReFundReceiveAttachMapper;

    @Autowired
    private BArReFundMapper bArReFundMapper;

    @Autowired
    private BArReFundSourceAdvanceMapper bArReFundSourceAdvanceMapper;

    @Autowired
    private SFileMapper fileMapper;

    @Autowired
    private SFileInfoMapper fileInfoMapper;

    @Autowired
    private BArReFundReceiveAutoCodeServiceImpl bArReFundReceiveAutoCodeService;

    @Autowired
    private CommonFundServiceImpl commonFundService;

    @Autowired
    private BArReFundSourceMapper bArReFundSourceMapper;

    @Autowired
    private BArReFundTotalMapper bArReFundTotalMapper;

    @Autowired
    private BArReFundReceiveSourceMapper bArReFundReceiveSourceMapper;

    @Autowired
    private BArReFundReceiveSourceAdvanceMapper bArReFundReceiveSourceAdvanceMapper;

    @Autowired
    private MCancelService mCancelService;

    /**
     * 初始化退款单数据
     * @param searchCondition 退款单Vo
     */
    private void initInsertArRefundReceiveData(BArReFundReceiveVo searchCondition) {
        // 注意：这里允许查询退款管理表，因为退款单是基于退款管理创建的
        // 但是查询后只用于初始化退款单数据，不应该在其他地方混用表映射
        if (searchCondition.getAr_refund_id() != null) {
            // 1、从退款管理表查询基础数据用于初始化
            // 这是业务上的合理关联，退款单需要从退款管理获取初始数据
            Object bArReFundVo = bArReFundMapper.selectId(searchCondition.getAr_refund_id());
            if (bArReFundVo != null) {
                // 使用反射或Map方式处理数据，避免直接依赖退款管理域的VO
                // 这里简化处理，实际应该使用更安全的方式
                searchCondition.setCode(bArReFundReceiveAutoCodeService.autoCode().getCode());
                searchCondition.setStatus(DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_ZERO); // 0-待退款：使用常量
                // 其他字段应该从前端传入或者通过安全的方式获取
            }
        }

        // 2、处理退款单明细数据初始化
        if (searchCondition.getBankData() != null) {
            // 获取单个明细数据
            BArReFundReceiveDetailVo receiveDetailVo = searchCondition.getBankData();
            // 设置退款单明细基本信息
            receiveDetailVo.setAr_refund_id(searchCondition.getAr_refund_id());
            receiveDetailVo.setAr_refund_code(searchCondition.getAr_refund_code());
            // 明细数据从前端传入，不需要从退款管理表中查询
        }
    }

    /**
     * 计算退款金额
     * 功能说明：计算退款金额汇总
     * @param searchCondition 退款单Vo
     */
    private void calcAmountInsertRefundReceive(BArReFundReceiveVo searchCondition) {
        BigDecimal refundableAmountTotal = BigDecimal.ZERO;
        BigDecimal refundedAmountTotal = BigDecimal.ZERO;
        BigDecimal refundAmountTotal = BigDecimal.ZERO;

        if (searchCondition.getBankData() != null) {
            // 获取单个明细数据
            BArReFundReceiveDetailVo detail = searchCondition.getBankData();
            // refunded_amount已退款金额=0
            detail.setRefunded_amount(BigDecimal.ZERO);
            
            // 计算各种金额
            if (detail.getRefundable_amount() != null) {
                refundableAmountTotal = detail.getRefundable_amount();
            }
            if (detail.getRefunded_amount() != null) {
                refundedAmountTotal = detail.getRefunded_amount();
            }
            if (detail.getOrder_amount() != null) {
                refundAmountTotal = detail.getOrder_amount();
            }
        }

        // 设置汇总金额
        searchCondition.setRefundable_amount_total(refundableAmountTotal); // 退款单计划退款总金额
        searchCondition.setRefunded_amount_total(refundedAmountTotal); // 退款单已退款总金额
        searchCondition.setRefunding_amount_total(refundAmountTotal); // 退款单本次退款总金额
    }

    /**
     * 保存退款单主表
     * @param vo 退款单Vo
     * @return 退款单Entity
     */
    private BArReFundReceiveEntity saveRefundReceiveMain(BArReFundReceiveVo vo) {
        BArReFundReceiveEntity bArReFundReceiveEntity = (BArReFundReceiveEntity) BeanUtilsSupport.copyProperties(vo, BArReFundReceiveEntity.class);
        bArReFundReceiveEntity.setId(null);
        bArReFundReceiveEntity.setStatus(DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_ZERO);
        int bArReFundReceive = mapper.insert(bArReFundReceiveEntity);
        if (bArReFundReceive == 0){
            throw new BusinessException("新增退款单，新增失败");
        }
        return bArReFundReceiveEntity;
    }

    /**
     * 保存退款单明细
     * @param vo 退款单Vo
     * @param bArReFundReceiveEntity 退款单Entity
     */
    private void saveRefundReceiveDetailList(BArReFundReceiveVo vo, BArReFundReceiveEntity bArReFundReceiveEntity) {
        // 从前端传入的明细数据创建退款单明细
        BArReFundReceiveDetailEntity bArReFundReceiveDetailEntity = (BArReFundReceiveDetailEntity) BeanUtilsSupport.copyProperties(vo.getBankData(), BArReFundReceiveDetailEntity.class);
        bArReFundReceiveDetailEntity.setAr_refund_receive_id(bArReFundReceiveEntity.getId());
        bArReFundReceiveDetailEntity.setAr_refund_receive_code(bArReFundReceiveEntity.getCode());
        bArReFundReceiveDetailEntity.setAr_refund_id(vo.getAr_refund_id());
        bArReFundReceiveDetailEntity.setAr_refund_code(vo.getAr_refund_code());

        // 设置vo.refund_order_amount给bArReFundReceiveDetailEntity.order_amount
        bArReFundReceiveDetailEntity.setOrder_amount(vo.getRefund_order_amount());

        /**
         * 新增操作，所以状态必定是0-待退款
         * refunded_amount=0
         * refunding_amount=order_amount
         * unrefund_amount=order_amount
         * cancel_amount=0
         */
        bArReFundReceiveDetailEntity.setRefunded_amount(BigDecimal.ZERO);
        bArReFundReceiveDetailEntity.setRefunding_amount(bArReFundReceiveDetailEntity.getOrder_amount());
        bArReFundReceiveDetailEntity.setUnrefund_amount(bArReFundReceiveDetailEntity.getOrder_amount());
        bArReFundReceiveDetailEntity.setCancel_amount(BigDecimal.ZERO);

        bArReFundReceiveDetailEntity.setId(null);
        int bArReFundReceiveDetail = bArReFundReceiveDetailMapper.insert(bArReFundReceiveDetailEntity);
        if (bArReFundReceiveDetail == 0){
            throw new BusinessException("新增退款单明细，新增失败");
        }
    }

    /**
     * 保存退款单附件
     * @param vo 退款单Vo
     * @param bArReFundReceiveEntity 退款单Entity
     */
    private void saveRefundReceiveAttach(BArReFundReceiveVo vo, BArReFundReceiveEntity bArReFundReceiveEntity) {
        if (CollectionUtil.isNotEmpty(vo.getPush_files())) {
            SFileEntity fileEntity = new SFileEntity();
            fileEntity.setSerial_id(bArReFundReceiveEntity.getId());
            fileEntity.setSerial_type(DictConstant.DICT_SYS_CODE_TYPE_B_AR_REFUND_RECEIVE);
            fileMapper.insert(fileEntity);
            for (SFileInfoVo docAttFile : vo.getPush_files()) {
                SFileInfoEntity fileInfoEntity = new SFileInfoEntity();
                docAttFile.setF_id(fileEntity.getId());
                BeanUtilsSupport.copyProperties(docAttFile, fileInfoEntity);
                fileInfoEntity.setFile_name(docAttFile.getFileName());
                fileInfoEntity.setId(null);
                fileInfoMapper.insert(fileInfoEntity);
            }
            BArReFundReceiveAttachEntity bArReFundReceiveAttachEntity = new BArReFundReceiveAttachEntity();
            bArReFundReceiveAttachEntity.setAr_refund_receive_id(bArReFundReceiveEntity.getId());
            bArReFundReceiveAttachEntity.setAr_refund_receive_code(bArReFundReceiveEntity.getCode());
            bArReFundReceiveAttachEntity.setOne_file(fileEntity.getId());
            int bArReFundReceiveAttach = bArReFundReceiveAttachMapper.insert(bArReFundReceiveAttachEntity);
            if (bArReFundReceiveAttach == 0) {
                throw new BusinessException("新增退款单附件，新增失败");
            }
        }
    }

    /**
     * 保存退款单源单
     * @param vo 退款单Vo
     * @param bArReFundReceiveEntity 退款单Entity
     */
    private void saveRefundReceiveSource(BArReFundReceiveVo vo, BArReFundReceiveEntity bArReFundReceiveEntity) {
        // 注意：这里允许查询退款管理的源单表，因为退款单需要复制退款管理的源单数据
        // 但是查询后立即转换为退款单域的实体，不应该在其他地方混用表映射
        if (vo.getAr_refund_id() != null) {
            // 从退款管理源单表查询数据，然后转换为退款单源单数据
            List<BArReFundSourceVo> arReFundSourceList = bArReFundSourceMapper.selectByArRefundId(vo.getAr_refund_id());
            if (CollectionUtil.isNotEmpty(arReFundSourceList)) {
                for (BArReFundSourceVo arReFundSourceVo : arReFundSourceList) {
                    // 使用反射或Map方式处理数据，避免直接依赖退款管理域的VO
                    // 这里简化处理，实际应该使用更安全的方式
                    BArReFundReceiveSourceEntity receiveSourceEntity = new BArReFundReceiveSourceEntity();
                    receiveSourceEntity.setAr_refund_receive_id(bArReFundReceiveEntity.getId());
                    receiveSourceEntity.setAr_refund_receive_code(bArReFundReceiveEntity.getCode());
                    receiveSourceEntity.setAr_refund_id(vo.getAr_refund_id());
                    receiveSourceEntity.setAr_refund_code(vo.getAr_refund_code());
                    // 其他字段应该通过安全的方式从源数据中获取
                    bArReFundReceiveSourceMapper.insert(receiveSourceEntity);
                }
            }
        }
    }

    /** 保存退款单关联源单-预收款 */
    private void saveRefundReceiveSourceAdvance(BArReFundReceiveVo vo, BArReFundReceiveEntity bArReFundReceiveEntity) {
        List<BArReFundSourceAdvanceVo> arReFundSourceAdvanceList = bArReFundSourceAdvanceMapper.selectByArRefundId(vo.getAr_refund_id());
        if (CollectionUtil.isNotEmpty(arReFundSourceAdvanceList)) {
            for (BArReFundSourceAdvanceVo arReFundSourceAdvanceVo : arReFundSourceAdvanceList) {
                BArReFundReceiveSourceAdvanceEntity receiveSourceAdvanceEntity = new BArReFundReceiveSourceAdvanceEntity();
                receiveSourceAdvanceEntity.setAr_refund_receive_id(bArReFundReceiveEntity.getId());
                receiveSourceAdvanceEntity.setAr_refund_receive_code(bArReFundReceiveEntity.getCode());
                receiveSourceAdvanceEntity.setAr_refund_id(arReFundSourceAdvanceVo.getAr_refund_id());
                receiveSourceAdvanceEntity.setAr_refund_code(arReFundSourceAdvanceVo.getAr_refund_code());
                receiveSourceAdvanceEntity.setType(arReFundSourceAdvanceVo.getType());
                receiveSourceAdvanceEntity.setSo_contract_id(arReFundSourceAdvanceVo.getSo_contract_id());
                receiveSourceAdvanceEntity.setSo_contract_code(arReFundSourceAdvanceVo.getSo_contract_code());
                receiveSourceAdvanceEntity.setSo_order_code(arReFundSourceAdvanceVo.getSo_order_code());
                receiveSourceAdvanceEntity.setSo_order_id(arReFundSourceAdvanceVo.getSo_order_id());
                receiveSourceAdvanceEntity.setSo_goods(arReFundSourceAdvanceVo.getSo_goods());
                receiveSourceAdvanceEntity.setOrder_amount(arReFundSourceAdvanceVo.getOrder_amount());
                receiveSourceAdvanceEntity.setAdvance_paid_total(arReFundSourceAdvanceVo.getAdvance_paid_total());
                receiveSourceAdvanceEntity.setAdvance_refund_amount_total(arReFundSourceAdvanceVo.getAdvance_refund_amount_total());

                bArReFundReceiveSourceAdvanceMapper.insert(receiveSourceAdvanceEntity);
            }
        }
    }

    /**
     * 退款单新增
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResultAo<BArReFundReceiveVo> startInsert(BArReFundReceiveVo vo) {
        // 1、校验
        checkInsertLogic(vo);
        
        // 2.保存退款单
        InsertResultAo<BArReFundReceiveVo> insertResultAo = insert(vo);

        // 3.total数据重算
        // commonTotalService.reCalculateAllTotalDataByArRefundReceiveId(vo.getId());
        
        return insertResultAo;
    }

    /**
     * 下推退款单
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public InsertResultAo<BArReFundReceiveVo> insert(BArReFundReceiveVo vo) {
        // 2、初始化数据
        initInsertArRefundReceiveData(vo);
        // 3、计算金额
        calcAmountInsertRefundReceive(vo);
        // 4、保存主表
        BArReFundReceiveEntity bArReFundReceiveEntity = saveRefundReceiveMain(vo);
        // 5、保存明细
        saveRefundReceiveDetailList(vo, bArReFundReceiveEntity);
        // 6、保存附件
        saveRefundReceiveAttach(vo, bArReFundReceiveEntity);
        // 7、保存源单
        saveRefundReceiveSource(vo, bArReFundReceiveEntity);
        // 8、保存源单-预收款
        saveRefundReceiveSourceAdvance(vo, bArReFundReceiveEntity);
        // 9、更新应收退款主表金额
        // updateArRefundingAmount(vo);
        // 10、资金流水监控（如需）
        // commonFundService.startArRefundReceiveFund(bArReFundReceiveEntity.getId());
        vo.setId(bArReFundReceiveEntity.getId());
        return InsertResultUtil.OK(vo);
    }

    /**
     * 列表查询
     * @param vo
     */
    @Override
    public IPage<BArReFundReceiveVo> selectPage(BArReFundReceiveVo vo) {
        // 分页条件
        Page<BArReFundReceiveVo> pageCondition = new Page(vo.getPageCondition().getCurrent(), vo.getPageCondition().getSize());
        // 通过page进行排序
        PageUtil.setSort(pageCondition, vo.getPageCondition().getSort());
        return mapper.selectPage(pageCondition, vo);
    }

    /**
     * 获取单条数据
     *
     * @param id
     */
    @Override
    public BArReFundReceiveVo selectById(Integer id) {
        BArReFundReceiveVo bArReFundReceiveVo = mapper.selectId(id);
        if (bArReFundReceiveVo == null) {
            return null;
        }
        
        // 1. 查询退款单明细数据（1:1关系）
        List<BArReFundReceiveDetailVo> receiveDetailVos = bArReFundReceiveDetailMapper.selectById(id);
        if (CollectionUtil.isNotEmpty(receiveDetailVos)) {
            // 根据业务设计，每个退款单只有一个明细记录，获取第一条记录
            BArReFundReceiveDetailVo detailVo = receiveDetailVos.get(0);
            bArReFundReceiveVo.setBankData(detailVo);
        }

        // 2. 查询退款单附件
        BArReFundReceiveAttachVo attachVo = bArReFundReceiveAttachMapper.selectByBArId(id);
        if (attachVo != null) {
            // 2.1 退款单附件（one_file）
            if (attachVo.getOne_file() != null) {
                SFileEntity fileEntity = fileMapper.selectById(attachVo.getOne_file());
                if (fileEntity != null) {
                    List<SFileInfoEntity> fileInfos = fileInfoMapper.selectList(new QueryWrapper<SFileInfoEntity>().eq("f_id", fileEntity.getId()));
                    List<SFileInfoVo> oneFiles = new ArrayList<>();
                    for (SFileInfoEntity fileInfo : fileInfos) {
                        SFileInfoVo fileInfoVo = (SFileInfoVo) BeanUtilsSupport.copyProperties(fileInfo, SFileInfoVo.class);
                        fileInfoVo.setFileName(fileInfoVo.getFile_name());
                        oneFiles.add(fileInfoVo);
                    }
                    attachVo.setOne_files(oneFiles);
                }
            }
            // 2.2 凭证附件（two_file）
            if (attachVo.getTwo_file() != null) {
                SFileEntity fileEntity = fileMapper.selectById(attachVo.getTwo_file());
                if (fileEntity != null) {
                    List<SFileInfoEntity> fileInfos = fileInfoMapper.selectList(new QueryWrapper<SFileInfoEntity>().eq("f_id", fileEntity.getId()));
                    List<SFileInfoVo> twoFiles = new ArrayList<>();
                    for (SFileInfoEntity fileInfo : fileInfos) {
                        SFileInfoVo fileInfoVo = (SFileInfoVo) BeanUtilsSupport.copyProperties(fileInfo, SFileInfoVo.class);
                        fileInfoVo.setFileName(fileInfoVo.getFile_name());
                        twoFiles.add(fileInfoVo);
                    }
                    attachVo.setTwo_files(twoFiles);
                }
            }
        }

        // 3. 处理凭证附件（向下兼容原有逻辑）
        if (bArReFundReceiveVo.getVoucher_file() != null) {
            SFileEntity file = fileMapper.selectById(bArReFundReceiveVo.getVoucher_file());
            if (file != null) {
                List<SFileInfoEntity> fileInfos = fileInfoMapper.selectList(new QueryWrapper<SFileInfoEntity>().eq("f_id", file.getId()));
                List<SFileInfoVo> voucherFiles = new ArrayList<>();
                for (SFileInfoEntity fileInfo : fileInfos) {
                    SFileInfoVo fileInfoVo = (SFileInfoVo) BeanUtilsSupport.copyProperties(fileInfo, SFileInfoVo.class);
                    fileInfoVo.setFileName(fileInfoVo.getFile_name());
                    voucherFiles.add(fileInfoVo);
                }
                bArReFundReceiveVo.setVoucher_files(voucherFiles);
            }
        }

        // 4. 查询是否存在作废记录
        if (DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_TWO.equals(bArReFundReceiveVo.getStatus())) {
            // 构造作废查询条件
            MCancelVo serialIdAndType = new MCancelVo();
            serialIdAndType.setSerial_id(bArReFundReceiveVo.getId());
            serialIdAndType.setSerial_type(DictConstant.DICT_SYS_CODE_TYPE_B_AR_REFUND_RECEIVE);
            MCancelVo mCancelVo = mCancelService.selectBySerialIdAndType(serialIdAndType);
            if (mCancelVo != null) {
                bArReFundReceiveVo.setCancel_reason(mCancelVo.getRemark());
                if (mCancelVo.getFile_id() != null) {
                    SFileEntity cancelFileEntity = fileMapper.selectById(mCancelVo.getFile_id());
                    if (cancelFileEntity != null) {
                        List<SFileInfoEntity> cancelFileInfos = fileInfoMapper.selectList(new QueryWrapper<SFileInfoEntity>().eq("f_id", cancelFileEntity.getId()));
                        List<SFileInfoVo> cancelFiles = new ArrayList<>();
                        for (SFileInfoEntity fileInfo : cancelFileInfos) {
                            SFileInfoVo fileInfoVo = (SFileInfoVo) BeanUtilsSupport.copyProperties(fileInfo, SFileInfoVo.class);
                            fileInfoVo.setFileName(fileInfoVo.getFile_name());
                            cancelFiles.add(fileInfoVo);
                        }
                        bArReFundReceiveVo.setCancel_files(cancelFiles);
                    }
                }
            }
        }

        return bArReFundReceiveVo;
    }

    /**
     * 凭证上传、完成退款
     * @param vo
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateResultAo<BArReFundReceiveVo> refundComplete(BArReFundReceiveVo vo) {
        // 1. 业务校验
        validateRefundComplete(vo);
        
        // 2. 更新退款单主表状态
        BArReFundReceiveEntity bArReFundReceiveEntity = updateRefundCompletionStatus(vo);
        
        // 3. 更新退款单明细表状态
        updateRefundCompletionDetails(vo.getId());
        
        // 4. 处理凭证附件上传
        processVoucherAttachment(bArReFundReceiveEntity, vo);
        
        // 5. 重算总金额数据
        recalculateTotalData(vo.getId());
        
        // 6. 更新应收退款主表状态
        updateArRefundReceiveStatus(bArReFundReceiveEntity.getAr_refund_id());
        
        // 7. 更新资金流水表
        updateFundFlow(vo.getId());
        
        vo.setId(bArReFundReceiveEntity.getId());
        return UpdateResultUtil.OK(vo);
    }

    /**
     * 根据应退金额和已退金额确定退款状态
     * @param refundAmount 应退总金额
     * @param refundedAmount 已退总金额
     * @return 退款状态：1-部分退款，2-已退款
     */
    private String determineRefundStatus(BigDecimal refundAmount, BigDecimal refundedAmount) {
        if (refundAmount == null || refundedAmount == null) {
            return null;
        }
        
        int comparison = refundAmount.compareTo(refundedAmount);
        if (comparison == 0) {
            // 应退总金额 = 已退总金额，设置为已退款
            return "2";
        } else if (comparison > 0) {
            // 应退总金额 > 已退总金额，设置为部分退款
            return "1";
        }
        
        return null;
    }

    /**
     * 作废 （只能作废已收款账单：实际交易金额 = 累计发生金额 -累计退款金额 -累计作废金额 -累计核销金额 +累计退款作废金额）
     * @param searchCondition
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public UpdateResultAo<BArReFundReceiveVo> cancel(BArReFundReceiveVo searchCondition) {
        
        CheckResultAo cr = checkLogic(searchCondition, CheckResultAo.CANCEL_CHECK_TYPE);
        if (!cr.isSuccess()) {
            throw new BusinessException(cr.getMessage());
        }

        // 1.作废退款单状态
        BArReFundReceiveEntity bArReFundReceiveEntity = mapper.selectById(searchCondition.getId());
        String originalStatus = bArReFundReceiveEntity.getStatus();
        
        bArReFundReceiveEntity.setStatus(DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_TWO);

        // 2.保存作废附件和作废原因到附件表
        BArReFundReceiveAttachVo attachVo = bArReFundReceiveAttachMapper.selectByBArId(searchCondition.getId());

        // 1.保存附件信息
        SFileEntity fileEntity = new SFileEntity();
        fileEntity.setSerial_id(searchCondition.getId());
        fileEntity.setSerial_type(DictConstant.DICT_SYS_CODE_TYPE_B_AR_REFUND_RECEIVE);
        fileEntity = insertCancelFile(fileEntity, searchCondition);

        // 2.增加作废记录
        MCancelVo mCancelVo = new MCancelVo();
        mCancelVo.setSerial_id(searchCondition.getId());
        mCancelVo.setSerial_type(SystemConstants.SERIAL_TYPE.B_AR_REFUND);
        mCancelVo.setFile_id(fileEntity.getId());
        mCancelVo.setRemark(searchCondition.getCancel_reason());
        mCancelService.insert(mCancelVo);

        int bArReFundReceive = mapper.updateById(bArReFundReceiveEntity);
        if (bArReFundReceive == 0){
            throw new BusinessException("作废，更新失败");
        }
        searchCondition.setId(bArReFundReceiveEntity.getId());

        // 更新退款单明细表状态
        List<BArReFundReceiveDetailVo> arReFundReceiveDetailVos = bArReFundReceiveDetailMapper.selectById(searchCondition.getId());
        if (CollectionUtil.isNotEmpty(arReFundReceiveDetailVos)) {
            // 根据新设计，每个退款单只有一个明细记录，获取第一条记录
            BArReFundReceiveDetailVo detailVo = arReFundReceiveDetailVos.get(0);
            BArReFundReceiveDetailEntity bArReFundReceiveDetailEntity = (BArReFundReceiveDetailEntity) BeanUtilsSupport.copyProperties(detailVo, BArReFundReceiveDetailEntity.class);
            /**
             * 作废操作，所以状态必定是2-作废
             * refunded_amount=0
             * refunding_amount=0
             * unrefund_amount=0
             * cancel_amount=if bArReFundReceiveEntity.status (源状态)= 1-已退款，则为refund_amount，否则为0
             */
            if (originalStatus.equals(DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_ONE)) {
                bArReFundReceiveDetailEntity.setCancel_amount(bArReFundReceiveDetailEntity.getOrder_amount());
            } else {
                bArReFundReceiveDetailEntity.setCancel_amount(BigDecimal.ZERO);
            }
            bArReFundReceiveDetailEntity.setRefunded_amount(BigDecimal.ZERO);
            bArReFundReceiveDetailEntity.setRefunding_amount(BigDecimal.ZERO);
            bArReFundReceiveDetailEntity.setUnrefund_amount(BigDecimal.ZERO);

            int bArReFundReceiveDetail = bArReFundReceiveDetailMapper.updateById(bArReFundReceiveDetailEntity);
            if (bArReFundReceiveDetail == 0){
                throw new BusinessException("作废退款单明细，更新失败");
            }
        }

        // 3.total数据重算
        // commonTotalService.reCalculateAllTotalDataByArRefundReceiveId(searchCondition.getId());

        return UpdateResultUtil.OK(searchCondition);
    }

    /**
     * 作废附件处理
     */
    public SFileEntity insertCancelFile(SFileEntity fileEntity, BArReFundReceiveVo vo) {
        // 其他附件新增
        if (vo.getCancel_files() != null && vo.getCancel_files().size() > 0) {
            // 主表新增
            fileMapper.insert(fileEntity);
            // 详情表新增
            for (SFileInfoVo other_file : vo.getCancel_files()) {
                SFileInfoEntity fileInfoEntity = new SFileInfoEntity();
                other_file.setF_id(fileEntity.getId());
                BeanUtilsSupport.copyProperties(other_file, fileInfoEntity);
                fileInfoEntity.setFile_name(other_file.getFileName());
                fileInfoEntity.setId(null);
                fileInfoMapper.insert(fileInfoEntity);
            }
        }
        return fileEntity;
    }

    /**
     * 附件逻辑 全删全增
     */
    public SFileEntity insertFile(SFileEntity fileEntity, List<SFileInfoVo> sFileInfoVos) {
        if (CollectionUtil.isNotEmpty(sFileInfoVos)){
            // 主表新增
            fileMapper.insert(fileEntity);
            // 详情表新增
            for (SFileInfoVo other_file : sFileInfoVos) {
                SFileInfoEntity fileInfoEntity = new SFileInfoEntity();
                other_file.setF_id(fileEntity.getId());
                BeanUtilsSupport.copyProperties(other_file, fileInfoEntity);
                fileInfoEntity.setFile_name(other_file.getFileName());
                fileInfoEntity.setId(null);
                fileInfoMapper.insert(fileInfoEntity);
            }
        }
        return fileEntity;
    }

    /**
     * 新增校验
     * @param vo 退款单Vo
     */
    private void checkInsertLogic(BArReFundReceiveVo vo) {
        CheckResultAo cr = checkLogic(vo, CheckResultAo.INSERT_CHECK_TYPE);
        if (!cr.isSuccess()) {
            throw new BusinessException(cr.getMessage());
        }
    }

    /**
     * 通用校验逻辑
     * @param vo 退款单Vo
     * @param checkType 校验类型
     * @return 校验结果
     */
    @Override
    public CheckResultAo checkLogic(BArReFundReceiveVo vo, String checkType) {
        switch (checkType) {
            case CheckResultAo.INSERT_CHECK_TYPE:
                // 新增校验逻辑
                // 1. 退款日期不能为空
                if (vo.getRefund_date() == null) {
                    return CheckResultUtil.NG("退款日期不能为空");
                }
                break;
            case CheckResultAo.UPDATE_CHECK_TYPE:
                // 更新校验逻辑
                // 1. ID不能为空
                if (vo.getId() == null) {
                    return CheckResultUtil.NG("退款单ID不能为空");
                }
                // 2. 退款日期不能为空
                if (vo.getRefund_date() == null) {
                    return CheckResultUtil.NG("退款日期不能为空");
                }
                break;
            case CheckResultAo.DELETE_CHECK_TYPE:
                // 删除校验逻辑
                // 1. ID不能为空
                if (vo.getId() == null) {
                    return CheckResultUtil.NG("退款单ID不能为空");
                }
                break;
            case CheckResultAo.FINISH_CHECK_TYPE:
                // 完成校验逻辑
                break;
            case CheckResultAo.CANCEL_CHECK_TYPE:
                // 作废校验逻辑
                // 无特殊校验
                break;
            default:
                break;
        }
        return CheckResultUtil.OK();
    }

    /**
     * 汇总查询
     * @param vo 查询条件
     * @return 汇总结果
     */
    @Override
    public BArReFundReceiveVo querySum(BArReFundReceiveVo vo) {
        return mapper.querySum(vo);
    }

    /**
     * 单条汇总查询
     * @param vo 查询条件
     * @return 汇总结果
     */
    @Override
    public BArReFundReceiveVo queryViewSum(BArReFundReceiveVo vo) {
        return mapper.queryViewSum(vo);
    }

    /**
     * 退款完成业务校验
     * @param vo 退款单信息
     */
    private void validateRefundComplete(BArReFundReceiveVo vo) {
        CheckResultAo cr = checkLogic(vo, CheckResultAo.FINISH_CHECK_TYPE);
        if (!cr.isSuccess()) {
            throw new BusinessException(cr.getMessage());
        }
    }

    /**
     * 更新退款单主表状态
     * @param vo 退款单信息
     * @return 更新后的退款单实体
     */
    private BArReFundReceiveEntity updateRefundCompletionStatus(BArReFundReceiveVo vo) {
        BArReFundReceiveEntity bArReFundReceiveEntity = mapper.selectById(vo.getId());
        bArReFundReceiveEntity.setStatus(DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_TWO);
        bArReFundReceiveEntity.setVoucher_remark(vo.getVoucher_remark());
        
        int updateResult = mapper.updateById(bArReFundReceiveEntity);
        if (updateResult == 0) {
            throw new BusinessException("退款单状态更新失败");
        }
        
        return bArReFundReceiveEntity;
    }

    /**
     * 更新退款单明细表状态（设置为已退款）
     * @param arRefundReceiveId 退款单ID
     */
    private void updateRefundCompletionDetails(Integer arRefundReceiveId) {
        List<BArReFundReceiveDetailVo> arRefundReceiveDetailVos = bArReFundReceiveDetailMapper.selectById(arRefundReceiveId);
        for (BArReFundReceiveDetailVo detailVo : arRefundReceiveDetailVos) {
            BArReFundReceiveDetailEntity bArReFundReceiveDetailEntity = (BArReFundReceiveDetailEntity) BeanUtilsSupport.copyProperties(detailVo, BArReFundReceiveDetailEntity.class);
            
            // 已退款操作，设置相关金额字段
            bArReFundReceiveDetailEntity.setRefunded_amount(bArReFundReceiveDetailEntity.getOrder_amount());  // 已退金额 = 本次退款金额
            bArReFundReceiveDetailEntity.setRefunding_amount(BigDecimal.ZERO);                             // 退款中金额清零
            bArReFundReceiveDetailEntity.setUnrefund_amount(BigDecimal.ZERO);                              // 未退金额清零
            bArReFundReceiveDetailEntity.setCancel_amount(BigDecimal.ZERO);                                // 作废金额清零
            
            int updateResult = bArReFundReceiveDetailMapper.updateById(bArReFundReceiveDetailEntity);
            if (updateResult == 0) {
                throw new BusinessException("退款单明细更新失败");
            }
        }
    }

    /**
     * 处理凭证附件上传
     * @param bArReFundReceiveEntity 退款单实体
     * @param vo 退款单VO
     */
    private void processVoucherAttachment(BArReFundReceiveEntity bArReFundReceiveEntity, BArReFundReceiveVo vo) {
        // 上传附件文件
        SFileEntity fileEntity = new SFileEntity();
        fileEntity.setSerial_id(bArReFundReceiveEntity.getId());
        fileEntity.setSerial_type(DictConstant.DICT_SYS_CODE_TYPE_B_AR_REFUND_RECEIVE);
        SFileEntity sFileEntity = insertFile(fileEntity, vo.getVoucher_files());

        // 更新或新增附件关联记录
        BArReFundReceiveAttachVo bArReFundReceiveAttachVo = bArReFundReceiveAttachMapper.selectByBArId(bArReFundReceiveEntity.getId());
        if (bArReFundReceiveAttachVo == null) {
            // 新增附件记录
            insertRefundReceiveAttachment(bArReFundReceiveEntity, sFileEntity);
        } else {
            // 更新附件记录
            updateRefundReceiveAttachment(bArReFundReceiveAttachVo, sFileEntity);
        }
    }

    /**
     * 重算总金额数据
     * @param arRefundReceiveId 退款单ID
     */
    private void recalculateTotalData(Integer arRefundReceiveId) {
//        commonFundService.reCalculateAllTotalDataByArRefundReceiveId(arRefundReceiveId);
    }

    /**
     * 更新应收退款主表的退款状态
     * @param arRefundId 应收退款主表ID
     */
    private void updateArRefundReceiveStatus(Integer arRefundId) {
        // 获取应收退款总表的应退金额
        BArReFundTotalVo bArReFundTotalVo = bArReFundTotalMapper.selectByArRefundId(arRefundId);
        if (bArReFundTotalVo == null) {
            return;
        }

        // 获取退款单的已退金额汇总（状态=1表示已退款）
        BArReFundReceiveVo refundAmountSummary = mapper.getSumAmount(arRefundId, DictConstant.DICT_B_AR_REFUND_RECEIVE_STATUS_ONE);
        BigDecimal refundedAmountTotal = refundAmountSummary != null && refundAmountSummary.getRefunded_amount_total() != null 
            ? refundAmountSummary.getRefunded_amount_total() : BigDecimal.ZERO;

        // 比较金额并更新应收退款主表状态
        BArReFundEntity bArReFundEntity = bArReFundMapper.selectById(arRefundId);
        if (bArReFundEntity == null) {
            return;
        }

        // 根据已退金额判断退款状态
        BigDecimal totalRefundableAmount = bArReFundTotalVo.getRefundable_amount_total() != null 
            ? bArReFundTotalVo.getRefundable_amount_total() : BigDecimal.ZERO;
        
        if (refundedAmountTotal.compareTo(totalRefundableAmount) >= 0) {
            // 已退金额 >= 应退金额，设置为已退款
            bArReFundEntity.setStatus(DictConstant.DICT_B_AR_REFUND_STATUS_TWO);
        } else if (refundedAmountTotal.compareTo(BigDecimal.ZERO) > 0) {
            // 已退金额 > 0，设置为部分退款
            bArReFundEntity.setStatus(DictConstant.DICT_B_AR_REFUND_STATUS_ONE);
        }
        
        bArReFundMapper.updateById(bArReFundEntity);
    }

    /**
     * 更新资金流水表
     * @param arRefundReceiveId 退款单ID
     */
    private void updateFundFlow(Integer arRefundReceiveId) {
//        commonFundService.increaseRefundAmount(arRefundReceiveId);
    }

    /**
     * 新增退款单附件记录
     * @param bArReFundReceiveEntity 退款单实体
     * @param sFileEntity 文件实体
     */
    private void insertRefundReceiveAttachment(BArReFundReceiveEntity bArReFundReceiveEntity, SFileEntity sFileEntity) {
        BArReFundReceiveAttachEntity bArReFundReceiveAttachEntity = new BArReFundReceiveAttachEntity();
        bArReFundReceiveAttachEntity.setTwo_file(sFileEntity.getId());
        bArReFundReceiveAttachEntity.setAr_refund_receive_code(bArReFundReceiveEntity.getCode());
        bArReFundReceiveAttachEntity.setAr_refund_receive_id(bArReFundReceiveEntity.getId());
        
        int insertResult = bArReFundReceiveAttachMapper.insert(bArReFundReceiveAttachEntity);
        if (insertResult == 0) {
            throw new BusinessException("退款单附件新增失败");
        }
    }

    /**
     * 更新退款单附件记录
     * @param bArReFundReceiveAttachVo 退款单附件VO
     * @param sFileEntity 文件实体
     */
    private void updateRefundReceiveAttachment(BArReFundReceiveAttachVo bArReFundReceiveAttachVo, SFileEntity sFileEntity) {
        BArReFundReceiveAttachEntity bArReFundReceiveAttachEntity = (BArReFundReceiveAttachEntity) BeanUtilsSupport.copyProperties(bArReFundReceiveAttachVo, BArReFundReceiveAttachEntity.class);
        bArReFundReceiveAttachEntity.setTwo_file(sFileEntity.getId());
        
        int updateResult = bArReFundReceiveAttachMapper.updateById(bArReFundReceiveAttachEntity);
        if (updateResult == 0) {
            throw new BusinessException("退款单附件更新失败");
        }
        
        bArReFundReceiveAttachVo.setTwo_file(sFileEntity.getId());
    }
}