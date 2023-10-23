package com.sq.datahandler;

/**
 * Created by TKMAHGF on 10/13/2017.
 */
public class DataHandlingException extends RuntimeException {
    public DataHandlingException() {
        super();
    }
    public DataHandlingException(String s) {
        super(s);
    }
    public DataHandlingException(String s, Throwable throwable) {
        super(s, throwable);
    }
    public DataHandlingException(Throwable throwable) {
        super(throwable);
    }
}