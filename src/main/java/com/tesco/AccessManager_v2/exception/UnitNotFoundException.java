package com.tesco.AccessManager_v2.exception;

public class UnitNotFoundException extends RuntimeException{
    public UnitNotFoundException(int unitId)
    {
        super(unitId + " not found");
    }
}
