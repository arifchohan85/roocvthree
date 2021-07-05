package com.egeroo.roocvthree.core.error;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CoreExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CoreException.class)
    @ResponseBody
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> internalServerErrorHandler(CoreException ex) {
        Map<String, Object> m1 = new HashMap<String, Object>();
        m1.put("status", ex.getStatus().getReasonPhrase());
        m1.put("message", ex.getMessage());
        return new ResponseEntity<Object>(m1, ex.getStatus());
    }
    
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {
    	ValidationError error = ValidationErrorBuilder.fromBindingErrors(exception.getBindingResult());
    	return super.handleExceptionInternal(exception, error, headers, status, request);
    }
    
    /*@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public void notFoundHandler() {
    	throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "System goes wrong in query or null pointer");
    }*/
    
    
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception ex, 
                HttpServletRequest request, HttpServletResponse response) {
    	Map<String, Object> m1 = new HashMap<String, Object>();
        m1.put("status", HttpStatus.INTERNAL_SERVER_ERROR);
        m1.put("message", "System error ,  500 Bad Request");
        //m1.put("message", "System error , error in query or null pointer");
        if (ex instanceof NullPointerException) {
        	return new ResponseEntity<Object>(m1,HttpStatus.INTERNAL_SERVER_ERROR);
            //return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        	//throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "System goes wrong in query or null pointer");
        }
        //return new ResponseEntity<Object>(m1,HttpStatus.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        //throw new CoreException(HttpStatus.INTERNAL_SERVER_ERROR, "System goes wrong in query or null pointer");
    }
    
    
    
    /*
    @ExceptionHandler(CoreException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(MethodArgumentNotValidException exception) {
        return error(exception.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.toList()));
    }


    @ExceptionHandler(CoreException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map handle(ConstraintViolationException exception) {
        return error(exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList()));
    }

    private Map error(Object message) {
        return Collections.singletonMap("error", message);
    }
    */
}