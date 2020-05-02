package ru.job4j.userslist.servlets;

import org.apache.commons.fileupload.FileItem;
import ru.job4j.userslist.service.Upload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * the class for test.
 */
public class TestUpload extends Upload {

    /**
     * the method for test
     * @param req
     * @param repository
     * @return
     */
    @Override
    public Map<String, Object> getFields(HttpServletRequest req, File repository) {
        Map<String, Object> fields = new HashMap<>();
        fields.put("name", "User Test");
        fields.put("email", "user@test");
        fields.put("photoId", null);
        fields.put("password", "password");
        fields.put("role", "user");
        fields.put("id", "1");
        return fields;
    }


    /**
     * the method for test.
     *
     * @param photoId
     * @return
     */
    @Override
    public String uploadPhoto(FileItem photoId, File repository) {
        return "";
    }

}
