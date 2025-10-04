package io.bootify.my_app.service;

import io.bootify.my_app.domain.PlanningWO;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.model.*;
import io.bootify.my_app.repos.*;
import io.bootify.my_app.rest.ProductOrderModelsResponse;
import io.bootify.planning.repos.PlanningWorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PlanningWOService {
    @Autowired
    PlanningwoRepository planningwoRepository;
    @Autowired
    ProductionOrderModelsService productionOrderModelsService;
    @Autowired
    ProductionOrderModelsRepository productionOrderModelsRepository;
    @Autowired
    MachineTypesModelsService machineTypesModelsService;
    @Autowired
    MachineTypesModelsRepository machineTypesModelsRepository;
    @Autowired
    ErrorModelService errorModelService;
    @Autowired
    ErrorModelRepository errorModelRepository;
    @Autowired
    DetailQuantityService detailQuantityService;
    @Autowired
    DetailQuantityRepository detailQuantityRepository;
    @Autowired
    ScanSerialCheckService scanSerialCheckService;
    @Autowired
    ScanSerialCheckRepository scanSerialCheckRepository;
    public ProductOrderModelsResponse getWoInfo (Long id){
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        response.setPlanningWO(planningwoRepository.findById(id).orElse(null));
        // Lấy thông tin Production Order Models
        List<ProductionOrderModelDetail> productionOrderModelDetails = new ArrayList<>();
        List<ProductionOrderModels> productionOrderModelsList = productionOrderModelsRepository.findAllByWorkOrder(response.getPlanningWO().getWoId());
        for (ProductionOrderModels pom : productionOrderModelsList) {
            ProductionOrderModelDetail detail = new ProductionOrderModelDetail();
            productionOrderModelsService.mapToDTO(pom, new ProductionOrderModelsDTO());
            // Lấy thông tin Machine Group Detail
            MachineGroupDetail machineGroupDetail = new MachineGroupDetail();
            machineGroupDetail.setMachineTypesModels( machineTypesModelsService.mapToDTO(
                    machineTypesModelsRepository.findById(pom.getMachineGroup().getMachineGroupId()).orElse(null),
                    new MachineTypesModelsDTO()));
            detail.setProductionOrderModels(productionOrderModelsService.mapToDTO(pom, new ProductionOrderModelsDTO()));
            detail.setMachineGroupDetails(machineGroupDetail);
            productionOrderModelDetails.add(detail);
        }
        response.setProductionOrderModelDetails(productionOrderModelDetails);
        //
        response.setScanSerialChecks(scanSerialCheckRepository.findAllByWorkOrder(response.getPlanningWO().getWoId()).stream()
                .map(scanSerialCheck -> scanSerialCheckService.mapToDTO(scanSerialCheck, new ScanSerialCheckDTO()))
                .toList());
        return response;
    }
    public Page<PlanningWO> getFilteredPlanningWOs(PlanningWOFilter filter, Pageable pageable) {
        Specification<PlanningWO> spec = Specification.where(null);

        if (filter.getBranchCode() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("branchCode"), filter.getBranchCode()));
        }
        if (filter.getProductCode() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("productCode"), filter.getProductCode()));
        }

        if (filter.getStatus() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("status"), filter.getStatus()));
        }
        // Add more filters as needed...

        return planningwoRepository.findAll(spec, pageable);
    }
    public ResponseEntity<?> create(PlanningWO planningWO) {
        try {
            PlanningWO savedWO = planningwoRepository.save(planningWO);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Tạo Work Order thành công",
                            "data", savedWO
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Lỗi khi tạo Work Order",
                            "error", e.getMessage()
                    ));
        }
    }
}
