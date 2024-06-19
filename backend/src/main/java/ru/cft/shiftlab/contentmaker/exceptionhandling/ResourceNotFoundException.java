package ru.cft.shiftlab.contentmaker.exceptionhandling;

public class ResourceNotFoundException extends IllegalArgumentException{
    public ResourceNotFoundException(String message){
        super(message);
    }
}
