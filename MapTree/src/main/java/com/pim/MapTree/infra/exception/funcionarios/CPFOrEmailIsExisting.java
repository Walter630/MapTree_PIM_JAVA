package com.pim.MapTree.infra.exception.funcionarios;

public class CPFOrEmailIsExisting extends RuntimeException {
    public CPFOrEmailIsExisting(String message) {
        super(message);
    }
}
