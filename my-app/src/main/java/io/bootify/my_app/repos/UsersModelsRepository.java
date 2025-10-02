package io.bootify.my_app.repos;

import io.bootify.my_app.domain.UsersModels;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersModelsRepository extends JpaRepository<UsersModels, Integer> {
}
