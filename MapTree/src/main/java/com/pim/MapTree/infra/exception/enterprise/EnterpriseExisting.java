package com.pim.MapTree.infra.exception.enterprise;

public class EnterpriseExisting extends RuntimeException {
    public EnterpriseExisting() {super("Enterprise already exists");}
    public EnterpriseExisting(String message) {
        super(message);
    }
}
