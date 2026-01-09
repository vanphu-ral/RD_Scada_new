package io.bootify.my_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.bootify.my_app.domain.*;
import io.bootify.my_app.model.*;
import io.bootify.my_app.repos.*;
import io.bootify.my_app.rest.ProductOrderModelsResponse;
import io.bootify.planning.repos.PlanningWorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serial;
import java.nio.file.attribute.UserPrincipalLookupService;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.slf4j.LoggerFactory.getLogger;

@Service
@RequiredArgsConstructor
public class PlanningWOService {
    private static final Logger logger = getLogger(PlanningWOService.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
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
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    KafkaProducer kafkaProducer;
    @Autowired
    WoErrorHistoryService woErrorHistoryService;
    @Autowired
    LineProductionsModelsRepository lineProductionsModelsRepository;
    @Autowired
    DetailParamsFCTATERepository detailParamsFCTATERepository;

    public ProductOrderModelsResponse getWoInfo(Long id) {
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
//        response.setErrorCommonScadas(errorCommonScadaRepository.findAll());
        response.setPlanningWO(planningwoRepository.findById(id).orElse(null));
        response.getPlanningWO().setNumberOfOutputs(detailQuantityRepository.sumQuantityOutByWorkOrder(response.getPlanningWO().getWoId()));
        response.getPlanningWO().setNumberOfInputs(detailQuantityRepository.sumQuantityInByWorkOrder(response.getPlanningWO().getWoId()));
        response.setMachinesDetailResponses(machinesModelsRepository.getMachinesByWorkOrder(response.getPlanningWO().getWoId()));
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

    public ProductOrderModelsResponse getWoErrorCommonScadaInfoserialBoard(String serialBoard) {
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        List<ScanSerialChecksResponse> scanSerialCheck = scanSerialCheckRepository.getBySerialBoard(serialBoard);
        System.out.println("check :::" + scanSerialCheck.get(0).getWorkOrder());
        PlanningWO planningWO = planningwoRepository.findByWoId(scanSerialCheck.get(0).getWorkOrder());
        response.setPlanningWO(planningWO);
        response.setScanSerialChecks(scanSerialCheck);
        return response;
    }

    public ProductOrderModelsResponse getWoErrorCommonScadaInfoserialItem(String serialItem) {
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        List<ScanSerialChecksResponse> scanSerialCheck = scanSerialCheckRepository.getBySerialItem(serialItem);
        System.out.println("check 2:::" + scanSerialCheck.get(0).getWorkOrder());
        PlanningWO planningWO = planningwoRepository.findByWoId(scanSerialCheck.get(0).getWorkOrder());
        response.setPlanningWO(planningWO);
        response.setScanSerialChecks(scanSerialCheck);
        return response;
    }

    @Transactional
    public ResponseEntity<InsertMachineResultDTO> insertMachine(List<MachinesModelsDTO> machinesModels) {
        InsertMachineResultDTO result = new InsertMachineResultDTO();
        result.setSuccessMachines(new ArrayList<>());
        result.setFailedMachines(new HashMap<>());

        for (MachinesModelsDTO mm : machinesModels) {
            MachinesModels existingMachine = machinesModelsRepository.findByMachineName(mm.getMachineName());
            try {
                machinesModelsRepository.insertMachine(
                        mm.getMachineName(),
                        existingMachine.getMachineGroup().getMachineGroupId(),
                        existingMachine.getStageId(),
                        existingMachine.getLine().getLineId(),
                        mm.getStatus(),
                        mm.getWorkOrder()
                );
                result.getSuccessMachines().add(mm.getMachineName());
            } catch (Exception e) {
                result.getFailedMachines().put(mm.getMachineName(), e.getMessage());
            }
        }

        return ResponseEntity.ok(result);
    }

    public ResponseEntity<SerialCheckResponse> checkSerialItemExist(SerialCheckRequest request) {
        // stageId Pass first
        Integer lineId = machinesModelsRepository.getLineIDByMachineName(request.getMachineName());
        Integer stageId = Objects.equals(lineId, 58) ? 2 : 1;
        Integer code = 0;
        List<String> workOrders = scanSerialCheckRepository.getDistinctWorkOrder(request.getSerialItems());
        System.out.println("check work order :: " + workOrders.size());
        ATECheckRespone ateResult = scanSerialCheckRepository.getSerialStatusBySerialItem(request.getSerialItems());
        if (ateResult != null && ateResult.getSerialStatus().equals("NG")) {
            return ResponseEntity.ok(new SerialCheckResponse(1,
                    "Serial item  " + request.getSerialItems() + " FAIL o cong doan." + ateResult.getMachineName()));
//        }else if (workOrders.size() > 1) {
//            return ResponseEntity.ok(new SerialCheckResponse(1,
//                    "Serial item đang nằm trên " + workOrders.size() + " work order. Vui lòng kiểm tra lại."));
//        } else if (workOrders.size() == 1 && !workOrders.get(0).equals(request.getWorkOrder())) {
//            return ResponseEntity.ok(new SerialCheckResponse(1,
//                    "Serial item đã tồn tại trên work order khác : " + workOrders.get(0) + " . Vui lòng kiểm tra lại."));
        } else if (workOrders.size() == 1 && machinesModelsRepository.countByWorkOrderAndStatusIsZero(request.getWorkOrder()) == 0) {
            return ResponseEntity.ok(new SerialCheckResponse(1,
                    " Chưa chọn công đoạn cho Work Order : " + request.getWorkOrder() + " . Vui lòng kiểm tra lại."));
        } else {
            String result = "Kết quả kiểm tra Serial : ";
            if (request.getStage() > stageId) { // bo qua cong doan FCT ( stageID = 1 va serial stageId = 0)
//            List<MachinesModels> machinesModels = machinesModelsRepository.findAllByMachineNameAndStageId(request.getMachineName(), request.getStage()-1);
//                MachinesDetailResponse machinesDetailResponse = machinesModelsRepository.getMachineNamesByWorkOrder(request.getWorkOrder(), request.getStage());
                List<MachinesDetailResponse> machinesDetailResponses = machinesModelsRepository.getAllMachineNamesByWorkOrder(request.getWorkOrder(), request.getStage());
                if (machinesDetailResponses == null) {
//                result +=  "Không tìm thấy công đoạn ở stage trước: "+(request.getStage()-1);
//                code = 1;
                    MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(request.getMachineName());
                    List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.getAllByWorkOrderAndMachineId(
                            request.getWorkOrder(), machinesModels1.getMachineId());
                    boolean found = false;
                    for (ScanSerialCheck ssc : scanSerialCheck) {
                        if (ssc.getSerialItem().equals(request.getSerialItems())) {
                            found = true;
                            break;
                        }
                    }
                    if (found) {
                        code = 1;
                        result += "\n Đã tồn tại Serial Item: " + request.getSerialItems() + " ở công đoạn: " + machinesModels1.getMachineName() + " stage: " + (request.getStage());
                    }
                } else {
                    boolean found = false;
                    String machineName = "";
                    // Tìm bản ghi có stageId gần nhất (nhỏ hơn request)
                    Optional<MachinesDetailResponse> nearestResponse = machinesDetailResponses.stream()
                            .filter(m -> m.getStageId() < request.getStage()) // Lấy các stage nhỏ hơn 5
                            .max(Comparator.comparingInt(MachinesDetailResponse::getStageId)); // Lấy thằng lớn nhất trong số đó

                    if (nearestResponse.isPresent()) {
                        MachinesDetailResponse target = nearestResponse.get();
                        int targetStageId = target.getStageId();

                        // Tiếp tục xử lý với targetStageId này (ví dụ: lấy list máy của stage này)
                        List<MachinesDetailResponse> filteredList = machinesDetailResponses.stream()
                                .filter(m -> m.getStageId() == targetStageId)
                                .collect(Collectors.toList());

                        // Thực hiện logic tìm ScanSerialCheck ở đây...
                        for (MachinesDetailResponse machinesModels2 : filteredList) {
                            MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(machinesModels2.getMachineName());
                            List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.getAllByWorkOrderAndMachineId(
                                    request.getWorkOrder(), machinesModels1.getMachineId());
                            for (ScanSerialCheck ssc : scanSerialCheck) {
                                if (ssc.getSerialItem().equals(request.getSerialItems())) {
                                    machineName = "";
                                    found = true;
                                    break;
                                } else {
                                    machineName += machinesModels1.getMachineName();
                                }
                            }
                        }
                    }
                    if (!found) {
                        code = 1;
                        result += "\n Không tìm thấy Serial Item: " + request.getSerialItems() + " o cong doan : " + machineName;
                    }
                }
                if (code == 1) {
                    ChatMessage message = new ChatMessage();
                    message.setType(ChatMessage.MessageType.CHAT);
                    message.setSender("Server");
                    message.setContent(result);
                    message.setWorkOrder(request.getWorkOrder());
                    message.setId(woErrorHistoryService.insertError(message));
                    kafkaProducer.sendMessage("scada-giam-sat", result);
                    messagingTemplate.convertAndSend("/topic/public", message);
                    System.out.println("Đã gửi: " + message.getWorkOrder());
                }
            } else {
                result += "\n Stage hiện tại là 0, không cần kiểm tra stage trước";
            }
            return ResponseEntity.ok(new SerialCheckResponse(code, result));
        }

    }
    public ResponseEntity<SerialCheckResponse> checkSerialItemExistBymode(SerialCheckRequest request) {
        // stageId Pass first
        Integer lineId = machinesModelsRepository.getLineIDByMachineName(request.getMachineName());
        Integer stageId = Objects.equals(lineId, 58) ? 2 : 1;
        Integer code = 0;
            String result = "Kết quả kiểm tra Serial : ";
        List<String> workOrders = scanSerialCheckRepository.getDistinctWorkOrder(request.getSerialItems());
        if (request.getType() == 0) {
        if (request.getMachineType() == 1 || request.getMachineType() == 2) {
            this.saveDetailParamsFCTATE(request);
            this.saveScanSerialCheck(
                    machinesModelsRepository.findByMachineName(request.getMachineName()),
                    request,
                    1
            );
        }
        String otherWOs = workOrders.stream()
                .filter(wo -> !wo.equals(request.getWorkOrder()))
                .collect(Collectors.joining(", "));
        System.out.println("check work order :: "+ workOrders.size());
        ATECheckRespone ateResult = scanSerialCheckRepository.getSerialStatusBySerialItem(request.getSerialItems());
        ATECheckRespone stageResult = scanSerialCheckRepository.getSerialStatusBySerialItemAndMachineName(request.getSerialItems(),request.getMachineName());
        if(ateResult != null && ateResult.getSerialStatus().equals("NG")){
            return ResponseEntity.ok(new SerialCheckResponse(1,
                    "Serial item  " + request.getSerialItems() + " FAIL o cong doan." + ateResult.getMachineName()));
        }else if(stageResult !=null && stageResult.equals("NG")){
            return ResponseEntity.ok(new SerialCheckResponse(1,
                    "Serial item  " + request.getSerialItems() + " FAIL o cong doan." + stageResult.getMachineName()));
//        } else if (workOrders.size() > 1) {
//            return ResponseEntity.ok(new SerialCheckResponse(1,
//                    "Serial item đang nằm trên " + workOrders.size() + " work order. Vui lòng kiểm tra lại."));
//        } else if (!otherWOs.isEmpty()) {
//            return ResponseEntity.ok(new SerialCheckResponse(1,
//                    "Serial item đã tồn tại trên work order khác : " +  otherWOs + " . Vui lòng kiểm tra lại."));
        } else if (workOrders.size() == 1 && machinesModelsRepository.countByWorkOrderAndStatusIsZero(request.getWorkOrder()) == 0) {
            return ResponseEntity.ok(new SerialCheckResponse(1,
                    " Chưa chọn công đoạn cho Work Order : " + request.getWorkOrder() + " . Vui lòng kiểm tra lại."));
        } else {
            if (request.getStage() > stageId) { // bo qua cong doan FCT ( stageID = 1 va serial stageId = 0)
                System.out.println("check stage :: "+ request.getStage() + " > " + stageId);
//            List<MachinesModels> machinesModels = machinesModelsRepository.findAllByMachineNameAndStageId(request.getMachineName(), request.getStage()-1);
//                MachinesDetailResponse machinesDetailResponse = machinesModelsRepository.getMachineNamesByWorkOrder(request.getWorkOrder(), request.getStage());
                    List<MachinesDetailResponse> machinesDetailResponses = machinesModelsRepository.getAllMachineNamesByWorkOrder(request.getWorkOrder(), request.getStage());
                    if (machinesDetailResponses == null) {
//                result +=  "Không tìm thấy công đoạn ở stage trước: "+(request.getStage()-1);
//                code = 1;
                        MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(request.getMachineName());
                        List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.getAllByWorkOrderAndMachineId(
                                request.getWorkOrder(), machinesModels1.getMachineId());
                        boolean found = false;
                        for (ScanSerialCheck ssc : scanSerialCheck) {
                            if (ssc.getSerialItem().equals(request.getSerialItems())) {
                                found = true;
                                break;
                            }
                        }
                        if (found) {
                            code = 1;
                            result += "\n Đã tồn tại Serial Item: " + request.getSerialItems() + " ở công đoạn: " + machinesModels1.getMachineName() + " stage: " + (request.getStage());
                        } else {
                            this.saveScanSerialCheck(machinesModels1, request, 0);
                        }
                    } else {
                        boolean found = false;
                        String machineName = "";
                        // Tìm bản ghi có stageId gần nhất (nhỏ hơn request)
                        Optional<MachinesDetailResponse> nearestResponse = machinesDetailResponses.stream()
                                .filter(m -> m.getStageId() < request.getStage()) // Lấy các stage nhỏ hơn 5
                                .max(Comparator.comparingInt(MachinesDetailResponse::getStageId)); // Lấy thằng lớn nhất trong số đó

                        if (nearestResponse.isPresent()) {
                            MachinesDetailResponse target = nearestResponse.get();
                            int targetStageId = target.getStageId();

                            // Tiếp tục xử lý với targetStageId này (ví dụ: lấy list máy của stage này)
                            List<MachinesDetailResponse> filteredList = machinesDetailResponses.stream()
                                    .filter(m -> m.getStageId() == targetStageId)
                                    .collect(Collectors.toList());

                            // Thực hiện logic tìm ScanSerialCheck ở đây...
                            for (MachinesDetailResponse machinesModels2 : filteredList) {
                                MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(machinesModels2.getMachineName());
                                List<ScanSerialCheck> scanSerialCheck = scanSerialCheckRepository.getAllByWorkOrderAndMachineId(
                                        request.getWorkOrder(), machinesModels1.getMachineId());
                                for (ScanSerialCheck ssc : scanSerialCheck) {
                                    if (ssc.getSerialItem().equals(request.getSerialItems())) {
                                        machineName = "";
                                        found = true;
                                        break;
                                    } else {
                                        if(machineName.contains(machinesModels1.getMachineName())){
                                            // do nothing
                                        }else{
                                        machineName += machinesModels1.getMachineName();
                                        }
                                    }
                                }
                            }
                        }
                        if (!found) {
                            code = 1;
                            result += "\n Không tìm thấy Serial Item: " + request.getSerialItems() + " ở công đoạn : " + machineName;
                        } else {
                            MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(request.getMachineName());
                            ScanSerialCheck existingRecord = scanSerialCheckRepository.getByWorkOrderAndMachineIdAndSerialItem(
                                    request.getWorkOrder(),
                                    machinesModels1.getMachineId(),
                                    request.getSerialItems()
                            );
                            if (existingRecord != null) {
                                code = 1;
                                result += "\n Đã tồn tại Serial Item: " + request.getSerialItems() + " ở công đoạn: " + machinesModels1.getMachineName() + " stage: " + (request.getStage());
                            } else {
                                this.saveScanSerialCheck(machinesModels1, request, 0);
                            }
                        }
                    }
                    if (code == 1) {
                        this.sendMessage(result, request.getWorkOrder());
                    }
                } else {
                    result += "\n Stage hiện tại là " + (stageId - 1) + " , không cần kiểm tra stage trước";
                    MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(request.getMachineName());
                    Integer countExist = scanSerialCheckRepository.countByWorkOrderAndMachineIdAndSerialItem(
                            request.getWorkOrder(), machinesModels1.getMachineId(), request.getSerialItems());
                    if (countExist >= 1) { // da ton tai
                        code = 1;
                        result = "Đã tồn tại Serial Item: " + request.getSerialItems() + " ở công đoạn: " + machinesModels1.getMachineName() + " stage: " + (request.getStage());
                        this.sendMessage(result, request.getWorkOrder());
                        // update
                        Integer saveCode = request.getMachineType() == 1 ? 1 : request.getMachineType() == 2 ? 1 : 2;
                        this.saveScanSerialCheck(machinesModelsRepository.findByMachineName(request.getMachineName()), request, saveCode);
                    } else if (request.getStatus().equals("NG")){
                        code = 1;
                        result = "Serial : " + request.getSerialItems() + " bị lỗi ở công đoạn: " + machinesModels1.getMachineName() + " stage: " + (request.getStage());
                        this.sendMessage(result,request.getWorkOrder());
                    } else{ // chua ton tai
                        this.saveScanSerialCheck(machinesModels1, request, 0);
                    }
                }
                return ResponseEntity.ok(new SerialCheckResponse(code, result));
            }
        } else if (request.getType() == 1) {
            if (workOrders.size() == 1 && machinesModelsRepository.countByWorkOrderAndStatusIsZero(request.getWorkOrder()) == 0) {
                return ResponseEntity.ok(new SerialCheckResponse(1,
                        " Chưa chọn công đoạn cho Work Order : " + request.getWorkOrder() + " . Vui lòng kiểm tra lại."));
            } else {
                if (request.getStage() > stageId) { // bo qua cong doan FCT ( stageID = 1 va serial stageId = 0)
                    System.out.println("check stage :: " + request.getStage() + " > " + stageId);
                    List<MachinesDetailResponse> machinesDetailResponses = machinesModelsRepository.getAllMachineNamesByWorkOrder(request.getWorkOrder(), request.getStage());
                        MachinesModels machinesModels = machinesModelsRepository.findByMachineName(request.getMachineName());
                    if(machinesDetailResponses == null){
                        List<ScanSerialCheck> scanSerialChecks = scanSerialCheckRepository.getBySerialBoardAndWorkOrderAndMachineId(
                                request.getSerialBoard(), request.getWorkOrder(), machinesModels.getMachineId());
                        if(scanSerialChecks != null && scanSerialChecks.size() >0){
                            code =1;
                            result = "Đã tồn tại Serial Board: " + request.getSerialBoard() + " ở công đoạn: " + machinesModels.getMachineName() + " stage: " + (request.getStage());
                        }
                    }else {
                        boolean found = false;
                        String machineName = "";
                        // Tìm bản ghi có stageId gần nhất (nhỏ hơn request)
                        Optional<MachinesDetailResponse> nearestResponse = machinesDetailResponses.stream()
                                .filter(m -> m.getStageId() < request.getStage()) // Lấy các stage nhỏ hơn 5
                                .max(Comparator.comparingInt(MachinesDetailResponse::getStageId)); // Lấy thằng lớn nhất trong số đó

                        if (nearestResponse.isPresent()) {
                            MachinesDetailResponse target = nearestResponse.get();
                            int targetStageId = target.getStageId();

                            // Tiếp tục xử lý với targetStageId này (ví dụ: lấy list máy của stage này)
                            List<MachinesDetailResponse> filteredList = machinesDetailResponses.stream()
                                    .filter(m -> m.getStageId() == targetStageId)
                                    .collect(Collectors.toList());

                            // Thực hiện logic tìm ScanSerialCheck ở đây...
                            for (MachinesDetailResponse machinesModels2 : filteredList) {
                                MachinesModels machinesModels1 = machinesModelsRepository.findByMachineName(machinesModels2.getMachineName());
                                List<ScanSerialCheck> scanSerialChecks = scanSerialCheckRepository.getBySerialBoardAndWorkOrderAndMachineId(
                                        request.getSerialBoard(), request.getWorkOrder(), machinesModels1.getMachineId());
                                if(scanSerialChecks != null && scanSerialChecks.size() >0){
                                    machineName = "";
                                    this.saveListSerialItem(scanSerialChecks, request, machinesModels);
                                    found = true;
                                    break;
                                } else {
                                    machineName += machinesModels1.getMachineName() + " - ";
                                }
                            }
                        }
                        if (found == false) {
                            code =1;
                            result = "Không tìm thấy Serial Board: " + request.getSerialBoard() + " o cong doan : " + machineName;
                        }
                    }
                } else {
                    // stageId Pass first
                    MachinesDetailResponse machinesDetailResponse = machinesModelsRepository.getMachineNamesByWorkOrder(request.getWorkOrder(), request.getStage());
                    if(machinesDetailResponse !=null){
                        MachinesModels machinesModels = machinesModelsRepository.findByMachineName(machinesDetailResponse.getMachineName());
                        List<ScanSerialCheck> scanSerialChecks = scanSerialCheckRepository.getBySerialBoardAndWorkOrderAndMachineId(
                                request.getSerialBoard(), request.getWorkOrder(), machinesModels.getMachineId());
                        if(scanSerialChecks != null && scanSerialChecks.size() >0){
                            this.saveListSerialItem(scanSerialChecks, request, machinesModels);
                        }
                    }
                }
            }
        }

                return ResponseEntity.ok(new SerialCheckResponse(code, result));

    }
    public void saveListSerialItem(List<ScanSerialCheck> scanSerialChecks, SerialCheckRequest request,MachinesModels machinesModels) {
        for(ScanSerialCheck ssc : scanSerialChecks){
            SerialCheckRequest newRequest = new SerialCheckRequest();
            newRequest.setMachineName(request.getMachineName());
            newRequest.setWorkOrder(request.getWorkOrder());
            newRequest.setSerialBoard(request.getSerialBoard());
            newRequest.setSerialItems(ssc.getSerialItem());
            newRequest.setStatus(request.getStatus());
            newRequest.setTimeScan(request.getTimeScan());
            this.saveScanSerialCheck(machinesModels, newRequest, 0);

        }
    }
    public void sendMessage(String result, String workOrder) {
        ChatMessage message = new ChatMessage();
        message.setType(ChatMessage.MessageType.CHAT);
        message.setSender("Server");
        message.setContent(result);
        message.setWorkOrder(workOrder);
        message.setId(woErrorHistoryService.insertError(message));
        kafkaProducer.sendMessage("scada-giam-sat", result);
        messagingTemplate.convertAndSend("/topic/public", message);
        System.out.println("Đã gửi: " + message.getWorkOrder());
    }

    public void saveScanSerialCheck(MachinesModels machinesModels, SerialCheckRequest request, Integer code) {
        // 1. Khai báo Formatter
        // [.SSS] cho phép có hoặc không có 3 chữ số mili giây
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[.SSS]");

// Bây giờ cả 2 chuỗi dưới đây đều parse thành công:
// "2025-12-30 01:07:08.000" -> OK
// "2025-12-30 01:07:08"     -> OK
        LocalDateTime parsedTime = LocalDateTime.parse(request.getTimeScan(), formatter);

        if (code == 0) {
            // Logic INSERT: Luôn tạo mới
            ScanSerialCheck scanSerialCheck1 = new ScanSerialCheck();
            scanSerialCheck1.setMachine(machinesModels);
            scanSerialCheck1.setSerialBoard(request.getSerialBoard());
            scanSerialCheck1.setSerialItem(request.getSerialItems());
            scanSerialCheck1.setSerialStatus(request.getStatus());
            scanSerialCheck1.setTimeScan(parsedTime);
            scanSerialCheck1.setWorkOrder(request.getWorkOrder());

            scanSerialCheckRepository.save(scanSerialCheck1);
        } else if (code == 1) {
            // Logic UPDATE: Phải tìm đúng bản ghi cũ dựa trên bộ 3 điều kiện
            ScanSerialCheck existingRecord = scanSerialCheckRepository.getByWorkOrderAndMachineIdAndSerialItem(
                    request.getWorkOrder(),
                    machinesModels.getMachineId(),
                    request.getSerialItems()
            );

            if (existingRecord != null) {
                // Cập nhật trên bản ghi đã tìm thấy (đã có ID)
                String serialStatus = request.getStatus().equals("OK") ? "OK" : "NG";
                existingRecord.setSerialStatus(serialStatus);

                // Cập nhật thời gian check (nếu bạn dùng chung cột timeScan thì sửa lại cho đúng)
                existingRecord.setTimeCheck(parsedTime);
                String resultCheck = request.getStatus().equals("OK") ? "PASS" : "FAIL";
                existingRecord.setResultCheck(resultCheck);
                // Vì existingRecord có ID từ DB, save() sẽ thực hiện lệnh UPDATE thay vì INSERT
                scanSerialCheckRepository.save(existingRecord);
            }
        }
    }

    public void saveDetailParamsFCTATE(SerialCheckRequest request) {
        // Lưu DetailParamsFCTATE
        DetailParamsFCTATE detailParamsFCTATE = new DetailParamsFCTATE();
        detailParamsFCTATE.setDetailParams(request.getDetailParams());
        detailParamsFCTATE.setSerialItem(request.getSerialItems());
        detailParamsFCTATE.setSerialBoard(request.getSerialBoard());
        detailParamsFCTATE.setWorkOrder(request.getWorkOrder());
        detailParamsFCTATE.setResults(request.getStatus());
        detailParamsFCTATE.setMachineType(request.getMachineType());
        detailParamsFCTATE.setMachineName(request.getMachineName());
        detailParamsFCTATERepository.save(detailParamsFCTATE);
    }

    public ProductOrderModelsResponse getWoDetaillnfo(Long id) {
        ProductOrderModelsResponse response = new ProductOrderModelsResponse();
        // Lấy thông tin Work Order
        PlanningWO planningWO = planningwoRepository.findById(id).orElse(null);
        LineProductionsModels lineProductionsModels = new LineProductionsModels();
        lineProductionsModels = lineProductionsModelsRepository.findAllByDescriptions(planningWO.getLineId());
//        response.setPlanningWO(planningWO);
        // Lấy thông tin Production Order Models
        List<ProductionOrderModelDetail> productionOrderModelDetails = new ArrayList<>();
        List<ProductionOrderModels> productionOrderModelsList = productionOrderModelsRepository.findAllByWorkOrder(planningWO.getWoId());
        List<MachinesModels> machinesModelsList = machinesModelsRepository.findByMachineGroupIdAndFixedLineId(lineProductionsModels.getLineId());
        for (MachinesModels pom : machinesModelsList) {
            ProductionOrderModelDetail detail = new ProductionOrderModelDetail();
            // Lấy thông tin Machine Group Detail
            MachineGroupDetail machineGroupDetail = new MachineGroupDetail();
            List<MachineDetail> machineDetails = new ArrayList<>();
            machineGroupDetail.setMachineTypesModels(machineTypesModelsService.mapToDTO(
                    machineTypesModelsRepository.findById(pom.getMachineGroup().getMachineGroupId()).orElse(null),
                    new MachineTypesModelsDTO()));
            detail.setProductionOrderModels(productionOrderModelsService.mapToDTO(productionOrderModelsRepository.getByWorkOrderAndMachineGroupID(
                    planningWO.getWoId(), pom.getMachineGroup().getMachineGroupId()
            ), new ProductionOrderModelsDTO()));
            // Lấy danh sách máy móc thuộc nhóm máy và lineId = 58
            System.out.println("CHeck machine detail lenght :: " + machinesModelsList.size() + "line ID ::" + lineProductionsModels.getLineId());
//            for (MachinesModels mm : machinesModelsList) {
            MachineDetail machineDetail = new MachineDetail();
            // Lấy thông tin của máy
            machineDetail.setMachine(machinesModelsService.mapToDTO(pom, new MachinesModelsDTO()));
            //   Lấy thông tin quantity của máy
            List<DetailQuantity> detailQuantities = detailQuantityRepository.findAllByWorkOrderAndMachineName(
                    planningWO.getWoId(), machineDetail.getMachine().getMachineName());
            List<DetailQuantityDTO> detailQuantityDTOS = new ArrayList<>();
            for (DetailQuantity dq : detailQuantities) {
                detailQuantityDTOS.add(detailQuantityService.mapToDTO(dq, new DetailQuantityDTO()));
            }
            // Lấy thông tin lỗi của máy
            List<ErrorResponse> errorModels = errorModelRepository.getErrorResponsesByWorkOrderAndMachineNameAndStageID(
                    planningWO.getWoId(), machineDetail.getMachine().getMachineName(), machineDetail.getMachine().getStageId());
            machineDetail.setDetailQuantity(detailQuantityDTOS);
            machineDetail.setErrors(errorModels);
            machineDetails.add(machineDetail);
//            }
            machineGroupDetail.setMachineDetails(machineDetails);
            detail.setMachineGroupDetails(machineGroupDetail);
            productionOrderModelDetails.add(detail);
        }
        response.setProductionOrderModelDetails(productionOrderModelDetails);
        return response;
    }

    public ProductOrderModelsResponse getWoErrorInfo(Long id) {
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
        if (filter.getPlanningWorkOrderId() != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("planningWorkOrderId"), filter.getPlanningWorkOrderId()));
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
        Page<PlanningWO> page = planningwoRepository.findAll(spec, pageable);
        for (PlanningWO p : page.getContent()) {
            p.setNumberOfOutputs(detailQuantityRepository.sumQuantityOutByWorkOrder(p.getWoId()));
            p.setNumberOfInputs(detailQuantityRepository.sumQuantityInByWorkOrder(p.getWoId()));
            p.setStatus(productionOrderModelsRepository.getStatusByWorkOrderWithMaxStage(p.getWoId()));
        }
        return page;
    }

    public ResponseEntity<?> create(PlanningWO planningWO) {
        if (planningwoRepository.findByWoId(planningWO.getWoId()) != null) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "message", "Work Order đã tồn tại",
                            "data", planningWO
                    ));
        } else {
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

    public ResponseEntity<?> update(PlanningWO planningWO) {
        try {
            PlanningWO updatedWO = planningwoRepository.save(planningWO);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(Map.of(
                            "message", "Cập nhật Work Order thành công",
                            "data", updatedWO
                    ));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                            "message", "Lỗi khi cập nhật Work Order",
                            "error", e.getMessage()
                    ));
        }
    }
}
