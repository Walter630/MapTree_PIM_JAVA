package com.pim.MapTree.infra.exception.enterprise;

import java.util.UUID;

public class EnterpriseNotFound extends RuntimeException {
    public EnterpriseNotFound(UUID id) {super("Empresa nao encontrada");    }
    public EnterpriseNotFound(String message) {
        super(message);
    }
}
