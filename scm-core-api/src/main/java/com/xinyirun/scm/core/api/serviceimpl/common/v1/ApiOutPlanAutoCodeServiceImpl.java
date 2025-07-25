package com.xinyirun.scm.core.api.serviceimpl.common.v1;

import com.xinyirun.scm.bean.system.ao.result.UpdateResultAo;
import com.xinyirun.scm.bean.entity.sys.syscode.SCodeEntity;
import com.xinyirun.scm.common.constant.DictConstant;
import com.xinyirun.scm.core.system.service.common.autocode.IAutoCodeService;
import com.xinyirun.scm.core.system.service.sys.platform.syscode.ISCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TenantAutoCode
 * @Description: 自动生成编码：入库单类
 * @Author: zxh
 * @date: 2019/12/13
 * @Version: 1.0
 */
@Component
public class ApiOutPlanAutoCodeServiceImpl implements IAutoCodeService {

    @Autowired
    ISCodeService service;

    @Override
    public SCodeEntity autoCode() {
        String type = DictConstant.DICT_SYS_CODE_TYPE_B_OUT_PLAN;
        UpdateResultAo<SCodeEntity> upd = service.createCode(type);
        if(upd.isSuccess()){
            return upd.getData();
        }
        return null;
    }
}
