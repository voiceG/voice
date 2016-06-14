package com.qq;

import java.io.Serializable;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
    private String LogId;
    private String LogName;
    private String LogPassword;
    public User(String LogId,String LogName,String LogPassword){
        this.LogId=LogId;
        this.LogName=LogName;
        this.LogPassword=LogPassword;
    }
    public String getLogId() {
        return LogId;
    }
    public String getLogName() {
        return LogName;
    }
    public String getLogPassword() {
        return LogPassword;
    }
}
