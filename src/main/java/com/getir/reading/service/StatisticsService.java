package com.getir.reading.service;

import com.getir.reading.repository.StatisticsCustomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StatisticsService {

    private final Logger logger = LoggerFactory.getLogger(StatisticsService.class);

    private final StatisticsCustomRepository statisticsCustomRepository;

    @Autowired
    public StatisticsService(StatisticsCustomRepository statisticsCustomRepository) {
        this.statisticsCustomRepository = statisticsCustomRepository;
    }

    public List<Map<String, Object>> getMonthlyReport() {
        logger.info("getMonthlyReport is called.");
        return statisticsCustomRepository.getMonthlyReport();
    }
}
