package org.study.exception;

/**
 * 커스텀 에러 핸들러
 */
public class InvalidInputException extends RuntimeException{
    public InvalidInputException(String message){
        super(message);
    }
}
