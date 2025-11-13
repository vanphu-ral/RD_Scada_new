package io.bootify.my_app.repos;

import io.bootify.my_app.domain.ScanCheckSerialLogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ScanCheckSerialLogsRepository extends JpaRepository<ScanCheckSerialLogs,Long> {
    @Modifying
    @Query(value = "DELETE FROM ScanSerialCheckLogs where user_name = ?1 and wo like ?2 ;",nativeQuery = true)
    void deleteByTimeAndUserName(String userName,String date);
}
