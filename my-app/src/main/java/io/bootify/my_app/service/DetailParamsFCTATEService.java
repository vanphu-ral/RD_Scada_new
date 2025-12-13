package io.bootify.my_app.service;

import io.bootify.my_app.domain.DetailParamsFCTATE;
import io.bootify.my_app.domain.ScanSerialCheck;
import io.bootify.my_app.model.ScanSerialChecksResponse;
import io.bootify.my_app.repos.DetailParamsFCTATERepository;
import io.bootify.my_app.repos.ScanSerialCheckRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class DetailParamsFCTATEService {

    private final DetailParamsFCTATERepository repository;
    private final ScanSerialCheckRepository scanSerialCheckRepository;

    public DetailParamsFCTATEService(DetailParamsFCTATERepository repository, ScanSerialCheckRepository scanSerialCheckRepository) {
        this.repository = repository;
        this.scanSerialCheckRepository = scanSerialCheckRepository;
    }

    public List<DetailParamsFCTATE> getAll() {
        return repository.findAll();
    }

//    public List<DetailParamsFCTATE> search(String keyword) {
//
//        // 1. Tìm theo serialItem
//        List<ScanSerialChecksResponse> byItem = scanSerialCheckRepository.getBySerialItem(keyword);
//
//        // 2. Tìm theo serialBoard
//        List<ScanSerialChecksResponse> byBoard = scanSerialCheckRepository.getBySerialBoard(keyword);
//
//        // 3. Gộp lại và lấy serialID
//        List<Long> serialIDs = Stream.concat(byItem.stream(), byBoard.stream())
//                .map(ScanSerialChecksResponse::getSerialId)   // Lấy serialID
//                .filter(Objects::nonNull)
//                .distinct()
//                .toList();
//
//        // 4. Lấy dữ liệu DetailParamsFCTATE theo serialID
//        return serialIDs.stream()
//                .flatMap(id -> repository.findBySerialID(id).stream())
//                .toList();
//    }


    /**
     * Tìm kiếm DetailParamsFCTATE theo serialBoard HOẶC serialItem.
     * @param serial Mã serial (serialBoard hoặc serialItem)
     * @return Danh sách DetailParamsFCTATE tương ứng
     */
    public List<DetailParamsFCTATE> getDetailParamsBySerial(String serial) {
        if (serial == null || serial.trim().isEmpty()) {
            return Collections.emptyList();
        }

        // 1. Tìm ScanSerialCheck theo serialBoard và serialItem
        List<ScanSerialCheck> checksByBoard = scanSerialCheckRepository.findBySerialBoard(serial);
        List<ScanSerialCheck> checksByItem = scanSerialCheckRepository.findAllBySerialItem(serial);

        // 2. Kết hợp và lọc ra serialID duy nhất (nếu có trường hợp 1 serial vừa là board vừa là item)
        List<Long> distinctSerialIds = Stream.concat(checksByBoard.stream(), checksByItem.stream())
                .map(ScanSerialCheck::getSerialId)
                .distinct()
                .collect(Collectors.toList());

        if (distinctSerialIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 3. Truy vấn DetailParamsFCTATE bằng danh sách Serial IDs
        return repository.findAllByScanSerialCheck_SerialIdIn(distinctSerialIds);
    }
}
