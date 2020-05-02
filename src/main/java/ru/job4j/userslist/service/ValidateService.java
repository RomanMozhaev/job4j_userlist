package ru.job4j.userslist.service;

import ru.job4j.userslist.connectors.DBStore;
import ru.job4j.userslist.interfaces.Store;
import ru.job4j.userslist.interfaces.Validate;

import java.util.HashMap;
import java.util.Map;

/**
 * the class for data validating.
 */
public class ValidateService implements Validate {

    /**
     * the instance of singleton
     */
    private static final ValidateService INSTANCE = new ValidateService();

    /**
     * the memory store.
     */
    private final Store memory = DBStore.getInstance();

    /**
     * the main constructor.
     */
    private ValidateService() {

    }

    /**
     * returns the instance of the singleton.
     *
     * @return - the instance.
     */
    public static Validate getInstance() {
        return INSTANCE;
    }

    /**
     * adds a new user to the memory.
     *
     * @param user - the user for adding
     * @return true if the user was added; otherwise false.
     */
    @Override
    public int add(User user) {
        int result = -1;
        if (!user.getName().equals("")) {
            result = this.memory.add(user);
        }
        return result;
    }

    /**
     * updates the mapped user.
     *
     * @param user - the user instance with new field.
     * @return - true if updating was successful; otherwise false.
     */
    @Override
    public boolean update(User user) {
        boolean result = false;
        String name;
        String email;
        long date;
        int id;
        String photoId;
        String password;
        String role;
        String city;
        String state;
        User listedUser = findById(user.getId());
        if (listedUser != null) {
            id = listedUser.getId();
            date = listedUser.getCreateDate();
            if (!user.getName().equals("")) {
                name = user.getName();
            } else {
                name = listedUser.getName();
            }
            if (!user.getEmail().equals("")) {
                email = user.getEmail();
            } else {
                email = listedUser.getEmail();
            }
            if (!user.getPhotoId().equals("")) {
                photoId = user.getPhotoId();
            } else {
                photoId = listedUser.getPhotoId();
            }
            if (!user.getPassword().equals("")) {
                password = user.getPassword();
            } else {
                password = listedUser.getPassword();
            }
            if (!user.getRole().equals("none")) {
                role = user.getRole();
            } else {
                role = listedUser.getRole();
            }
            if (!user.getCity().equals("")) {
                city = user.getCity();
            } else {
                city = listedUser.getCity();
            }
            if (!user.getState().equals("")) {
                state = user.getState();
            } else {
                state = listedUser.getState();
            }
            Map<String, String> map = new HashMap<>();
            map.put("name", name);
            map.put("email", email);
            map.put("photoId", photoId);
            map.put("password", password);
            map.put("role", role);
            map.put("city", city);
            map.put("state", state);

            result = this.memory.update(new User(id, date, map));
        }
        return result;
    }

    /**
     * deletes the user for the map. id filed used only for identification.
     *
     * @param user - the user for deleting.
     * @return true if the entry was deleted; otherwise false.
     */
    @Override
    public boolean delete(User user) {
        return this.memory.delete(user);
    }

    /**
     * returns all mapped data.
     *
     * @return the string with data.
     */
    @Override
    public Map<Integer, User> findAll() {
        return this.memory.findAll();
    }

    /**
     * finds the mapped user by id.
     *
     * @param id - the id for searching
     * @return found user or null.
     */
    @Override
    public User findById(int id) {

        return this.memory.findById(id);
    }

    public int isCredential(String name, String password) {
        int result = -1;
        for (Map.Entry<Integer, User> entry : this.memory.findAll().entrySet()) {
            User user = entry.getValue();
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                result = user.getId();
                break;
            }
        }
        return result;
    }
}