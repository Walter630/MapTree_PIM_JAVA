package com.pim.MapTree.infra;

import com.pim.MapTree.infra.exception.enterprise.CNPJExistingEnterpriseError;
import com.pim.MapTree.infra.exception.enterprise.EnterpriseExisting;
import com.pim.MapTree.infra.exception.enterprise.EnterpriseNotFound;
import com.pim.MapTree.infra.exception.funcionarios.CPFOrEmailIsExisting;
import com.pim.MapTree.infra.exception.funcionarios.FuncionarioNotFound;
import com.pim.MapTree.infra.exception.user.InvalidCredentialException;
import com.pim.MapTree.infra.exception.user.UserExisting;
import com.pim.MapTree.infra.exception.user.UserNotFound;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
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

    @ExceptionHandler(UserExisting.class)
    private ProblemDetail userExisting(UserExisting userExisting) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "User already exists");
    }

    @ExceptionHandler(InvalidCredentialException.class)
    private ProblemDetail invalidCredential(InvalidCredentialException invalidCredentialException) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Invalid credential");
    }

    // ─── Empresa ───────────────────────────────────────

    @ExceptionHandler(EnterpriseExisting.class)
    private ProblemDetail enterpriseExisting(EnterpriseExisting exception) {
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
    // ─── Funcionario ───────────────────────────────────────

    @ExceptionHandler(FuncionarioNotFound.class)
    private ProblemDetail funcionarioNotFound(FuncionarioNotFound ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Funcionario not found");
    }

    @ExceptionHandler(CPFOrEmailIsExisting.class)
    private ProblemDetail cpfNotExisting(CPFOrEmailIsExisting ex) {
        return ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, "CPF or EMAIL already exists");
    }

    // ─── Globais ───────────────────────────────────────

    //ele esta validando todos os campos com @valid / @Notnull / @Notblank etc..
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {

        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "Method argument not valid");

        //pegar todos os erros e os campos com erros e colocar na resposta
        List<String> erros = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .toList();

            problem.setProperty("erros", erros);
            return ResponseEntity.badRequest().body(problem);
        }

    //uma exceção de formato invalido no sistema, num campo de numero vir string
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            HttpHeaders headers,
            HttpStatusCode statusCode,
            WebRequest request) {
        ProblemDetail problem = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "invalid request body format");
        return ResponseEntity.badRequest().body(problem);
    }

    //erro global que nao foi mapeado aqui nos exceptions * Coringa
    //exception.getMessage() nunca pasar dessa forma
    @ExceptionHandler(Exception.class)
    public ProblemDetail genericError(Exception exception) {
        logger.error("Unexpected error occurred", exception); //erro interno
        return ProblemDetail.forStatusAndDetail(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred, contact support."); //,message generic
    }
}
