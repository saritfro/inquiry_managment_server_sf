package ClientServer;

import java.io.Serializable;

public class ResponseData implements Serializable {
    private ResponseStatus status;
    private String message;
    private Object result;

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public ResponseData(ResponseStatus status, String message, Object result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public ResponseData() {
        this.status = ResponseStatus.FAIL;
        this.message = "";
        this.result=null;
    }
}
