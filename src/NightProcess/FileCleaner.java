package NightProcess;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class FileCleaner  {
    private final String directoryPath;
    private final int daysThreshold;

    public FileCleaner(String directoryPath, int daysThreshold) {
        this.directoryPath = directoryPath;
        this.daysThreshold = daysThreshold;
    }

    //הפונקציה שמנקה
    public void clean() {
       try {
            Files.list(Paths.get(directoryPath)).forEach(filePath -> { // המרת ה-String ל-Path
                try {
                    BasicFileAttributes attrs = Files.readAttributes(filePath, BasicFileAttributes.class);
                    Instant creationTime = attrs.creationTime().toInstant();
                    LocalDateTime creationDateTime = LocalDateTime.ofInstant(creationTime, ZoneId.systemDefault());

                    if (creationDateTime.isBefore(LocalDateTime.now().minusDays(daysThreshold))) {
                        Files.delete(filePath); // שימוש ב-Files.delete
                        System.out.println("Deleted: " + filePath.getFileName());
                    }
                } catch (IOException e) {
                    System.err.println("Error processing file: " + filePath.getFileName());
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            System.err.println("Error listing directory: " + directoryPath);
            e.printStackTrace();
        }
    }
    //הפעלת התהליך
    public static void nightProcess(){
        FileCleaner fileCleaner = new FileCleaner("C:\\Users\\This User\\Documents\\תכנות\\try", 0); // אפשר לשנות את הנתיב ואת מספר הימים

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        // קביעת השעה הרצויה (2:00 בלילה)
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime nextRun = now.withHour(2).withMinute(0).withSecond(0).withNano(0);

        // אם השעה הנוכחית אחרי 2:00, קבע את הפעם הבאה ליום הבא
        if (now.isAfter(nextRun)) {
            nextRun = nextRun.plusDays(1);
        }

        long initialDelay = ChronoUnit.SECONDS.between(now, nextRun);
        long period = 24 * 60 * 60; // 24 שעות בשניות

        scheduler.scheduleAtFixedRate(fileCleaner::clean, initialDelay, period, TimeUnit.SECONDS); // להריץ כל


    }


}
