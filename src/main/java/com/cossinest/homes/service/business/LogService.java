package com.cossinest.homes.service.business;

import com.cossinest.homes.domain.concretes.business.Log;
import com.cossinest.homes.domain.enums.LogEnum;
import com.cossinest.homes.repository.business.LogRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogService {

    private final LogRepository logRepository;

    public Log createLogEvent(Long userId, Long advertId, LogEnum log) {
        Log logReport=new Log();
        logReport.setLog(log);
        logReport.setAdvertId(advertId);
        logReport.setUserId(userId);
        return logRepository.save(logReport);

    }

}
