package pl.mirekgab.task1.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AppRuntimeException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppRuntimeException(final AppRuntimeException ex) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(ex.getHttpStatus(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatusCode.valueOf(ex.getHttpStatus()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleException(final Exception ex) {
        ErrorResponseDTO errorDTO = new ErrorResponseDTO(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
