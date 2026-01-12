package io.bootify.my_app.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    // Khai báo chính xác tên package để Logback nhận diện được
    private final Logger logger = LoggerFactory.getLogger("io.bootify.my_app.service");
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before("execution(* io.bootify.my_app.service.PlanningWOService.checkSerialItemExistBymode(..))")
    public void logRequest(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (args != null && args.length > 0) {
            try {
                // Chuyển đối tượng request đầu tiên sang JSON
                String jsonBody = objectMapper.writeValueAsString(args[0]);

                // Ghi vào log
                logger.info(jsonBody);

                // Dòng này ĐỂ KIỂM TRA TRÊN CONSOLE: Nếu thấy dòng này hiện ra thì Aspect đã chạy
                System.out.println("===> ASPECT LOGGED: " + jsonBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}