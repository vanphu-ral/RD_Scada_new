package io.bootify.my_app.rest;

import io.bootify.my_app.domain.DetailParamsFCTATE;
import io.bootify.my_app.model.DetailParamsFCTATEDTO;
import io.bootify.my_app.service.DetailParamsFCTATEService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/detail-params")
public class DetailParamsFCTATEResource {

    private final DetailParamsFCTATEService service;

    public DetailParamsFCTATEResource(DetailParamsFCTATEService service) {
        this.service = service;
    }

    @GetMapping
    public List<DetailParamsFCTATE> getList() {
        return service.getAll();
    }

    @GetMapping("/search-details")
    public ResponseEntity<List<DetailParamsFCTATE>> searchDetailParamsBySerial(
            @RequestParam final String serial) {

        List<DetailParamsFCTATE> results = service.getDetailParamsBySerial(serial);

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(results);
    }

    @GetMapping("/by-workorder")
    public ResponseEntity<List<DetailParamsFCTATEDTO>> getDetailParamsByWorkOrder(
            @RequestParam final String workOrder) {

        List<DetailParamsFCTATEDTO> results = service.getDetailParamsByWorkOrder(workOrder);

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(results);
    }

    @GetMapping("/{serial}")
    public ResponseEntity<List<DetailParamsFCTATEDTO>> getTestInfo(@PathVariable String serial) {
        List<DetailParamsFCTATEDTO> results = service.getTestInfoBySerial(serial);

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(results);
    }
}
