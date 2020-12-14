package com.example.crime.Repository;

import com.example.crime.Model.User;

import java.util.List;
import java.util.UUID;

public interface UserIRepository {


    List<User> getUsers();
    User getUser(UUID uuid);
    void insertUser(User user);
    void deleteUser(User user);
    void UpdateUser(User user);
    int sizeList();
    User validUser(String password);

}
