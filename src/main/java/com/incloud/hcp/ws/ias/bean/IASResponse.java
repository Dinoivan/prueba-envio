package com.incloud.hcp.ws.ias.bean;

import java.util.Map;

public class IASResponse {

    // GS
    private String status;
    private String message;
    private String body;
    Map<String,Object> resultMap;
    private IASUserInfoResponse result;
    private String id;

    //
    private String messageException;           // Exception > getMessage()
    private String messageCause;
    private Throwable cause;                  // Exception > getCause()

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getMessageException() {
        return messageException;
    }

    public void setMessageException(String messageException) {
        this.messageException = messageException;
    }

    public String getMessageCause() {
        return messageCause;
    }

    public void setMessageCause(String messageCause) {
        this.messageCause = messageCause;
    }

    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    public IASUserInfoResponse getResult() {
        return result;
    }

    public void setResult(IASUserInfoResponse result) {
        this.result = result;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "IASResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", body='" + body + '\'' +
                ", result=" + result +
                ", messageException='" + messageException + '\'' +
                ", messageCause='" + messageCause + '\'' +
                ", cause=" + cause +
                '}';
    }
}
