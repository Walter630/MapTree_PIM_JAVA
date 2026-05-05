package com.pim.MapTree.infra.exception.user;

public class UserNotFound extends RuntimeException {
    public UserNotFound() {
        super("User not found");
    }
    public UserNotFound(String message) {
        super(message);
    }
}
