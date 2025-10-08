package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailQuantity;
import io.bootify.my_app.domain.MachinesModels;
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
    MachinesModelsService machinesModelsService;
    @Autowired
    MachinesModelsRepository machinesModelsRepository;
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
    @Autowired
    ErrorCommonScadaRepository errorCommonScadaRepository;
    public ProductOrderModelsResponse getWoInfo (Long id){
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        response.setErrorCommonScadas(errorCommonScadaRepository.findAll());
        response.setPlanningWO(planningwoRepository.findById(id).orElse(null));
//        // Lấy thông tin Production Order Models
//        List<ProductionOrderModelDetail> productionOrderModelDetails = new ArrayList<>();
//        List<ProductionOrderModels> productionOrderModelsList = productionOrderModelsRepository.findAllByWorkOrder(response.getPlanningWO().getWoId());
//        for (ProductionOrderModels pom : productionOrderModelsList) {
//            ProductionOrderModelDetail detail = new ProductionOrderModelDetail();
//            productionOrderModelsService.mapToDTO(pom, new ProductionOrderModelsDTO());
//            // Lấy thông tin Machine Group Detail
//            MachineGroupDetail machineGroupDetail = new MachineGroupDetail();
//            List<MachineDetail> machineDetails = new ArrayList<>();
//            machineGroupDetail.setMachineTypesModels( machineTypesModelsService.mapToDTO(
//                    machineTypesModelsRepository.findById(pom.getMachineGroup().getMachineGroupId()).orElse(null),
//                    new MachineTypesModelsDTO()));
//            detail.setProductionOrderModels(productionOrderModelsService.mapToDTO(pom, new ProductionOrderModelsDTO()));
//            // Lấy danh sách máy móc thuộc nhóm máy và lineId = 58
//            List<MachinesModels> machinesModelsList = machinesModelsRepository.findByMachineGroupIdAndFixedLineId(
//                    machineGroupDetail.getMachineTypesModels().getMachineGroupId());
//            for (MachinesModels mm : machinesModelsList) {
//                MachineDetail machineDetail = new MachineDetail();
//                // Lấy thông tin của máy
//                machineDetail.setMachine(machinesModelsService.mapToDTO(mm, new MachinesModelsDTO()));
//               //   Lấy thông tin quantity của máy
//                List<DetailQuantity> detailQuantities = detailQuantityRepository.findAllByWorkOrderAndMachineName(
//                        response.getPlanningWO().getWoId(),machineDetail.getMachine().getMachineName());
//                List<DetailQuantityDTO> detailQuantityDTOS = new ArrayList<>();
//                for (DetailQuantity dq : detailQuantities) {
//                    detailQuantityDTOS.add(detailQuantityService.mapToDTO(dq, new DetailQuantityDTO()));
//                }
//                // Lấy thông tin lỗi của máy
//                List<ErrorResponse> errorModels = errorModelRepository.getErrorResponsesByWorkOrderAndMachineNameAndStageID(
//                        response.getPlanningWO().getWoId(),machineDetail.getMachine().getMachineName(),machineDetail.getMachine().getStageId());
//                machineDetail.setDetailQuantity(detailQuantityDTOS);
//                machineDetail.setErrors(errorModels);
//                machineDetails.add(machineDetail);
//            }
//            machineGroupDetail.setMachineDetails(machineDetails);
//            detail.setMachineGroupDetails(machineGroupDetail);
//            productionOrderModelDetails.add(detail);
//        }
//        response.setProductionOrderModelDetails(productionOrderModelDetails);
//        //
//        response.setScanSerialChecks(scanSerialCheckRepository.getAllByWorkOrder(response.getPlanningWO().getWoId()));
        return response;
    }
    public ProductOrderModelsResponse getWoDetaillnfo (Long id){
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
            List<MachineDetail> machineDetails = new ArrayList<>();
            machineGroupDetail.setMachineTypesModels( machineTypesModelsService.mapToDTO(
                    machineTypesModelsRepository.findById(pom.getMachineGroup().getMachineGroupId()).orElse(null),
                    new MachineTypesModelsDTO()));
            detail.setProductionOrderModels(productionOrderModelsService.mapToDTO(pom, new ProductionOrderModelsDTO()));
            // Lấy danh sách máy móc thuộc nhóm máy và lineId = 58
            List<MachinesModels> machinesModelsList = machinesModelsRepository.findByMachineGroupIdAndFixedLineId(
                    machineGroupDetail.getMachineTypesModels().getMachineGroupId());
            for (MachinesModels mm : machinesModelsList) {
                MachineDetail machineDetail = new MachineDetail();
                // Lấy thông tin của máy
                machineDetail.setMachine(machinesModelsService.mapToDTO(mm, new MachinesModelsDTO()));
                //   Lấy thông tin quantity của máy
                List<DetailQuantity> detailQuantities = detailQuantityRepository.findAllByWorkOrderAndMachineName(
                        response.getPlanningWO().getWoId(),machineDetail.getMachine().getMachineName());
                List<DetailQuantityDTO> detailQuantityDTOS = new ArrayList<>();
                for (DetailQuantity dq : detailQuantities) {
                    detailQuantityDTOS.add(detailQuantityService.mapToDTO(dq, new DetailQuantityDTO()));
                }
                // Lấy thông tin lỗi của máy
                List<ErrorResponse> errorModels = errorModelRepository.getErrorResponsesByWorkOrderAndMachineNameAndStageID(
                        response.getPlanningWO().getWoId(),machineDetail.getMachine().getMachineName(),machineDetail.getMachine().getStageId());
                machineDetail.setDetailQuantity(detailQuantityDTOS);
                machineDetail.setErrors(errorModels);
                machineDetails.add(machineDetail);
            }
            machineGroupDetail.setMachineDetails(machineDetails);
            detail.setMachineGroupDetails(machineGroupDetail);
            productionOrderModelDetails.add(detail);
        }
        response.setProductionOrderModelDetails(productionOrderModelDetails);
        return response;
    }
    public ProductOrderModelsResponse getWoErrorInfo (Long id){
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        response.setPlanningWO(planningwoRepository.findById(id).orElse(null));
        //
        response.setScanSerialChecks(scanSerialCheckRepository.getAllByWorkOrder(response.getPlanningWO().getWoId()));
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
        if (filter.getWoId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("woId"), filter.getWoId()));
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
