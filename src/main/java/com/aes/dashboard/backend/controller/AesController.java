package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.AppConfig;
import com.aes.dashboard.backend.service.AppConfigService;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aes")
public class AesController {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AESDataService aesDataService;

    @RequestMapping(method = RequestMethod.GET, path = "/authToken")
    public ResponseEntity<AppConfig> getAuthToken() {
        AppConfig authTokenConfig = appConfigService.getAuthTokenConfig();
        return ResponseEntity.ok(authTokenConfig);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/authToken")
    public ResponseEntity<Void> updateAuthToken(@RequestBody AppConfig appConfig) {
        appConfigService.updateAuthToken(appConfig.getValue());
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/refreshAuthToken")
    public ResponseEntity<Void> refreshAuthToken() {
        aesDataService.refreshAuthToken();
        return ResponseEntity.ok(null);
    }
}
