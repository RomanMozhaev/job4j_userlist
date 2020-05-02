package ru.job4j.userslist.service;

import java.util.Map;
import java.util.Objects;

/**
 * the class of user.
 */
public class User {

    private final int id;
    private final String name;
    private final String email;
    private final long createDate;
    private final String photoId;
    private final String password;
    private final String role;
    private final String city;
    private final String state;

    /**
     * this constructor is user for updating the user.
     *
     * @param id      - the id of the user
     * @param map  - the map which contains parameters.
     */
    public User(int id, Map<String, String> map) {
        this.id = id;
        this.createDate = -1;
        this.name = map.get("name");
        this.email = map.get("email");
        this.photoId = map.get("photoId");
        this.password = map.get("password");
        this.role = map.get("role");
        this.city = map.get("city");
        this.state = map.get("state");
    }

    /**
     * this constructor is used for creating a new user.
     *
     * @param map  - the map which contains parameters.
     */
    public User(Map<String, String> map) {
        this.id = -1;
        this.createDate = System.currentTimeMillis();
        this.name = map.get("name");
        this.email = map.get("email");
        this.photoId = map.get("photoId");
        this.password = map.get("password");
        this.role = map.get("role");
        this.city = map.get("city");
        this.state = map.get("state");

    }

    /**
     * this constructor is used for deleting a new user.
     *
     * @param id - the id of the user.
     */
    public User(int id) {
        this.id = id;
        this.photoId = null;
        this.name = null;
        this.email = null;
        this.createDate = -1;
        this.password = null;
        this.role = null;
        this.city = null;
        this.state = null;
    }

    /**
     * the constructor for adding to the map
     *
     * @param id         - the id of the user
     * @param createDate - the date of user creating
     * @param map  - the map which contains parameters.

     */
    public User(int id, long createDate, Map<String, String> map) {
        this.id = id;
        this.createDate = createDate;
        this.name = map.get("name");
        this.email = map.get("email");
        this.photoId = map.get("photoId");
        this.password = map.get("password");
        this.role = map.get("role");
        this.city = map.get("city");
        this.state = map.get("state");
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public long getCreateDate() {
        return createDate;
    }

    public String getPhotoId() {
        return photoId;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return Objects.equals(name, user.name)
                && Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email);
    }
}
