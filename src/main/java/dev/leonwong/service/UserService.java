package dev.leonwong.service;

import dev.leonwong.model.User;

public interface UserService {
    public User findUserByJwtToken(String token) throws Exception;

    public User findUserByEmail(String email) throws Exception;
}
