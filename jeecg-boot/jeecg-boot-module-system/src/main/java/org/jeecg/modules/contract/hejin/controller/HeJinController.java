package org.jeecg.modules.contract.hejin.controller;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.formula.functions.T;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.contract.hejin.service.IHeJinService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="合金信息")
@RestController
@RequestMapping("/hejin/hejin")
@Slf4j
public class HeJinController extends JeecgController<T, IHeJinService> {
}
