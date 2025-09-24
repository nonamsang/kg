/*package aoppackage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RecentViewLogger {

    // 절대 경로로 수정 (본인의 실제 프로젝트 경로에 맞게 조정)
    private static final String LOG_FILE_PATH = "D:/sts3/MoodShop2/logs/recent-view-paths.txt";

    @Before("execution(* com.moodshop.kokone.service.RecentViewService.addRecentView(..))")
    public void logRecentView(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();

        String userId = "";
        String productId = "";

        if (args.length >= 2) {
            userId = String.valueOf(args[0]);
            productId = String.valueOf(args[1]);
            if(productId==null ||productId.trim().isEmpty()) {
            	return;
            }
        }

        String log = String.format("%s - userId: %s, productId: %s%n", new Date(), userId, productId);

        try {
            File logFile = new File(LOG_FILE_PATH);

            // 상위 폴더가 없으면 생성
            File parentDir = logFile.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            // 파일이 없으면 생성
            if (!logFile.exists()) {
                logFile.createNewFile();
            }

            // 로그 기록
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true))) {
                bw.write(log);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
*/