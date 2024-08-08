package cc.maids.librarymanagement.user.Exception;

import cc.maids.librarymanagement.exception.ErrorResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> badCredentials(BadCredentialsException e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Credentials");
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ErrorResponse> jwtError(JwtException e) {
        ErrorResponse errorResponse = new ErrorResponse("Invalid Token");
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }

}
