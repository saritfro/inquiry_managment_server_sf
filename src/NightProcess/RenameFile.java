package NightProcess;

import java.io.File;

public class RenameFile {
    private File file;

    public RenameFile(File file) {
        this.file = file;
    }
    public void renameFile(String textToAdd) {
        String newFileName = textToAdd + file.getName();
        File newFile = new File(file.getParent(), newFileName);
        boolean renamed = file.renameTo(newFile);

        if (renamed) {
            System.out.println("File renamed: " + file.getName() + " -> " + newFileName);
        } else {
            System.out.println("Failed to rename file: " + file.getName());
        }
    }
}
