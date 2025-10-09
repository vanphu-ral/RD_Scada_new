package io.bootify.my_app.service;

import io.bootify.my_app.domain.*;
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
//        List<ProductionOrderModels> productionOrderModelsList = productionOrderModelsRepository.findAllByWorkOrder(planningWO.getWoId());
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
//                        planningWO.getWoId(),machineDetail.getMachine().getMachineName());
//                List<DetailQuantityDTO> detailQuantityDTOS = new ArrayList<>();
//                for (DetailQuantity dq : detailQuantities) {
//                    detailQuantityDTOS.add(detailQuantityService.mapToDTO(dq, new DetailQuantityDTO()));
//                }
//                // Lấy thông tin lỗi của máy
//                List<ErrorResponse> errorModels = errorModelRepository.getErrorResponsesByWorkOrderAndMachineNameAndStageID(
//                        planningWO.getWoId(),machineDetail.getMachine().getMachineName(),machineDetail.getMachine().getStageId());
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
//        response.setScanSerialChecks(scanSerialCheckRepository.getAllByWorkOrder(planningWO.getWoId()));
        return response;
    }
    public  ProductOrderModelsResponse getWoErrorCommonScadaInfoserialBoard (String serialBoard){
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.findAllBySerialBoard(serialBoard);
        System.out.println("check :::"+scanSerialCheck.get(0).getWorkOrder());
        PlanningWO planningWO = planningwoRepository.findByWoId(scanSerialCheck.get(0).getWorkOrder());
        response.setPlanningWO(planningWO);
        response.setScanSerialChecks(scanSerialCheckRepository.getAllByWorkOrder(planningWO.getWoId()));
        return response;
    }
    public  ProductOrderModelsResponse getWoErrorCommonScadaInfoserialItem (String serialItem){
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.findAllBySerialItem(serialItem);
        System.out.println("check 2:::"+scanSerialCheck.get(0).getWorkOrder());
        PlanningWO planningWO = planningwoRepository.findByWoId(scanSerialCheck.get(0).getWorkOrder());
        response.setPlanningWO(planningWO);
        response.setScanSerialChecks(scanSerialCheckRepository.getAllByWorkOrder(planningWO.getWoId()));
        return response;
    }
//    public String checkSerialItemExist (String serialItem,String machineName,Integer stage,String workOrder){
//        if(stage >1){
//            List<MachinesModels> machinesModels = machinesModelsRepository.findAllByMachineNameAndStageId(machineName,stage-1);
//            if (machinesModels.isEmpty()){
//                return "Không tìm thấy máy ở stage trước: "+(stage-1);
//            }else {
//                for (MachinesModels machinesModels1 : machinesModels){
//                    List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.findAllByWorkOrderAndMachineId(
//                            workOrder,machinesModels1.getMachineId());
//                    boolean found = false;
//                    for (ScanSerialCheck ssc : scanSerialCheck){
//                        if (ssc.getSerialItem().equals(serialItem)){
//                            found = true;
//                            break;
//                        }
//                    }
//                    if (found){
//                        return "Tìm thấy Serial Item: "+serialItem+" ở máy: "+machinesModels1.getMachineName()+" stage: "+(stage-1);
//                    }
//                }
//            }
//        }else{
//            return "Stage hiện tại là 1, không cần kiểm tra stage trước";
//        }
//        List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.findAllBySerialItem(serialItem);
//        if (scanSerialCheck.isEmpty()){
//            return "Không tìm thấy Serial Item: "+serialItem;
//        }else {
//            return "Tìm thấy Serial Item: "+serialItem;
//        }
//    }
    public ProductOrderModelsResponse getWoDetaillnfo (Long id){
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        PlanningWO planningWO = planningwoRepository.findById(id).orElse(null);
//        response.setPlanningWO(planningWO);
        // Lấy thông tin Production Order Models
        List<ProductionOrderModelDetail> productionOrderModelDetails = new ArrayList<>();
        List<ProductionOrderModels> productionOrderModelsList = productionOrderModelsRepository.findAllByWorkOrder(planningWO.getWoId());
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
                        planningWO.getWoId(),machineDetail.getMachine().getMachineName());
                List<DetailQuantityDTO> detailQuantityDTOS = new ArrayList<>();
                for (DetailQuantity dq : detailQuantities) {
                    detailQuantityDTOS.add(detailQuantityService.mapToDTO(dq, new DetailQuantityDTO()));
                }
                // Lấy thông tin lỗi của máy
                List<ErrorResponse> errorModels = errorModelRepository.getErrorResponsesByWorkOrderAndMachineNameAndStageID(
                        planningWO.getWoId(),machineDetail.getMachine().getMachineName(),machineDetail.getMachine().getStageId());
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
//        response.setPlanningWO(planningwoRepository.findById(id).orElse(null));
        PlanningWO planningWO = planningwoRepository.findById(id).orElse(null);
        
        //
        response.setScanSerialChecks(scanSerialCheckRepository.getAllByWorkOrder(planningWO.getWoId()));
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
        if (filter.getSapWoId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("sapWoId"), filter.getSapWoId()));
        }
        if (filter.getProductOrderId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("productOrderId"), filter.getProductOrderId()));
        }
        if (filter.getGroupName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("groupName"), filter.getGroupName()));
        }
        if (filter.getGroupCode() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("groupCode"), filter.getGroupCode()));
        }
        if (filter.getProductName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("productName"), filter.getProductName()));
        }
        if (filter.getBranchName() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("branchName"), filter.getBranchName()));
        }
        if (filter.getLotNumber() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lotNumber"), filter.getLotNumber()));
        }
        if (filter.getWoId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("woId"), filter.getWoId()));
        }
        if (filter.getQuantityPlan() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("quantityPlan"), filter.getQuantityPlan()));
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
