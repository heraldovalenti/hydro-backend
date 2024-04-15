package com.aes.dashboard.backend.service.aesLatestData;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class AESDataServiceTest {

    @Autowired
    private AESDataService service;

    @Test
    public void basicTest() {
        List<DataItem> result = service.getLatestData(true);
        for(DataItem item : result) {
            System.out.println(item.toString());
        }
        Assertions.assertEquals(341, result.size());
    }

    @Test
    public void multipleRefreshTest() {
        String fedAuth = "77u/PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48U1A+VjEyLDBoLmZ8bWVtYmVyc2hpcHx1cm4lM2FzcG8lM2FndWVzdCNuZmd1aWxsZW5AaG90bWFpbC5jb20sMCMuZnxtZW1iZXJzaGlwfHVybiUzYXNwbyUzYWd1ZXN0I25mZ3VpbGxlbkBob3RtYWlsLmNvbSwxMzI5NTM2NjM3OTAwMDAwMDAsMCwxMzI5NjI1Nzk3MTY2MTc3MTksMC4wLjAuMCwxMDI3LDlmNGZmMjM5LTFjY2UtNDI3Zi1iMmI5LWQzYjdkNDg1MDJjYiwsLDhmMzAzN2EwLWQwNGItMTAwMC1hNzEwLWNiN2QxNDM4N2YyNiwyNWU3MzhhMC1lMDliLTEwMDAtZTFiYS1lMTcwOTQ0MWRlOGQsYXd6UHRYOHYzMENMY1FUWjNzUDg3ZywxMzI5NTM2NjA3OTk3NDY0NDIsMCwwLCwsLDI2NTA0Njc3NDM5OTk5OTk5OTksMCwsLCwsLCwwLCxVYUpMZGxiMEs4MTI0d1lPN1VVcmpzb1VBejg5SzU0NFE1YnRKeW41K0RiL2VrRWt0S1U5cW5iSUNhV3ViL2NEL2orMk9Od1hXUmIvb0ZYQklhdnNydjJaWWlMeWhrVG9nYitDaUl1SHprK2F2VUxCVGZLNVMwb0ZDSU5PdkVZdERQcmc2K2QzbUJRZUNPSHppNDlGMVIycENkQkRhR1VYeE9Ydld6NmwrS3pnSUpxY0JHalpycnU2MkZtajNwcHRaU0hmWi9DZ2l2amkxaVJ6WFZVWnlFOGdQVHQ3UzJISjRnUFRKODFVcEZVWSt5RHJZNnVlM0htVUxCMmZicDVuTjlWL2pOZVNwd2Z1SEljYUlaQUEyaHRPYlIwR2NVcXczRFg4UTU3S0pYNWcxYVdCQ0Mxc250K1ZLWXJZQWVkU2pFdmJPbUx4bFdicTVTczBTbUlPbnc9PTwvU1A+";
        AuthTokens authTokens = new AuthTokens();
        authTokens.setFedAuth(fedAuth);
        service.refreshAuthTokens(authTokens);
        service.refreshAuthTokens(authTokens);
    }

}