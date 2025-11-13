package io.bootify.my_app.service;

import io.bootify.my_app.domain.ScanCheckSerialLogs;
import io.bootify.my_app.repos.ScanCheckSerialLogsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ScanCheckSerialLogsService {
    @Autowired
    ScanCheckSerialLogsRepository scanCheckSerialLogsRepository;

    public void create (ScanCheckSerialLogs scanCheckSerialLogs){
        this.scanCheckSerialLogsRepository.save(scanCheckSerialLogs);
    }
    public List<ScanCheckSerialLogs> getAll(){
        return this.scanCheckSerialLogsRepository.findAll();
    }
    @Transactional
    public void delete(ScanCheckSerialLogs scanCheckSerialLogs){
        this.scanCheckSerialLogsRepository.deleteByTimeAndUserName(scanCheckSerialLogs.getUserName(),scanCheckSerialLogs.getTimeCheck());
    }
}
