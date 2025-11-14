package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ScanCheckSerialLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ScanCheckSerialLogsRepository extends JpaRepository<ScanCheckSerialLogs,Long> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM ScanSerialCheckLogs where user_name = ?1 and time_check between ?2 and ?3 ;",nativeQuery = true)
    void deleteByTimeAndUserName(String userName,String date,String endDate);
}
