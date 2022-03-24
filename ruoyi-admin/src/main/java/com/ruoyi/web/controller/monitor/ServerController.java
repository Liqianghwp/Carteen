package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.domain.BaseResult;
import com.ruoyi.framework.web.domain.Server;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author ruoyi
 */
@Api(value = "/monitor/server", tags = {"服务器监控"})
@RestController
@RequestMapping("/monitor/server")
public class ServerController {
    /**
     * 服务器信息
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "服务器信息", notes = "服务器信息", httpMethod = "GET")
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public BaseResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return BaseResult.success(server);
    }
}
