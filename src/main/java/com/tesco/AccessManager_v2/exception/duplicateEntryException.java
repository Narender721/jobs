package com.tesco.AccessManager_v2.exception;

public class duplicateEntryException extends RuntimeException{

    public duplicateEntryException(int id){
        super(id + " already exists");
    }
}
