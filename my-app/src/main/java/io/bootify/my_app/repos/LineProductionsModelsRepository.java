package io.bootify.my_app.repos;

import io.bootify.my_app.domain.LineProductionsModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LineProductionsModelsRepository extends JpaRepository<LineProductionsModels, Integer> {
}
