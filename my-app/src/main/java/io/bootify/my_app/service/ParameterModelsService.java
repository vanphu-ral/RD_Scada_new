package io.bootify.my_app.service;

import io.bootify.my_app.domain.ParameterModels;
import io.bootify.my_app.domain.ProductsModels;
import io.bootify.my_app.events.BeforeDeleteProductsModels;
import io.bootify.my_app.model.ParameterModelsDTO;
import io.bootify.my_app.repos.ParameterModelsRepository;
import io.bootify.my_app.repos.ProductsModelsRepository;
import io.bootify.my_app.util.NotFoundException;
import io.bootify.my_app.util.ReferencedException;
import java.util.List;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class ParameterModelsService {

    private final ParameterModelsRepository parameterModelsRepository;
    private final ProductsModelsRepository productsModelsRepository;

    public ParameterModelsService(final ParameterModelsRepository parameterModelsRepository,
            final ProductsModelsRepository productsModelsRepository) {
        this.parameterModelsRepository = parameterModelsRepository;
        this.productsModelsRepository = productsModelsRepository;
    }

    public List<ParameterModelsDTO> findAll() {
        final List<ParameterModels> parameterModelses = parameterModelsRepository.findAll(Sort.by("id"));
        return parameterModelses.stream()
                .map(parameterModels -> mapToDTO(parameterModels, new ParameterModelsDTO()))
                .toList();
    }

    public ParameterModelsDTO get(final Integer id) {
        return parameterModelsRepository.findById(id)
                .map(parameterModels -> mapToDTO(parameterModels, new ParameterModelsDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Integer create(final ParameterModelsDTO parameterModelsDTO) {
        final ParameterModels parameterModels = new ParameterModels();
        mapToEntity(parameterModelsDTO, parameterModels);
        return parameterModelsRepository.save(parameterModels).getId();
    }

    public void update(final Integer id, final ParameterModelsDTO parameterModelsDTO) {
        final ParameterModels parameterModels = parameterModelsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(parameterModelsDTO, parameterModels);
        parameterModelsRepository.save(parameterModels);
    }

    public void delete(final Integer id) {
        final ParameterModels parameterModels = parameterModelsRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        parameterModelsRepository.delete(parameterModels);
    }

    private ParameterModelsDTO mapToDTO(final ParameterModels parameterModels,
            final ParameterModelsDTO parameterModelsDTO) {
        parameterModelsDTO.setId(parameterModels.getId());
        parameterModelsDTO.setCh(parameterModels.getCh());
        parameterModelsDTO.setUmin(parameterModels.getUmin());
        parameterModelsDTO.setUmax(parameterModels.getUmax());
        parameterModelsDTO.setImin(parameterModels.getImin());
        parameterModelsDTO.setImax(parameterModels.getImax());
        parameterModelsDTO.setPmin(parameterModels.getPmin());
        parameterModelsDTO.setPmax(parameterModels.getPmax());
        parameterModelsDTO.setCosmin(parameterModels.getCosmin());
        parameterModelsDTO.setCosmax(parameterModels.getCosmax());
        parameterModelsDTO.setOffsetI(parameterModels.getOffsetI());
        parameterModelsDTO.setTimeCaculator(parameterModels.getTimeCaculator());
        parameterModelsDTO.setTimeAlert(parameterModels.getTimeAlert());
        parameterModelsDTO.setUl1min(parameterModels.getUl1min());
        parameterModelsDTO.setUl2min(parameterModels.getUl2min());
        parameterModelsDTO.setUl3min(parameterModels.getUl3min());
        parameterModelsDTO.setUl4min(parameterModels.getUl4min());
        parameterModelsDTO.setUl5min(parameterModels.getUl5min());
        parameterModelsDTO.setUl6min(parameterModels.getUl6min());
        parameterModelsDTO.setUl7min(parameterModels.getUl7min());
        parameterModelsDTO.setUl8min(parameterModels.getUl8min());
        parameterModelsDTO.setUl1max(parameterModels.getUl1max());
        parameterModelsDTO.setUl2max(parameterModels.getUl2max());
        parameterModelsDTO.setUl3max(parameterModels.getUl3max());
        parameterModelsDTO.setUl4max(parameterModels.getUl4max());
        parameterModelsDTO.setUl5max(parameterModels.getUl5max());
        parameterModelsDTO.setUl6max(parameterModels.getUl6max());
        parameterModelsDTO.setUl7max(parameterModels.getUl7max());
        parameterModelsDTO.setUl8max(parameterModels.getUl8max());
        parameterModelsDTO.setSv1(parameterModels.getSv1());
        parameterModelsDTO.setSv2(parameterModels.getSv2());
        parameterModelsDTO.setSv3(parameterModels.getSv3());
        parameterModelsDTO.setSv4(parameterModels.getSv4());
        parameterModelsDTO.setSv5(parameterModels.getSv5());
        parameterModelsDTO.setSv6(parameterModels.getSv6());
        parameterModelsDTO.setSv7(parameterModels.getSv7());
        parameterModelsDTO.setSv8(parameterModels.getSv8());
        parameterModelsDTO.setProductMode(parameterModels.getProductMode());
        parameterModelsDTO.setProduct(parameterModels.getProduct() == null ? null : parameterModels.getProduct().getProductId());
        return parameterModelsDTO;
    }

    private ParameterModels mapToEntity(final ParameterModelsDTO parameterModelsDTO,
            final ParameterModels parameterModels) {
        parameterModels.setCh(parameterModelsDTO.getCh());
        parameterModels.setUmin(parameterModelsDTO.getUmin());
        parameterModels.setUmax(parameterModelsDTO.getUmax());
        parameterModels.setImin(parameterModelsDTO.getImin());
        parameterModels.setImax(parameterModelsDTO.getImax());
        parameterModels.setPmin(parameterModelsDTO.getPmin());
        parameterModels.setPmax(parameterModelsDTO.getPmax());
        parameterModels.setCosmin(parameterModelsDTO.getCosmin());
        parameterModels.setCosmax(parameterModelsDTO.getCosmax());
        parameterModels.setOffsetI(parameterModelsDTO.getOffsetI());
        parameterModels.setTimeCaculator(parameterModelsDTO.getTimeCaculator());
        parameterModels.setTimeAlert(parameterModelsDTO.getTimeAlert());
        parameterModels.setUl1min(parameterModelsDTO.getUl1min());
        parameterModels.setUl2min(parameterModelsDTO.getUl2min());
        parameterModels.setUl3min(parameterModelsDTO.getUl3min());
        parameterModels.setUl4min(parameterModelsDTO.getUl4min());
        parameterModels.setUl5min(parameterModelsDTO.getUl5min());
        parameterModels.setUl6min(parameterModelsDTO.getUl6min());
        parameterModels.setUl7min(parameterModelsDTO.getUl7min());
        parameterModels.setUl8min(parameterModelsDTO.getUl8min());
        parameterModels.setUl1max(parameterModelsDTO.getUl1max());
        parameterModels.setUl2max(parameterModelsDTO.getUl2max());
        parameterModels.setUl3max(parameterModelsDTO.getUl3max());
        parameterModels.setUl4max(parameterModelsDTO.getUl4max());
        parameterModels.setUl5max(parameterModelsDTO.getUl5max());
        parameterModels.setUl6max(parameterModelsDTO.getUl6max());
        parameterModels.setUl7max(parameterModelsDTO.getUl7max());
        parameterModels.setUl8max(parameterModelsDTO.getUl8max());
        parameterModels.setSv1(parameterModelsDTO.getSv1());
        parameterModels.setSv2(parameterModelsDTO.getSv2());
        parameterModels.setSv3(parameterModelsDTO.getSv3());
        parameterModels.setSv4(parameterModelsDTO.getSv4());
        parameterModels.setSv5(parameterModelsDTO.getSv5());
        parameterModels.setSv6(parameterModelsDTO.getSv6());
        parameterModels.setSv7(parameterModelsDTO.getSv7());
        parameterModels.setSv8(parameterModelsDTO.getSv8());
        parameterModels.setProductMode(parameterModelsDTO.getProductMode());
        final ProductsModels product = parameterModelsDTO.getProduct() == null ? null : productsModelsRepository.findById(parameterModelsDTO.getProduct())
                .orElseThrow(() -> new NotFoundException("product not found"));
        parameterModels.setProduct(product);
        return parameterModels;
    }

    @EventListener(BeforeDeleteProductsModels.class)
    public void on(final BeforeDeleteProductsModels event) {
        final ReferencedException referencedException = new ReferencedException();
        final ParameterModels productParameterModels = parameterModelsRepository.findFirstByProductProductId(event.getProductId());
        if (productParameterModels != null) {
            referencedException.setKey("productsModels.parameterModels.product.referenced");
            referencedException.addParam(productParameterModels.getId());
            throw referencedException;
        }
    }

}
