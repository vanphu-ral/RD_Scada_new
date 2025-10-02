package io.bootify.my_app.service;

import io.bootify.my_app.domain.ParameterPaints;
import io.bootify.my_app.domain.ProductsModels;
import io.bootify.my_app.events.BeforeDeleteProductsModels;
import io.bootify.my_app.model.ParameterPaintsDTO;
import io.bootify.my_app.repos.ParameterPaintsRepository;
import io.bootify.my_app.repos.ProductsModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ParameterPaintsService {

    private final ParameterPaintsRepository parameterPaintsRepository;
    private final ProductsModelsRepository productsModelsRepository;

    public ParameterPaintsService(final ParameterPaintsRepository parameterPaintsRepository,
            final ProductsModelsRepository productsModelsRepository) {
        this.parameterPaintsRepository = parameterPaintsRepository;
        this.productsModelsRepository = productsModelsRepository;
    }

    public List<ParameterPaintsDTO> findAll() {
        final List<ParameterPaints> parameterPaintses = parameterPaintsRepository.findAll(Sort.by("idparamsPaint"));
        return parameterPaintses.stream()
                .map(parameterPaints -> mapToDTO(parameterPaints, new ParameterPaintsDTO()))
                .toList();
    }

    public ParameterPaintsDTO get(final Integer idparamsPaint) {
        return parameterPaintsRepository.findById(idparamsPaint)
                .map(parameterPaints -> mapToDTO(parameterPaints, new ParameterPaintsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ParameterPaintsDTO parameterPaintsDTO) {
        final ParameterPaints parameterPaints = new ParameterPaints();
        mapToEntity(parameterPaintsDTO, parameterPaints);
        return parameterPaintsRepository.save(parameterPaints).getIdparamsPaint();
    }

    public void update(final Integer idparamsPaint, final ParameterPaintsDTO parameterPaintsDTO) {
        final ParameterPaints parameterPaints = parameterPaintsRepository.findById(idparamsPaint)
                .orElseThrow(NotFoundException::new);
        mapToEntity(parameterPaintsDTO, parameterPaints);
        parameterPaintsRepository.save(parameterPaints);
    }

    public void delete(final Integer idparamsPaint) {
        final ParameterPaints parameterPaints = parameterPaintsRepository.findById(idparamsPaint)
                .orElseThrow(NotFoundException::new);
        parameterPaintsRepository.delete(parameterPaints);
    }

    private ParameterPaintsDTO mapToDTO(final ParameterPaints parameterPaints,
            final ParameterPaintsDTO parameterPaintsDTO) {
        parameterPaintsDTO.setIdparamsPaint(parameterPaints.getIdparamsPaint());
        parameterPaintsDTO.setSetConveryorSpeed1(parameterPaints.getSetConveryorSpeed1());
        parameterPaintsDTO.setSetConveryorSpeed2(parameterPaints.getSetConveryorSpeed2());
        parameterPaintsDTO.setSetSpeedGun01(parameterPaints.getSetSpeedGun01());
        parameterPaintsDTO.setSetSpeedGun02(parameterPaints.getSetSpeedGun02());
        parameterPaintsDTO.setSetSpeedGun03(parameterPaints.getSetSpeedGun03());
        parameterPaintsDTO.setSetSpeedGun04(parameterPaints.getSetSpeedGun04());
        parameterPaintsDTO.setSetSpeedGun05(parameterPaints.getSetSpeedGun05());
        parameterPaintsDTO.setSetPhArea01(parameterPaints.getSetPhArea01());
        parameterPaintsDTO.setSetPhArea02(parameterPaints.getSetPhArea02());
        parameterPaintsDTO.setSetPhArea03(parameterPaints.getSetPhArea03());
        parameterPaintsDTO.setSetPhArea04(parameterPaints.getSetPhArea04());
        parameterPaintsDTO.setSetPhArea05(parameterPaints.getSetPhArea05());
        parameterPaintsDTO.setSetEsdArea01(parameterPaints.getSetEsdArea01());
        parameterPaintsDTO.setSetEsdArea02(parameterPaints.getSetEsdArea02());
        parameterPaintsDTO.setSetEsdArea03(parameterPaints.getSetEsdArea03());
        parameterPaintsDTO.setSetEsdArea04(parameterPaints.getSetEsdArea04());
        parameterPaintsDTO.setSetEsdArea05(parameterPaints.getSetEsdArea05());
        parameterPaintsDTO.setSetTempArea01(parameterPaints.getSetTempArea01());
        parameterPaintsDTO.setSetTempArea02(parameterPaints.getSetTempArea02());
        parameterPaintsDTO.setSetTempArea03(parameterPaints.getSetTempArea03());
        parameterPaintsDTO.setSetTempArea04(parameterPaints.getSetTempArea04());
        parameterPaintsDTO.setSetTempArea05(parameterPaints.getSetTempArea05());
        parameterPaintsDTO.setSetTempDrying01(parameterPaints.getSetTempDrying01());
        parameterPaintsDTO.setSetTempDrying02(parameterPaints.getSetTempDrying02());
        parameterPaintsDTO.setSetTempDrying03(parameterPaints.getSetTempDrying03());
        parameterPaintsDTO.setSetTempDrying04(parameterPaints.getSetTempDrying04());
        parameterPaintsDTO.setSetTempDrying05(parameterPaints.getSetTempDrying05());
        parameterPaintsDTO.setProduct(parameterPaints.getProduct() == null ? null : parameterPaints.getProduct().getProductId());
        return parameterPaintsDTO;
    }

    private ParameterPaints mapToEntity(final ParameterPaintsDTO parameterPaintsDTO,
            final ParameterPaints parameterPaints) {
        parameterPaints.setSetConveryorSpeed1(parameterPaintsDTO.getSetConveryorSpeed1());
        parameterPaints.setSetConveryorSpeed2(parameterPaintsDTO.getSetConveryorSpeed2());
        parameterPaints.setSetSpeedGun01(parameterPaintsDTO.getSetSpeedGun01());
        parameterPaints.setSetSpeedGun02(parameterPaintsDTO.getSetSpeedGun02());
        parameterPaints.setSetSpeedGun03(parameterPaintsDTO.getSetSpeedGun03());
        parameterPaints.setSetSpeedGun04(parameterPaintsDTO.getSetSpeedGun04());
        parameterPaints.setSetSpeedGun05(parameterPaintsDTO.getSetSpeedGun05());
        parameterPaints.setSetPhArea01(parameterPaintsDTO.getSetPhArea01());
        parameterPaints.setSetPhArea02(parameterPaintsDTO.getSetPhArea02());
        parameterPaints.setSetPhArea03(parameterPaintsDTO.getSetPhArea03());
        parameterPaints.setSetPhArea04(parameterPaintsDTO.getSetPhArea04());
        parameterPaints.setSetPhArea05(parameterPaintsDTO.getSetPhArea05());
        parameterPaints.setSetEsdArea01(parameterPaintsDTO.getSetEsdArea01());
        parameterPaints.setSetEsdArea02(parameterPaintsDTO.getSetEsdArea02());
        parameterPaints.setSetEsdArea03(parameterPaintsDTO.getSetEsdArea03());
        parameterPaints.setSetEsdArea04(parameterPaintsDTO.getSetEsdArea04());
        parameterPaints.setSetEsdArea05(parameterPaintsDTO.getSetEsdArea05());
        parameterPaints.setSetTempArea01(parameterPaintsDTO.getSetTempArea01());
        parameterPaints.setSetTempArea02(parameterPaintsDTO.getSetTempArea02());
        parameterPaints.setSetTempArea03(parameterPaintsDTO.getSetTempArea03());
        parameterPaints.setSetTempArea04(parameterPaintsDTO.getSetTempArea04());
        parameterPaints.setSetTempArea05(parameterPaintsDTO.getSetTempArea05());
        parameterPaints.setSetTempDrying01(parameterPaintsDTO.getSetTempDrying01());
        parameterPaints.setSetTempDrying02(parameterPaintsDTO.getSetTempDrying02());
        parameterPaints.setSetTempDrying03(parameterPaintsDTO.getSetTempDrying03());
        parameterPaints.setSetTempDrying04(parameterPaintsDTO.getSetTempDrying04());
        parameterPaints.setSetTempDrying05(parameterPaintsDTO.getSetTempDrying05());
        final ProductsModels product = parameterPaintsDTO.getProduct() == null ? null : productsModelsRepository.findById(parameterPaintsDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        parameterPaints.setProduct(product);
        return parameterPaints;
    }

    @EventListener(BeforeDeleteProductsModels.class)
    public void on(final BeforeDeleteProductsModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ParameterPaints productParameterPaints = parameterPaintsRepository.findFirstByProductProductId(event.getProductId());
        if (productParameterPaints != null) {
            referencedException.setKey("productsModels.parameterPaints.product.referenced");
            referencedException.addParam(productParameterPaints.getIdparamsPaint());
            throw referencedException;
        }
    }

}
