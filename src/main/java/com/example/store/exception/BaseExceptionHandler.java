package com.example.store.exception;


import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class BaseExceptionHandler {
    private static final String STRING_MESSAGE_FORMAT = "[%s :: %s]";

    @ExceptionHandler({BusinessLogicException.class})
    public ResponseEntity<ErrorResponse> handleBusinessLogicException(final BusinessLogicException exception) {
        final Throwable cause = exception.getCause() != null ? exception.getCause() : exception;
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(StringUtils.isBlank(exception.getErrorCode()) ? "USC-001" : exception.getErrorCode());
        errorResponse.setMessage(exception.getMessage());
        final String message = String.format(STRING_MESSAGE_FORMAT, errorResponse.getCode(),
                errorResponse.getMessage());
        log.error(message, cause);

        return new ResponseEntity<>(errorResponse, exception.getCode());
    }

    @ExceptionHandler({ModelNotFoundException.class})
    public ResponseEntity<ErrorResponse> handleModelNotFoundException(final ModelNotFoundException exception) {
        final Throwable cause = exception.getCause() != null ? exception.getCause() : exception;
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setCode(StringUtils.isBlank(exception.getErrorCode()) ? "USC-002" : exception.getErrorCode());
        errorResponse.setMessage(exception.getMessage());
        final String message = String.format(STRING_MESSAGE_FORMAT, errorResponse.getCode(),
                errorResponse.getMessage());
        log.error(message, cause);

        return new ResponseEntity<>(errorResponse, exception.getCode());
    }
}
