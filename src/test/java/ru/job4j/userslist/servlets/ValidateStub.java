package ru.job4j.userslist.servlets;

import ru.job4j.userslist.interfaces.Validate;
import ru.job4j.userslist.service.User;

import java.util.HashMap;
import java.util.Map;

public class ValidateStub implements Validate {
    private final Map<Integer, User> store = new HashMap<>();
    private int ids = 1;

    @Override
    public int add(User user) {
        int id = ids++;
        Map<String, String> map = new HashMap<>();
        map.put("name", user.getName());
        map.put("email", user.getEmail());
        map.put("photoId", user.getPhotoId());
        map.put("password", user.getPassword());
        map.put("role", user.getRole());
        map.put("city", user.getCity());
        map.put("state", user.getState());
        User newUser = new User(id, map);
        this.store.put(id, newUser);
        return id;
    }

    @Override
    public boolean update(User user) {
        return this.store.put(user.getId(), user) != null;
    }

    @Override
    public boolean delete(User user) {
        return this.store.remove(user.getId()) != null;
    }

    @Override
    public Map<Integer, User> findAll() {
        return this.store;
    }

    @Override
    public User findById(int id) {
        return this.store.get(id);
    }

    @Override
    public int isCredential(String name, String password) {
        return 0;
    }
}
