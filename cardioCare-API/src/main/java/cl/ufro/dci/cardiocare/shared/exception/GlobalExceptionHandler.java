package cl.ufro.dci.cardiocare.shared.exception;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // -----------------------------
    // 1) VALIDACIONES @Valid
    // -----------------------------
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, Object> body = baseBody(HttpStatus.BAD_REQUEST);

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(
                error -> errors.put(error.getField(), error.getDefaultMessage())
        );

        body.put("errors", errors);
        body.put("message", "Error de validación en los datos enviados");

        return ResponseEntity.badRequest().body(body);
    }

    // -----------------------------
    // 2) VALIDACIONES MANUALES (ConstraintViolation)
    // -----------------------------
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintErrors(ConstraintViolationException ex) {
        Map<String, Object> body = baseBody(HttpStatus.BAD_REQUEST);

        Map<String, String> errors = new HashMap<>();
        ex.getConstraintViolations().forEach(
                cv -> errors.put(
                        cv.getPropertyPath().toString(),
                        cv.getMessage()
                )
        );

        body.put("errors", errors);
        body.put("message", "Violación de restricciones");

        return ResponseEntity.badRequest().body(body);
    }

    // -----------------------------
    // 3) NOT FOUND
    // -----------------------------
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleEntityNotFound(EntityNotFoundException ex) {
        Map<String, Object> body = baseBody(HttpStatus.NOT_FOUND);
        body.put("message", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // -----------------------------
    // 4) ENDPOINT NO EXISTE
    // -----------------------------
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoHandlerFoundException ex) {
        Map<String, Object> body = baseBody(HttpStatus.NOT_FOUND);
        body.put("message", "El endpoint solicitado no existe");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(body);
    }

    // -----------------------------
    // 5) ERRORES DE NEGOCIO CUSTOM
    // -----------------------------
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalState(IllegalStateException ex) {
        Map<String, Object> body = baseBody(HttpStatus.BAD_REQUEST);
        body.put("message", ex.getMessage());
        return ResponseEntity.badRequest().body(body);
    }

    // -----------------------------
    // 6) CUALQUIER EXCEPCIÓN NO MANEJADA (FALLO INTERNO)
    // -----------------------------
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        ex.printStackTrace(); // Útil en desarrollo

        Map<String, Object> body = baseBody(HttpStatus.INTERNAL_SERVER_ERROR);
        body.put("message", "Ha ocurrido un error inesperado. Intente nuevamente.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
    }

    // -----------------------------
    // UTILIDAD: FORMATO BASE PARA TODAS LAS RESPUESTAS DE ERROR
    // -----------------------------
    private Map<String, Object> baseBody(HttpStatus status) {
        Map<String, Object> map = new HashMap<>();
        map.put("timestamp", Instant.now());
        map.put("status", status.value());
        map.put("error", status.getReasonPhrase());
        return map;
    }
}
