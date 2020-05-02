package ru.job4j.userslist.servlets;

import org.apache.commons.fileupload.FileItem;
import org.json.JSONObject;
import ru.job4j.userslist.service.Upload;
import ru.job4j.userslist.service.User;
import ru.job4j.userslist.interfaces.Validate;
import ru.job4j.userslist.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * the servlet for creating a new user
 */
public class UserCreateServlet extends HttpServlet {

    /**
     * the instance with working methods.
     */
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Upload upload = Upload.getUploadInstance();
        HttpSession session = req.getSession();
        File repository = (File) session.getServletContext().getAttribute("javax.servlet.context.tempdir");
        Map<String, Object> fields = upload.getFields(req, repository);
        String name = (String) fields.get("name");
        String email = (String) fields.get("email");
        FileItem photoId = (FileItem) fields.get("pic");
        String password = (String) fields.get("pass");
        String role = (String) fields.get("role");
        String city = (String) fields.get("city");
        String state = (String) fields.get("state");
        String photoPath = upload.uploadPhoto(photoId, repository);
        Map<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("email", email);
        map.put("photoId", photoPath);
        map.put("password", password);
        map.put("role", role);
        map.put("city", city);
        map.put("state", state);
        User user = new User(map);
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject status = new JSONObject();
        Integer result = this.validate.add(user);
        User addedUser = this.validate.findById(result);
        if (result > -1) {
            status.put("status", "valid");
            status.put("id", result.toString());
            status.put("pic", addedUser.getPhotoId());
            status.put("date", addedUser.getCreateDate());
        } else {
            status.put("status", "invalid");
            new File(photoPath).delete();
        }
        writer.append(status.toString());
        writer.flush();
    }
}