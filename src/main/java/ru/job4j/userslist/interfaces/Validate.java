package ru.job4j.userslist.interfaces;

import ru.job4j.userslist.service.User;

import java.util.Map;

/**
 * the interface for validating classes.
 */
public interface Validate {

    /**
     * adds a new user to the memory.
     */
    int add(User user);

    /**
     * updates the mapped user.
     */
    boolean update(User user);

    /**
     * deletes the user for the map.
     */
    boolean delete(User user);

    /**
     * returns all mapped data.
     */
    Map<Integer, User> findAll();

    /**
     * finds the mapped user by id.
     */
    User findById(int id);

    /**
     * the method for checking the user name and password.
     * @param name the user name.
     * @param password the user password.
     * @return true if name and password matched; otherwise false.
     */
    int isCredential(String name, String password);
}