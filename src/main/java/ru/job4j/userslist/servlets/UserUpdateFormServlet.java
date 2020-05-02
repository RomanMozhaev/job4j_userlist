package ru.job4j.userslist.servlets;

import org.json.JSONObject;
import ru.job4j.userslist.service.User;
import ru.job4j.userslist.interfaces.Validate;
import ru.job4j.userslist.service.ValidateService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * the servlet for creating update form
 */
public class UserUpdateFormServlet extends HttpServlet {

    /**
     * the instance with working methods.
     */
    private final Validate validate = ValidateService.getInstance();

    /**
     * the method for user data updating.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        User user = this.validate.findById(Integer.parseInt(id));
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        JSONObject status = new JSONObject();
        if (user != null) {
            status.put("status", "valid");
            status.put("id", user.getId());
            status.put("name", user.getName());
            status.put("email", user.getEmail());
            status.put("city", user.getCity());
            status.put("state", user.getState());
        } else {
            status.put("status", "invalid");
        }
        writer.append(status.toString());
        writer.flush();
    }
}