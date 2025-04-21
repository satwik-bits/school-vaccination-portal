package com.school.vaccination.service;

import com.school.vaccination.response.DashboardOverView;
import org.springframework.stereotype.Service;

@Service
public interface DashboardService {

    DashboardOverView getMetrics();
}
