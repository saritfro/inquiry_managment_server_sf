package HandleStoreFiles;

import java.util.ArrayList;

public interface IForSaving {
    public String getFolderName();
    public String getFileName();
    public String getData();
    public void parse(ArrayList<String> itemsList);
}
