package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ProfileTem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileTemRepository extends JpaRepository<ProfileTem, Integer> {

    ProfileTem findFirstByProductProductId(Integer productId);

}
