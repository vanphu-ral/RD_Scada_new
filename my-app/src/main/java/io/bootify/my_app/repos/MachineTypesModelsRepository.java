package io.bootify.my_app.repos;

import io.bootify.my_app.domain.MachineTypesModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MachineTypesModelsRepository extends JpaRepository<MachineTypesModels, Integer> {
}
