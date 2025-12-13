package com.auth.exception;

import com.auth.dto.ErrorResponseDto;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class AuthserverExceptionHandling {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> userNotFound(EntityNotFoundException entityNotFoundException, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(entityNotFoundException.getMessage());
        errorResponseDto.setHttpStatus(HttpStatus.NOT_FOUND);
        errorResponseDto.setPath(webRequest.getContextPath());
        return new ResponseEntity<>(errorResponseDto,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgNotValid(MethodArgumentNotValidException methodArgumentNotValidException, WebRequest webRequest){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        List<ObjectError> allErrors = methodArgumentNotValidException.getBindingResult().getAllErrors();
        Map<String,String> errorMsgMap = new HashMap<>();
        allErrors.forEach(error->{
            String field = ((FieldError)error).getField();
            String msg = error.getDefaultMessage();
            errorMsgMap.put(field,msg);
        });
        return new ResponseEntity<>(errorMsgMap,HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<String>> constraintNotValid(ConstraintViolationException constraintViolationException){
        List<String> allErrorMsg = constraintViolationException.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();
        return new ResponseEntity<>(allErrorMsg,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> genaralException(Exception e){
        ErrorResponseDto errorResponseDto = new ErrorResponseDto();
        errorResponseDto.setMessage(e.getMessage());
        errorResponseDto.setHttpStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(errorResponseDto,HttpStatus.INTERNAL_SERVER_ERROR);
    }


}
