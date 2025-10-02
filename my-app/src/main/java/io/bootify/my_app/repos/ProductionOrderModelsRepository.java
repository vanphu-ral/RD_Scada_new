package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ProductionOrderModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductionOrderModelsRepository extends JpaRepository<ProductionOrderModels, Integer> {

    ProductionOrderModels findFirstByMachineGroupMachineGroupId(Integer machineGroupId);

    ProductionOrderModels findFirstByProductProductId(Integer productId);

}
