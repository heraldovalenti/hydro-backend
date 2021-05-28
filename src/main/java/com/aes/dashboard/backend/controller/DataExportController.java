package com.aes.dashboard.backend.controller;

import com.aes.dashboard.backend.controller.entities.RequestTimePeriod;
import com.aes.dashboard.backend.exception.EntityNotFound;
import com.aes.dashboard.backend.model.MeasurementDimension;
import com.aes.dashboard.backend.model.Observation;
import com.aes.dashboard.backend.model.Station;
import com.aes.dashboard.backend.repository.MeasurementDimensionRepository;
import com.aes.dashboard.backend.repository.StationRepository;
import com.aes.dashboard.backend.service.DataExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

@RestController
@RequestMapping("/export")
public class DataExportController {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private MeasurementDimensionRepository dimensionRepository;

    @Autowired
    private ObservationController observationController;

    @Autowired
    private DataExportService dataExportService;

    @RequestMapping(method = RequestMethod.GET, value = "/observations/{stationId}/{dimensionId}")
    public void exportByStation(
            @PathVariable
                    Long stationId,
            @PathVariable
                    Long dimensionId,
            @PageableDefault(value = Integer.MAX_VALUE, page = 0)
            @SortDefault(sort = "time", direction = Sort.Direction.DESC)
                    Pageable pageable,
            @RequestParam(defaultValue = "") String from,
            @RequestParam(defaultValue = "") String to,
            HttpServletResponse response) throws IOException {
        Optional<Station> stationOpt = stationRepository.findById(stationId);
        Station station = stationOpt.orElseThrow(() -> new EntityNotFound(stationId, Station.class));
        Optional<MeasurementDimension> dimensionOpt = dimensionRepository.findById(dimensionId);
        MeasurementDimension dimension = dimensionOpt.orElseThrow(() -> new EntityNotFound(dimensionId, MeasurementDimension.class));
        RequestTimePeriod requestTimePeriod = dataExportService.parseRequestTimePeriod(from, to);
        Page<Observation> result = observationController.listByStationAndDimensionAndPeriod(
                stationId, dimensionId, pageable, requestTimePeriod);
        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, dataExportService.getContentDispositionHeader(station, dimension, requestTimePeriod));
        PrintWriter pw = response.getWriter();
        dataExportService.writeExportData(pw, result);
        pw.close();
    }

}
