package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ErrorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ErrorModelRepository extends JpaRepository<ErrorModel, Integer> {

    ErrorModel findFirstByHmierrHmierrId(Integer hmierrId);

    ErrorModel findFirstByProductTypeGroupProductTypeGroupId(Integer productTypeGroupId);

}
