package NightProcess;

import java.io.File;

public class RenameFiles extends Thread{
    private String folderPath;
    private String textToAdd;

    public RenameFiles(String folderPath, String textToAdd) {
        this.folderPath = folderPath;
        this.textToAdd = textToAdd;
    }

    public void run() {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    RenameFile processor = new RenameFile(file);
                    processor.renameFile(textToAdd);
                }
            }
        }
    }
}
