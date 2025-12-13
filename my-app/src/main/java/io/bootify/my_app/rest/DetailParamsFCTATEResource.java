package io.bootify.my_app.rest;

import io.bootify.my_app.domain.DetailParamsFCTATE;
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

//    @GetMapping("/search")
//    public List<DetailParamsFCTATE> search(@RequestParam String q) {
//        return service.search(q);
//    }

    @GetMapping("/search-details")
    public ResponseEntity<List<DetailParamsFCTATE>> searchDetailParamsBySerial(
            @RequestParam final String serial) {

        List<DetailParamsFCTATE> results = service.getDetailParamsBySerial(serial);

        if (results.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(results);
    }
}
