package com.xinyirun.scm.core.system.serviceimpl.common.autocode;

import com.xinyirun.scm.bean.entity.sys.syscode.SCodeEntity;
import com.xinyirun.scm.bean.system.ao.result.UpdateResultAo;
import com.xinyirun.scm.common.constant.DictConstant;
import com.xinyirun.scm.core.system.service.common.autocode.IAutoCodeService;
import com.xinyirun.scm.core.system.service.sys.platform.syscode.ISCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TenantAutoCode
 * @Description: 自动生成编码：应付退款管理表
 */
@Component
public class BApReFundAutoCodeServiceImpl implements IAutoCodeService {

    @Autowired
    ISCodeService service;

    @Override
    public SCodeEntity autoCode() {
        String type = DictConstant.DICT_SYS_CODE_TYPE_B_AP_REFUND;
        UpdateResultAo<SCodeEntity> upd = service.createCode(type);
        if(upd.isSuccess()){
            return upd.getData();
        }
        return null;
    }
}
