package kunuz.uz.api.exceptions.handler;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import tools.jackson.databind.exc.InvalidFormatException;

import java.util.*;

@ControllerAdvice
public class ExceptionHandlerClass extends ResponseEntityExceptionHandler {
    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                            HttpHeaders headers,
                                                                            HttpStatusCode status,
                                                                            WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        List<String> errors = new LinkedList<>();
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errors.add(fieldError.getDefaultMessage());
        }
        body.put("errors", errors);
        return new ResponseEntity<>(body, headers, status);
    }


    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(IllegalArgumentException ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        Class<?> requiredType = ex.getRequiredType();
        String parameter = "param";

        if(ex instanceof MethodArgumentTypeMismatchException){
            parameter = ((MethodArgumentTypeMismatchException) ex).getName();
        }

        if(requiredType != null && requiredType.equals(UUID.class)){
            body.put("error", String.format("Invalid UUID parameter was passed %s", parameter));
        }else {
            body.put("error", String.format("Invalid value formate was given for parameter %s", parameter));
        }

        return new ResponseEntity<>(body,headers,status);
    }

    @Override
    protected @Nullable ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                            HttpHeaders headers,
                                                                            HttpStatusCode status,
                                                                            WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        Throwable cause = ex.getCause();

        if (cause instanceof InvalidFormatException) {
            if (((InvalidFormatException) cause).getTargetType().isEnum()) {
                InvalidFormatException exception = (InvalidFormatException) cause;
                StringBuilder error = new StringBuilder(200);
                error.append("Invalid value (")
                        .append(exception.getValue())
                        .append(") values allowed: ");

                Object[] allowedConstants = exception.getTargetType().getEnumConstants();
                error.append(Arrays.toString(allowedConstants));
                body.put("error", error);
                return new ResponseEntity<>(body, headers, status);
            }
        }

        body.put("error", "Malformed or unreadable json body");
        return new ResponseEntity<>(body, headers, status);
    }


    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", new Date());
        body.put("status", HttpStatus.CONFLICT.value());

        body.put("error",  "The request might be violating a database integrity rule");
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }

}
