package io.bootify.my_app.repos;

import io.bootify.my_app.domain.DetailError;
import io.bootify.my_app.model.ChatMessage;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetailErrorRepository extends JpaRepository<DetailError, Long> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO woErrorHistory (sender, content, workOrder, type,status) VALUES (:sender, :content, :workOrder, :type,0)", nativeQuery = true)
    Integer insertWoErrorHistory(@Param("sender") String sender,
                              @Param("content") String content,
                              @Param("workOrder") String workOrder,
                              @Param("type") String type);

    @Modifying
    @Transactional
    @Query(value = "UPDATE woErrorHistory SET sender = :sender, content = :content, type = :type , status =:status WHERE workOrder = :workOrder", nativeQuery = true)
    void updateWoErrorHistory(@Param("sender") String sender,
                              @Param("content") String content,
                              @Param("workOrder") String workOrder,
                              @Param("type") String type,
                              @Param("status") Integer status);
    @Query(value = "SELECT * FROM woErrorHistory WHERE workOrder = :workOrder", nativeQuery = true)
    List<ChatMessage> findByWorkOrder(@Param("workOrder") String workOrder);
}
