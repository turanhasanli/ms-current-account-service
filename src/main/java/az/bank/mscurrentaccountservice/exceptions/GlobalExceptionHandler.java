package az.bank.mscurrentaccountservice.exceptions;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice

    public class GlobalExceptionHandler {

        @ExceptionHandler(ChangeSetPersister.NotFoundException.class)
        public ResponseEntity<ErrorResponse> handle(ChangeSetPersister.NotFoundException ex) {
            ErrorResponse errorResponse =new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setCode("NOT_FOUND");
            return ResponseEntity.status(404).body(errorResponse);
        }
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponse> handle(Exception exception) {
            ErrorResponse errorResponse =new ErrorResponse();
            errorResponse.setMessage("An unexpected error occurred. Please try again later." +exception.getMessage());
            errorResponse.setCode("INTERNAL_SERVER_ERROR");
            return ResponseEntity.status(500).body(errorResponse);
        }
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exception){
            ErrorResponse errorResponse=new ErrorResponse();
            errorResponse.setMessage(exception.getBindingResult().getFieldError().getDefaultMessage()+"Validations failed");
            errorResponse.setCode("VALIDATION_ERROR");
            return ResponseEntity.status(400).body(errorResponse);
        }
        @ExceptionHandler(IllegalStateException.class)
        public ResponseEntity<String> handleIllegalState(IllegalStateException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
        @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
        public ResponseEntity<ErrorResponse> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("The requested method is not allowed for this resource.");
            errorResponse.setCode("METHOD_NOT_ALLOWED");
            return ResponseEntity.status(405).body(errorResponse);
        }
        @ExceptionHandler(NotFoundException.class)
        public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(NotFoundException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage(ex.getMessage());
            errorResponse.setCode("CUSTOMER_NOT_FOUND");
            return ResponseEntity.status(404).body(errorResponse);
        }
        @ExceptionHandler(HttpMessageNotReadableException.class)
        public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Invalid JSON format"+ex.getMessage());
            errorResponse.setCode("INVALID_JSON");
            return ResponseEntity.status(400).body(errorResponse);
        }
        @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
        public ResponseEntity<ErrorResponse> handleHttpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException ex) {
            ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setMessage("Unsupported media type"+ex.getMessage());
            errorResponse.setCode("UNSUPPORTED_MEDIA_TYPE");
            return ResponseEntity.status(415).body(errorResponse);
        }




}
