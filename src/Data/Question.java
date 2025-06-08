package Data;

import java.io.Serializable;
import java.util.List;

public class Question extends Inquiry {
    public Question(String description, List<String> DocumentsList) {
        super(description, DocumentsList);
    }

    public Question() {

    }

    public void handling(){
        System.out.println("number "+this.getCode()+" Question");
    }



}
