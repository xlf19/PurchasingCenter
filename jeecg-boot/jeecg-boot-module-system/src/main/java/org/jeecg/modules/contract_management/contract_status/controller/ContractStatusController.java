package org.jeecg.modules.contract_management.contract_status.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract_management.contract_status.service.IContractStatusService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description: 合同状态
 * @Author: jeecg-boot
 * @Date: 2020-12-04
 * @Version: V1.0
 */
@Api(tags = "合同状态")
@RestController
@RequestMapping("/contractstatus/contractstatus")
@Slf4j
public class ContractStatusController  extends JeecgController<T, IContractStatusService> {
}
