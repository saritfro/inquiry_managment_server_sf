package HandleStoreFiles;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HandleFiles {
    public void saveFile(IForSaving IforSaving) throws IOException {
        Path filePath = getDirectoryPath(IforSaving);
        // בדיקה אם התיקייה קיימת, ואם לא - יוצרים אותה
        Files.createDirectories(filePath.getParent());

        // פתיחת הקובץ לכתיבה
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile()))) {
            writer.write(IforSaving.getData());
            System.out.println("✅ הקובץ נשמר בהצלחה: " + filePath);
        } catch (IOException e) {
            System.out.println("❌ שגיאה בשמירת הקובץ: " + e.getMessage());
        }
    }

    public void deleteFile(IForSaving IforSaving) throws IOException {
              // בונים את הנתיב של הקובץ
                Path filePath = getDirectoryPath(IforSaving);

                try {
                    boolean deleted = Files.deleteIfExists(filePath); // מוחק אם קיים
                    if (deleted) {
                        System.out.println("✅ הקובץ נמחק בהצלחה: " + filePath);
                    } else {
                        System.out.println("⚠ הקובץ לא נמצא: " + filePath);
                    }
                } catch (Exception e) {
                    System.out.println("❌ שגיאה במחיקת הקובץ: " + e.getMessage());
                }
    }

    public void updateFile(IForSaving IforSaving) throws IOException {
        Path filePath = getDirectoryPath(IforSaving);
        Files.createDirectories(filePath.getParent());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath.toFile(), true))) {
            writer.newLine(); // מוסיף מעבר שורה
            writer.write(IforSaving.getData());
            System.out.println("✅ התוכן נוסף בהצלחה לקובץ: " + filePath);
        } catch (IOException e) {
            System.out.println("❌ שגיאה בהוספת תוכן לקובץ: " + e.getMessage());
        }
    }

    private String getFileName(IForSaving IforSaving){
            return IforSaving.getFileName();
    }

    private Path getDirectoryPath(IForSaving IforSaving){
        return Paths.get(IforSaving.getFolderName()+File.separator+IforSaving.getFileName());
    }

    public void saveFiles(List <IForSaving>list) throws IOException {
       for (IForSaving IforSaving:list){
           saveFile(IforSaving);
       }
    }
    public IForSaving readObj(String fileName) throws Exception{
       File f=new File(fileName);
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line).append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileContent = content.toString();
        String[] itemsArray = fileContent.split(",");

        // ממירים את המערך ל-ArrayList
        ArrayList<String> itemsList = new ArrayList<>(Arrays.asList(itemsArray));
        Class<?> clazz;
        if(!itemsList.get(0).equals("Agents.Agent"))
        clazz= Class.forName("Data."+itemsList.get(0)); // מקבלים את המחלקה
        else
            clazz=Class.forName(itemsList.get(0));
        IForSaving instance =(IForSaving) clazz.getDeclaredConstructor().newInstance();
        instance.parse(itemsList);
        return instance;
    }
    public String getCSVDataRecursive(Object obj) throws NoSuchFieldException, IllegalAccessException {
        Class<?>c=obj.getClass().getSuperclass();
        String field="";
        Field []f= c.getDeclaredFields();
       for(Field fi:f){
           fi.setAccessible(true);
           if(fi.get(obj)!=null) {
               if(fi.get(obj).getClass().isPrimitive()||fi.get(obj).getClass().getSimpleName().equals("String"))
               field += " " + fi.get(obj);
               else {
                   field+=getCSVDataRecursive(fi.get(obj));
               }
           }
       }
       c=obj.getClass();
       f=c.getDeclaredFields();
        for(Field fi:f){
            fi.setAccessible(true);
            if(fi.get(obj)!=null) {
                if(fi.get(obj).getClass().isPrimitive()||fi.get(obj).getClass().getSimpleName().equals("String"))
                    field += " " + fi.get(obj);
                else {
                    field+=getCSVDataRecursive(fi.get(obj));
                }
            }
        }
       return field;
    }
    public boolean saveCSV(Object obj , String filePath) throws NoSuchFieldException, IllegalAccessException {
        String ans=getCSVDataRecursive(obj);
        filePath+=".csv";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.newLine(); // מוסיף מעבר שורה
            writer.write(ans);
            System.out.println("✅ התוכן נוסף בהצלחה לקובץ: " + filePath);
        } catch (IOException e) {
            System.out.println("❌ שגיאה בהוספת תוכן לקובץ: " + e.getMessage());
        }
        return true;
    }

}
