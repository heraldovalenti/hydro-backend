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

    //@Test
    public void basicTest() {
        List<DataItem> result = service.getLatestData();
        for(DataItem item : result) {
            System.out.println(item.toString());
        }
        Assertions.assertEquals(341, result.size());
    }

    @Test
    public void testAuthTest1() {
        service.refreshAuthToken(
                "77u/PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48U1A+VjEyLDBoLmZ8bWVtYmVyc2hpcHx1cm4lM2FzcG8lM2FndWVzdCNoZXJhbGRvdmFsZW50aUBnbWFpbC5jb20sMCMuZnxtZW1iZXJzaGlwfHVybiUzYXNwbyUzYWd1ZXN0I2hlcmFsZG92YWxlbnRpQGdtYWlsLmNvbSwxMzI5MjE4MjM5MTAwMDAwMDAsMCwxMzI5MjI2ODQ5MTczNzM4ODksMC4wLjAuMCwxMDI2LDlmNGZmMjM5LTFjY2UtNDI3Zi1iMmI5LWQzYjdkNDg1MDJjYiwsLDEyNTQyYmEwLTEwNjktMTAwMC05ZDYzLTJhOThkNjI2YWU4OCwxMjU0MmJhMC0xMDY5LTEwMDAtOWQ2My0yYTk4ZDYyNmFlODgsR2NVVnVvYTUzVWljaUVIUFdPTGRYZywxMzI5MjE4MjA5MTczNzM4ODksMCwwLCwsLDI2NTA0Njc3NDM5OTk5OTk5OTksMCwsLCwsLCwwLCxUTGZLa24zZVZkYnlreGUzbjNwZTFGb0RSbDh6ZVgyMUlBamdScDZUZVIrcUlaejFFZkJWejhIR1l5dlNqY3pPbUt1UXBEMUwvcXlGR3BZRnVBcG5oUTA5THJuM2p4OXB0ZGEySXJtT2JQUml1TGo2bExSaW5kTk9kcWN4OWJNMGlEY3JrZVRvaDUxeThEV2M0VjdSWFhVb3RRRmtqSlhjOHA3ZFVRcWlzbjZrd0hPeTdPVWFFaGRvQ2JqVDdtRXlQcHVuL2gyaW9OSXNpQUE0L3Qybkh2SWN1b001RmQ1NkZjVVBqSVBIQ1NwUTdaNHdlSXlaYnpnVVJ1eHlXL1JXWFZEcWU1eGZvLzB4cURuUXJzYXZuRzdaV3FVU2tZTDRvbWlHRzNYd2l6Rk9UZkVPaDRSWkx6S3c0LzB0TnVTNlBlR2sxSDA2TG93VEt3Zit2NG9BTWc9PTwvU1A+"
        );
    }

    @Test
    public void testAuthTest2() {
        service.refreshAuthToken(
                "77u/PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0idXRmLTgiPz48U1A+VjEyLDBoLmZ8bWVtYmVyc2hpcHx1cm4lM2FzcG8lM2FndWVzdCNoZXJhbGRvdmFsZW50aUBnbWFpbC5jb20sMCMuZnxtZW1iZXJzaGlwfHVybiUzYXNwbyUzYWd1ZXN0I2hlcmFsZG92YWxlbnRpQGdtYWlsLmNvbSwxMzI5MjI5MTY4NjAwMDAwMDAsMCwxMzI5MjM3Nzc4NjE0NjU3MjMsMC4wLjAuMCwxMDI2LDlmNGZmMjM5LTFjY2UtNDI3Zi1iMmI5LWQzYjdkNDg1MDJjYiwsLDRkYmMyYmEwLWEwOWUtMTAwMC04ODdiLWE0NGUxYWI1ZmM2Niw0ZGJjMmJhMC1hMDllLTEwMDAtODg3Yi1hNDRlMWFiNWZjNjYsdUFid1hhWW1vRTZTLzBrcnhyU1dqUSwxMzI5MjI5MTM4NjE0NjU3MjMsMCwwLCwsLDI2NTA0Njc3NDM5OTk5OTk5OTksMCwsLCwsLCwwLCxtQWYrSGc5RGhUaXcvWDRLQWxwcE5jOU4rbUtuaUU4Q3JsbW1uaGc4WDFDL21tMTFKd21tUGZhYzZRYy9ycGNxMmpDRVJRU2ZHaEN3MkJjY2dtb0ZIZExvZ3JsOW5uemxwNFpESFUxajdNMFFia2c4MFBlYmZILytQNUNtZGV2TTFuemsxVEtVVk5vZGtnSW1nbWM4VnNNYXNLZGV3dzRFcUcvaVEwRHlCdzZ6Sjhxd2JDdDZraUtDeUEvdlN4RFlWeE5oY3NiTVRqUXJKWTNQdXY4US94bXlVSGh0SkFVV1d2WEZGYzVKanZUWktwWGJVNWJmUG8xVWdzMzFoSjlkMk9zblczTXhnTlhJWHJXRUpjQ1JoOXN1ZWtxT2ZZaVNXVmx3dzVWQmdhKzFYQkJLOEU2NHVIbW1yWGVqT3p0aDJhajQ4QlFqR1gzRDA1TThTRytZNGc9PTwvU1A+"
        );
    }

}