package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.dto.RequestTimePeriod;
import com.aes.dashboard.backend.dto.StreamLevel;
import com.aes.dashboard.backend.service.StreamLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stream-level")
public class StreamLevelController {

    @Autowired
    private StreamLevelService streamLevelService;

    @RequestMapping(method = RequestMethod.GET, value = "/{stationId}")
    public ResponseEntity<StreamLevel> streamLevelForStation(
            @PathVariable Long stationId,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod period = RequestTimePeriod.of(from, to);
        StreamLevel result = streamLevelService.streamLevelForStation(stationId, period);
        return ResponseEntity.ok(result);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<StreamLevel>> streamLevels(
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to) {
        RequestTimePeriod period = RequestTimePeriod.of(from, to);
        List<StreamLevel> result = streamLevelService.streamLevels(period);
        return ResponseEntity.ok(result);
    }

}
