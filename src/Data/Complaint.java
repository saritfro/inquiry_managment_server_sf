package Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Scanner;


public class Complaint extends Inquiry{
   private String assignedBranch;

    public String getAssignedBranch() {
        return assignedBranch;
    }

    public void setAssignedBranch(String assignedBranch) {
        this.assignedBranch = assignedBranch;
    }

    public Complaint(String description, String assignedBranch, List<String> DocumentsList) {
        super(description,DocumentsList);
        this.assignedBranch = assignedBranch;
    }

    public Complaint() {
        super();
        this.assignedBranch = "";
    }

    public void fillDataByUser(){
        super.fillDataByUser();
        Scanner scanner = new Scanner(System.in);
        System.out.println("הכנס סניף עליו מדוברת התלונה: ");
        this.assignedBranch = scanner.nextLine(); // קלט מחרוזת
    }
    public void handling(){
        System.out.println("number "+this.getCode()+" Complaint");
    }

    @Override
    public String getData() {
        String s=super.getData();
        s+=","+assignedBranch;
        return s;
    }
    public void parse(ArrayList<String> itemsList){
        super.parse(itemsList);
        if(this.getDocumentsList()!=null)
        this.getDocumentsList().remove(itemsList.size()-1);
        this.assignedBranch=itemsList.get(itemsList.size()-1);
    }

    }
