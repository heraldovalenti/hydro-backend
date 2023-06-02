package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.controller.entities.AuthTokens;
import com.aes.dashboard.backend.service.AppConfigService;
import com.aes.dashboard.backend.service.aesLatestData.AESDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aes")
public class AesController {

    @Autowired
    private AppConfigService appConfigService;

    @Autowired
    private AESDataService aesDataService;

    @RequestMapping(method = RequestMethod.GET, path = "/authTokens")
    public ResponseEntity<AuthTokens> getAuthTokens() {
        AuthTokens authTokens = appConfigService.getAuthTokens();
        return ResponseEntity.ok(authTokens);
    }

    @RequestMapping(method = RequestMethod.PUT, path = "/authTokens")
    public ResponseEntity<Void> updateAuthTokens(@RequestBody AuthTokens authTokens) {
        appConfigService.updateFedAuth(authTokens.getFedAuth());
        appConfigService.updateRtFa(authTokens.getRtFa());
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/refreshAuthTokens")
    public ResponseEntity<Void> refreshAuthTokens() {
        aesDataService.refreshAuthTokens();
        return ResponseEntity.ok(null);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/refreshAuthTokens")
    public ResponseEntity<Void> setAuthTokens(@RequestBody AuthTokens authTokens) {
        aesDataService.refreshAuthTokens(authTokens);
        return ResponseEntity.ok(null);
    }
}
