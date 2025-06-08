package NightProcess;

import NightProcess.RenameFiles;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RenameFilesScheduler {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void scheduleRenamingTask(String folderPath, String textToAdd, int hour, int minute) {
        long initialDelay = calculateInitialDelay(hour, minute);
        long period = 24 * 60 * 60; // 24 hours

        // Schedule the renaming task to run at the specified time
        scheduler.scheduleAtFixedRate(new RenameFiles(folderPath, textToAdd), initialDelay, period, TimeUnit.SECONDS);
    }

    private long calculateInitialDelay(int hour, int minute) {
        long currentTime = System.currentTimeMillis();
        java.util.GregorianCalendar calendar = new java.util.GregorianCalendar();

        // Set the target time with the desired hour and minute
        calendar.set(java.util.Calendar.HOUR_OF_DAY, hour); // Set the hour
        calendar.set(java.util.Calendar.MINUTE, minute);     // Set the minute
        calendar.set(java.util.Calendar.SECOND, 0);          // Set seconds to 0
        calendar.set(java.util.Calendar.MILLISECOND, 0);     // Set milliseconds to 0

        long targetTime = calendar.getTimeInMillis();
        return (targetTime - currentTime) / 1000;
    }

    public void shutdown() {
        scheduler.shutdown();
    }
}
