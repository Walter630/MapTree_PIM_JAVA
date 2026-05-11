package com.pim.MapTree.infra.exception.user;

public class UserExisting extends RuntimeException {
    public UserExisting(String message) {
        super(message);
    }
}
