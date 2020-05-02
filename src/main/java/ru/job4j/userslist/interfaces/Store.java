package ru.job4j.userslist.interfaces;

import ru.job4j.userslist.service.User;

import java.util.Map;


/**
 * the interface for working with memory class.
 */
public interface Store {

    /**
     * returns the map with users.
     *
     * @return
     */
    Map<Integer, User> findAll();

    /**
     * adds the new user if the id is not mapped in the map.
     *
     * @param user the new user.
     * @return - true if the user was added; otherwise false.
     */
    int add(User user);

    /**
     * deletes the user from the map.
     *
     * @param user - the user for deleting.
     * @return true if deleted; otherwise false.
     */
    boolean delete(User user);

    /**
     * updates data for the mapped user.
     *
     * @param user - the user with field for updating.
     * @return - true if the user was updated; otherwise false.
     */
    boolean update(User user);

    /**
     * returns the found user by id.
     *
     * @param id - the user id
     * @return user if found; otherwise null.
     */
    User findById(int id);
}