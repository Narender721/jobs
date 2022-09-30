package com.tesco.AccessManager_v2.exception;

public class IdNullException extends RuntimeException{
    public IdNullException(){
        super("Provide proper ID value");
    }
}
