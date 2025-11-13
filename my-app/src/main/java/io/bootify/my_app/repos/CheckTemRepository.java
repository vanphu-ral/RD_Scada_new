package io.bootify.my_app.repos;

import io.bootify.my_app.domain.CheckTem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckTemRepository extends JpaRepository<CheckTem, Long> {

    CheckTem findFirstByProfileProfileId(Integer profileId);

}
