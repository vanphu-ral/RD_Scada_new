package io.bootify.my_app.rest;

import io.bootify.my_app.domain.ScanCheckSerialLogs;
import io.bootify.my_app.domain.ScanSerialCheck;
import io.bootify.my_app.service.ScanCheckSerialLogsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/scanCheckSerialLogs")
public class ScanCheckSerialLogsResource {
    @Autowired
    ScanCheckSerialLogsService scanCheckSerialLogsService;
    @PostMapping
    public void create(ScanCheckSerialLogs scanCheckSerialLogs){
        this.scanCheckSerialLogsService.create(scanCheckSerialLogs);
    }
    @GetMapping
    public List<ScanCheckSerialLogs> getAll(){
        return this.scanCheckSerialLogsService.getAll();
    }
    @DeleteMapping
    public void delete(ScanCheckSerialLogs scanCheckSerialLogs){
        this.scanCheckSerialLogsService.delete(scanCheckSerialLogs);
    }
}
