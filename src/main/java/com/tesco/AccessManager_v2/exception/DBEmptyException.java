package com.tesco.AccessManager_v2.exception;

public class DBEmptyException extends RuntimeException{
    public DBEmptyException(){
        super("Database empty");
    }
}
