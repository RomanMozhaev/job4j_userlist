package ru.job4j.userslist.servlets;

import org.json.JSONObject;
import ru.job4j.userslist.service.User;
import ru.job4j.userslist.interfaces.Validate;
import ru.job4j.userslist.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * the servlet deletes the user from the memory.
 */
public class UserDeleteServlet extends HttpServlet {

    /**
     * the instance with working methods.
     */
    private final Validate validate = ValidateService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stgId = req.getParameter("id");
        String message = "invalid";
        String photo;
        User user = this.validate.findById(intOrDef(stgId, -1));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject status = new JSONObject();
        if (user != null) {
            photo = user.getPhotoId();
            if (this.validate.delete(user)) {
                message = "valid";
                new File(photo).delete();
            }
        }
        status.put("status", message);
        writer.append(status.toString());
        writer.flush();
    }

    /**
     * the method converts string to int or return def
     *
     * @param srgId - the string for converting to int
     * @param def   - default int
     * @return int
     */
    private int intOrDef(String srgId, int def) {
        int id;
        try {
            id = Integer.parseInt(srgId);
        } catch (NumberFormatException e) {
            id = def;
        }
        return id;
    }
}