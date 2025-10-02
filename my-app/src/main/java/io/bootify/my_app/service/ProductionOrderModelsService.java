package io.bootify.my_app.service;

import io.bootify.my_app.domain.MachineTypesModels;
import io.bootify.my_app.domain.ProductionOrderModels;
import io.bootify.my_app.domain.ProductsModels;
import io.bootify.my_app.events.BeforeDeleteMachineTypesModels;
import io.bootify.my_app.events.BeforeDeleteProductionOrderModels;
import io.bootify.my_app.events.BeforeDeleteProductsModels;
import io.bootify.my_app.model.ProductionOrderModelsDTO;
import io.bootify.my_app.repos.MachineTypesModelsRepository;
import io.bootify.my_app.repos.ProductionOrderModelsRepository;
import io.bootify.my_app.repos.ProductsModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ProductionOrderModelsService {

    private final ProductionOrderModelsRepository productionOrderModelsRepository;
    private final MachineTypesModelsRepository machineTypesModelsRepository;
    private final ProductsModelsRepository productsModelsRepository;
    private final ApplicationEventPublisher publisher;

    public ProductionOrderModelsService(
            final ProductionOrderModelsRepository productionOrderModelsRepository,
            final MachineTypesModelsRepository machineTypesModelsRepository,
            final ProductsModelsRepository productsModelsRepository,
            final ApplicationEventPublisher publisher) {
        this.productionOrderModelsRepository = productionOrderModelsRepository;
        this.machineTypesModelsRepository = machineTypesModelsRepository;
        this.productsModelsRepository = productsModelsRepository;
        this.publisher = publisher;
    }

    public List<ProductionOrderModelsDTO> findAll() {
        final List<ProductionOrderModels> productionOrderModelses = productionOrderModelsRepository.findAll(Sort.by("productionOrderId"));
        return productionOrderModelses.stream()
                .map(productionOrderModels -> mapToDTO(productionOrderModels, new ProductionOrderModelsDTO()))
                .toList();
    }

    public ProductionOrderModelsDTO get(final Integer productionOrderId) {
        return productionOrderModelsRepository.findById(productionOrderId)
                .map(productionOrderModels -> mapToDTO(productionOrderModels, new ProductionOrderModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ProductionOrderModelsDTO productionOrderModelsDTO) {
        final ProductionOrderModels productionOrderModels = new ProductionOrderModels();
        mapToEntity(productionOrderModelsDTO, productionOrderModels);
        return productionOrderModelsRepository.save(productionOrderModels).getProductionOrderId();
    }

    public void update(final Integer productionOrderId,
            final ProductionOrderModelsDTO productionOrderModelsDTO) {
        final ProductionOrderModels productionOrderModels = productionOrderModelsRepository.findById(productionOrderId)
                .orElseThrow(NotFoundException::new);
        mapToEntity(productionOrderModelsDTO, productionOrderModels);
        productionOrderModelsRepository.save(productionOrderModels);
    }

    public void delete(final Integer productionOrderId) {
        final ProductionOrderModels productionOrderModels = productionOrderModelsRepository.findById(productionOrderId)
                .orElseThrow(NotFoundException::new);
        publisher.publishEvent(new BeforeDeleteProductionOrderModels(productionOrderId));
        productionOrderModelsRepository.delete(productionOrderModels);
    }

    private ProductionOrderModelsDTO mapToDTO(final ProductionOrderModels productionOrderModels,
            final ProductionOrderModelsDTO productionOrderModelsDTO) {
        productionOrderModelsDTO.setProductionOrderId(productionOrderModels.getProductionOrderId());
        productionOrderModelsDTO.setWorkOrder(productionOrderModels.getWorkOrder());
        productionOrderModelsDTO.setWorking(productionOrderModels.getWorking());
        productionOrderModelsDTO.setNumberOfPlan(productionOrderModels.getNumberOfPlan());
        productionOrderModelsDTO.setLot(productionOrderModels.getLot());
        productionOrderModelsDTO.setAlert1(productionOrderModels.getAlert1());
        productionOrderModelsDTO.setAlert2(productionOrderModels.getAlert2());
        productionOrderModelsDTO.setAlert3(productionOrderModels.getAlert3());
        productionOrderModelsDTO.setAlert4(productionOrderModels.getAlert4());
        productionOrderModelsDTO.setAlert5(productionOrderModels.getAlert5());
        productionOrderModelsDTO.setAlert6(productionOrderModels.getAlert6());
        productionOrderModelsDTO.setAlert7(productionOrderModels.getAlert7());
        productionOrderModelsDTO.setMachineGroup(productionOrderModels.getMachineGroup() == null ? null : productionOrderModels.getMachineGroup().getMachineGroupId());
        productionOrderModelsDTO.setProduct(productionOrderModels.getProduct() == null ? null : productionOrderModels.getProduct().getProductId());
        return productionOrderModelsDTO;
    }

    private ProductionOrderModels mapToEntity(
            final ProductionOrderModelsDTO productionOrderModelsDTO,
            final ProductionOrderModels productionOrderModels) {
        productionOrderModels.setWorkOrder(productionOrderModelsDTO.getWorkOrder());
        productionOrderModels.setWorking(productionOrderModelsDTO.getWorking());
        productionOrderModels.setNumberOfPlan(productionOrderModelsDTO.getNumberOfPlan());
        productionOrderModels.setLot(productionOrderModelsDTO.getLot());
        productionOrderModels.setAlert1(productionOrderModelsDTO.getAlert1());
        productionOrderModels.setAlert2(productionOrderModelsDTO.getAlert2());
        productionOrderModels.setAlert3(productionOrderModelsDTO.getAlert3());
        productionOrderModels.setAlert4(productionOrderModelsDTO.getAlert4());
        productionOrderModels.setAlert5(productionOrderModelsDTO.getAlert5());
        productionOrderModels.setAlert6(productionOrderModelsDTO.getAlert6());
        productionOrderModels.setAlert7(productionOrderModelsDTO.getAlert7());
        final MachineTypesModels machineGroup = productionOrderModelsDTO.getMachineGroup() == null ? null : machineTypesModelsRepository.findById(productionOrderModelsDTO.getMachineGroup())
                .orElseThrow(() -> new NotFoundException("machineGroup not found"));
        productionOrderModels.setMachineGroup(machineGroup);
        final ProductsModels product = productionOrderModelsDTO.getProduct() == null ? null : productsModelsRepository.findById(productionOrderModelsDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        productionOrderModels.setProduct(product);
        return productionOrderModels;
    }

    @EventListener(BeforeDeleteMachineTypesModels.class)
    public void on(final BeforeDeleteMachineTypesModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ProductionOrderModels machineGroupProductionOrderModels = productionOrderModelsRepository.findFirstByMachineGroupMachineGroupId(event.getMachineGroupId());
        if (machineGroupProductionOrderModels != null) {
            referencedException.setKey("machineTypesModels.productionOrderModels.machineGroup.referenced");
            referencedException.addParam(machineGroupProductionOrderModels.getProductionOrderId());
            throw referencedException;
        }
    }

    @EventListener(BeforeDeleteProductsModels.class)
    public void on(final BeforeDeleteProductsModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ProductionOrderModels productProductionOrderModels = productionOrderModelsRepository.findFirstByProductProductId(event.getProductId());
        if (productProductionOrderModels != null) {
            referencedException.setKey("productsModels.productionOrderModels.product.referenced");
            referencedException.addParam(productProductionOrderModels.getProductionOrderId());
            throw referencedException;
        }
    }

}
