package com.pim.MapTree.infra.exception.enterprise;

public class CNPJExistingEnterpriseError extends RuntimeException {
    public CNPJExistingEnterpriseError() {super("CNPJ existing for Enterprise");}
    public CNPJExistingEnterpriseError(String message) {
        super(message);
    }
}
