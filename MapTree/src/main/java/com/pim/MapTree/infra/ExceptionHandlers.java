package com.pim.MapTree.infra;

import com.pim.MapTree.infra.exception.*;
import com.pim.MapTree.infra.exception.enterprise.CNPJExistingEnterpriseError;
import com.pim.MapTree.infra.exception.enterprise.EnterpriseExisting;
import com.pim.MapTree.infra.exception.enterprise.EnterpriseNotFound;
import com.pim.MapTree.infra.exception.user.UserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

//classe global sempre tem acesso a ela
@RestControllerAdvice
public class ExceptionHandlers extends ResponseEntityExceptionHandler {

    // ─── User ──────────────────────────────────────────

    @ExceptionHandler(UserNotFound.class)
    private ProblemDetail userNotFound(UserNotFound userNotFound) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "User not found");
    }

    // ─── Empresa ───────────────────────────────────────

    @ExceptionHandler(EnterpriseExisting.class)
    private ProblemDetail enterpriseExisting(EnterpriseExisting exception) {
        ErrorMessage threatResponse = new ErrorMessage(HttpStatus.BAD_REQUEST,"Enterprise already exists" );
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Enterprise already exists");
    }

    @ExceptionHandler(CNPJExistingEnterpriseError.class)
    private ProblemDetail cnpjExisting(CNPJExistingEnterpriseError exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "Enterprise cnpj already exists");
    }

    @ExceptionHandler(EnterpriseNotFound.class)
    public ProblemDetail enterpriseNotFound(EnterpriseNotFound ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Enterprise not found");
    }

    // ─── Globais ───────────────────────────────────────

    //ele esta validando todos os campos com @valid / @Notnull / @Notblank etc..
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail methodArgumentNotValid(MethodArgumentNotValidException exception) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Method argument not valid");

        //pegar todos os erros e os campos com erros e colocar na resposta
        List<String> erros = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

        problem.setProperty("erros", erros);
        return problem;
    }

    //uma exceção de formato invalido no sistema, num campo de numero vir string
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ProblemDetail invalidBody(HttpMessageNotReadableException exception) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid request body format");
    }

    //erro global que nao foi mapeado aqui nos exceptions * Coringa
    //exception.getMessage() nunca pasar dessa forma
    @ExceptionHandler(Exception.class)
    public ProblemDetail genericError(Exception exception) {
        logger.error("Unexpected error occurred", exception); //erro interno
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred, contact support."); //,message generic
    }
}
