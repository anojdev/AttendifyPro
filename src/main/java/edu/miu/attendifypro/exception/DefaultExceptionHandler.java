package edu.miu.attendifypro.exception;

import edu.miu.attendifypro.dto.common.ApiResponse;
import edu.miu.attendifypro.dto.common.ErrorField;
import edu.miu.attendifypro.dto.common.MessageErrorNode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author kush
 */
@ControllerAdvice
@Slf4j
public class DefaultExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleAuthenticationException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ApiResponse<String> respData = new ApiResponse<>();
        respData.setMessage(ex.getMessage());
        respData.setStatus(false);
        respData.setData(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respData);
    }

    @ExceptionHandler({AuthorizationServiceException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleAuthorizationException(HttpServletRequest request, HttpServletResponse response, Exception ex) {
        ApiResponse<String> respData = new ApiResponse<>();
        respData.setMessage(ex.getMessage());
        respData.setStatus(false);
        respData.setData(null);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(respData);
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleAuthorizationException(Exception ex) {
        ApiResponse<String> respData = new ApiResponse<>();
        respData.setMessage(ex.getMessage());
        respData.setStatus(false);
        respData.setData(null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(respData);
    }


    @ExceptionHandler({BadCredentialsException.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleBadCredentialsException(Exception ex) {
        ApiResponse<String> respData = new ApiResponse<>();
        respData.setMessage(ex.getMessage());
        respData.setStatus(false);
        respData.setData(null);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(respData);
    }

    @ExceptionHandler({HttpClientErrorException.NotFound.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleNotFoundException(Exception ex) {
        ApiResponse<String> respData = new ApiResponse<>();
        respData.setMessage(ex.getMessage());
        respData.setStatus(false);
        respData.setData(null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respData);
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<ApiResponse<String>> handleInternalCheckedException(Exception ex) {
        ApiResponse<String> respData = new ApiResponse<>();
        respData.setMessage(ex.getMessage());
        respData.setStatus(false);
        respData.setData(null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(respData);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Exception exception = new Exception(ex.getBindingResult().getAllErrors().get(0).getDefaultMessage());
        List<String> errorMessages = new ArrayList<>();
        BindingResult bindingResult = ex.getBindingResult();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError error : fieldErrors) {
            String message = error.getField() + " - "
                    + error.getDefaultMessage() + " - "
                    + error.getRejectedValue();
            errorMessages.add(message);
        }

        List<ErrorField> fields = new ArrayList<>();
        Map<String, List<FieldError>> errorMap = fieldErrors.stream().collect(Collectors.groupingBy(FieldError::getField));
        errorMap.forEach((key, value) -> fields.add(ErrorField.builder()
                .field(key)
                .messages(value.stream().map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList())
                .build()));

        return this.createResponseEntity(HttpStatus.BAD_REQUEST, exception, request, fields);
    }

    private ResponseEntity<Object> createResponseEntity(HttpStatus httpStatus,
                                                        Exception ex,
                                                        WebRequest request,
                                                        List<ErrorField> errorFields) {
        boolean concatResponse = false;
        ApiResponse.ApiResponseBuilder errorResponse = ApiResponse.builder()
                .message("Issue validating message request.");
        if (concatResponse) {
            String message = errorFields.stream().map(x -> x.getMessages()
                    .stream()
                    .map(y -> "Field " + x.getField() + " " + y)
                    .collect(Collectors.joining(". "))
            ).collect(Collectors.joining(". "));
            errorResponse.message("Issue validating message request: " + message);
            errorResponse.status(false);
            errorResponse.code(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        } else {
            errorResponse.errors(MessageErrorNode.builder().fields(errorFields).build());
        }
        return handleExceptionInternal(ex, errorResponse.build(),
                new HttpHeaders(), httpStatus, request);
    }
}