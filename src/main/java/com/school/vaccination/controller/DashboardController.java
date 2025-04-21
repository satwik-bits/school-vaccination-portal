package com.school.vaccination.controller;

import com.school.vaccination.response.DashboardOverView;
import com.school.vaccination.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @PostMapping("/overview")
    public ResponseEntity<Object> getMetrics(){
        try {
            DashboardOverView dashboardOverView = dashboardService.getMetrics();
            return ResponseEntity.ok().body(dashboardOverView);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Some exception occurred while fetching information: "+e);
        }
    }
}
