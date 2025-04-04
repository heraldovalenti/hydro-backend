package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.service.AppConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/config")
public class AppConfigController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppConfigController.class);

    @Autowired
    private AppConfigService appConfigService;

    @RequestMapping(method = RequestMethod.POST)
    public void updateAppConfig(
            @RequestParam("configName") String configName,
            @RequestParam("configValue") String configValue
    ) {
        appConfigService.updateConfig(configName, configValue);
    }
}
