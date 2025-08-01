package com.xinyirun.scm.core.app.serviceimpl.common.autocode;

import com.xinyirun.scm.bean.app.ao.result.AppUpdateResultAo;
import com.xinyirun.scm.bean.entity.sys.syscode.SCodeEntity;
import com.xinyirun.scm.common.constant.DictConstant;
import com.xinyirun.scm.core.app.service.common.autocode.AppIAutoCodeService;
import com.xinyirun.scm.core.app.service.sys.platform.syscode.AppISCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName: TenantAutoCode
 * @Description: 自动生成编码：客户类
 */
@Component
public class AppMCustomerAutoCodeServiceImpl implements AppIAutoCodeService {

    @Autowired
    AppISCodeService service;

    @Override
    public SCodeEntity autoCode() {
        String type = DictConstant.DICT_SYS_CODE_TYPE_M_CUSTOMER;
        AppUpdateResultAo<SCodeEntity> upd = service.createCode(type);
        if(upd.isSuccess()){
            return upd.getData();
        }
        return null;
    }
}
