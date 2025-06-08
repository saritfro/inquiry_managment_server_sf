package Agents;

import HandleStoreFiles.IForSaving;

import java.util.ArrayList;

public class Agent implements IForSaving {
    private String Name;
    private int Id;

    public Agent(int id, String name) {
        Id = id;
        Name = name;
    }
    public Agent(){
        Id=1;
        Name="ee";
    }
    public int getId() {return Id;}

    public void setId(int id) {Id = id;}

    @Override
    public String getFolderName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getFileName() { return  Id+"";

    }



    @Override
    public String getData() {
        return  this.getClass().getName()+","+Id+","+Name;
    }

    @Override
    public void parse(ArrayList<String> itemsList) {
        this.Name=itemsList.get(2);
        this.Id=Integer.parseInt(itemsList.get(1));
    }



    @Override
    public String toString() {
        return "Agent{" +
                "Name='" + Name + '\'' +
                ", Id=" + Id +
                '}';
    }
}
