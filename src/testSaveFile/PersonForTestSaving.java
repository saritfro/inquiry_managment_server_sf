package testSaveFile;


import HandleStoreFiles.IForSaving;

import java.util.ArrayList;

public class PersonForTestSaving implements IForSaving {

    String id;
    String name;

    public PersonForTestSaving(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getFolderName() {
        return getClass().getPackage().getName();
    }

    public String getFileName() {
        return getClass().getSimpleName() + id;
    }

    public String getData() {
        return id + "," + name;
    }
    public void parse(ArrayList<String> itemsList){
        System.out.println();
    }
}
