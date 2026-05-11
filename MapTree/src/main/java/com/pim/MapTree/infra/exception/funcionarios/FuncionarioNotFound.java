package com.pim.MapTree.infra.exception.funcionarios;

public class FuncionarioNotFound extends RuntimeException {
    public FuncionarioNotFound(String message) {
        super(message);
    }
}
