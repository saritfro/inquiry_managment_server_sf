package Data;

import Business.InquiryManager;
import HandleStoreFiles.IForSaving;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public abstract class Inquiry implements IForSaving, Serializable {
    private String className;
    private	Integer code;
    private String description;
    private LocalDateTime creationDate;
    private List<String> DocumentsList;
    public static String HISTORY_FOLDER="history";
    public static String INQUIRIES_IN_TREAT_FOLDER="inquiries_in_treat";
    private String folderName;
    private InquiryStatus status;

    public void setFolderName(String folderName){
        this.folderName=folderName+ File.separator+className;
    }
    public InquiryStatus getStatus() {
        return status;
    }
    public void setStatus(InquiryStatus status) {
        this.status = status;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public LocalDateTime getCreationDate() {
        return creationDate;
    }
    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }
    public List<String> getDocumentsList() {
        return DocumentsList;
    }
    public void setDocumentsList(List<String> documentsList) {
        DocumentsList = documentsList;
    }
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

    public Inquiry(String description, List<String> DocumentsList) {
        this.className=this.getClass().getSimpleName();
        this.description = description;
        this.creationDate = LocalDateTime.now();
        this.DocumentsList=DocumentsList;
        this.folderName=INQUIRIES_IN_TREAT_FOLDER+File.separator+className;
    }

    public Inquiry() {
        this.className=this.getClass().getSimpleName();
        this.description = "";
        this.creationDate = LocalDateTime.now();
        this.DocumentsList=null;
        this.folderName=INQUIRIES_IN_TREAT_FOLDER+File.separator+className;
    }

    public void fillDataByUser(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("הכנס תאור ");
        this.description = scanner.nextLine();
    }
    public void handling(){
        System.out.print("number"+this.code);
    }


    @Override
    public String getFileName() {
        return code+"";
    }

    @Override
    public String getData() {
        String s= className+","+code+","+
         description+","+
        creationDate;
        if(this.DocumentsList!=null)
        for(String str: this.DocumentsList){
            s+=","+str;
        }
        return  s;
    }
    @Override
    public void parse(ArrayList<String> itemsList){
        this .className=itemsList.get(0);
       this.code=Integer.parseInt(itemsList.get(1));
       this.description=itemsList.get(2);
       itemsList.set(3,itemsList.get(3).substring(0,22));
       this.creationDate=LocalDateTime.parse(itemsList.get(3));
        if(this.DocumentsList!=null)
            for(int i=4;i<itemsList.size();i++){
           this.DocumentsList.add(itemsList.get(i));
       }
    }

    @Override
    public String toString() {
        return this.code+" "+this.description;
    }

    @Override
    public String getFolderName(){
        return folderName;
    }
}
