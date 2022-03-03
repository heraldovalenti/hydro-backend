package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.model.Basin;
import com.aes.dashboard.backend.service.BasinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/basin")
public class BasinController {

    @Autowired
    private BasinService basinService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Basin>> basinList() {
        List<Basin> basinList = basinService.basinList();
        return ResponseEntity.ok(basinList);
    }

}
