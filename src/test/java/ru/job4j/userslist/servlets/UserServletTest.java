package ru.job4j.userslist.servlets;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import ru.job4j.userslist.interfaces.Validate;
import ru.job4j.userslist.service.User;
import ru.job4j.userslist.service.ValidateService;
import ru.job4j.userslist.service.Upload;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ValidateService.class, Upload.class, HttpSession.class})
@PowerMockIgnore({"org.apache.logging.log4j.*", "com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "org.w3c.dom.*", "javax.management.*"})
public class UserServletTest {

    /**
     * this test for UserCreateServlet.
     * ValidateStub - stub for ValidateService
     * TestUpload - stab for Upload
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void whenCreateThenReturnName() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        Upload testUpload = new TestUpload();
        PowerMockito.mockStatic(ValidateService.class);
        PowerMockito.mockStatic(Upload.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(Upload.getUploadInstance()).thenReturn(testUpload);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext context = mock(ServletContext.class);
        ServletOutputStream stream = mock(ServletOutputStream.class);
        when(request.getSession()).thenReturn(session);
        when(response.getOutputStream()).thenReturn(stream);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("javax.servlet.context.tempdir")).thenReturn(new File(""));
        UserCreateServlet servlet = new UserCreateServlet();
        servlet.doPost(request, response);
        Map<Integer, User> map = validate.findAll();
        String resultName = map.entrySet().iterator().next().getValue().getName();
        assertThat(resultName, is("User Test"));
    }

    /**
     * this test for UserUpdateServlet.
     * ValidateStub - stub for ValidateService
     * TestUpload - stab for Upload
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void whenUpdateThenReturnNewName() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        Upload testUpload = new TestUpload();
        PowerMockito.mockStatic(ValidateService.class);
        PowerMockito.mockStatic(Upload.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        when(Upload.getUploadInstance()).thenReturn(testUpload);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext context = mock(ServletContext.class);
        RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        ServletOutputStream stream = mock(ServletOutputStream.class);
        when(request.getSession()).thenReturn(session);
        when(response.getOutputStream()).thenReturn(stream);
        when(session.getServletContext()).thenReturn(context);
        when(context.getAttribute("javax.servlet.context.tempdir")).thenReturn(new File(""));
        when(context.getRequestDispatcher("/WEB-INF/result.jsp")).thenReturn(dispatcher);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "User1");
        userMap.put("email", "user@mail");
        userMap.put("password", "password");
        userMap.put("role", "user");
        userMap.put("photoId", "");
        userMap.put("city", "MSK");
        userMap.put("state", "Russia");
        validate.add(new User(userMap));
        UserUpdateServlet servlet = new UserUpdateServlet();
        servlet.doPost(request, response);
        Map<Integer, User> map = validate.findAll();
        String resultName = map.entrySet().iterator().next().getValue().getName();
        assertThat(resultName, is("User Test"));
    }

    /**
     * this test for UserDeleteServlet.
     * ValidateStub - stub for ValidateService
     *
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void whenDeleteThenNUserFound() throws ServletException, IOException {
        Validate validate = new ValidateStub();
        PowerMockito.mockStatic(ValidateService.class);
        when(ValidateService.getInstance()).thenReturn(validate);
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        ServletContext context = mock(ServletContext.class);
        ServletOutputStream stream = mock(ServletOutputStream.class);
        when(request.getSession()).thenReturn(session);
        when(response.getOutputStream()).thenReturn(stream);
        when(session.getServletContext()).thenReturn(context);
        Map<String, String> userMap = new HashMap<>();
        userMap.put("name", "User1");
        userMap.put("email", "user@mail");
        userMap.put("password", "password");
        userMap.put("role", "user");
        userMap.put("photoId", "");
        userMap.put("city", "MSK");
        userMap.put("state", "Russia");
        validate.add(new User(userMap));
        int previousSize = validate.findAll().size();
        UserDeleteServlet servlet = new UserDeleteServlet();
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("id")).thenReturn("1");
        servlet.doPost(request, response);
        int currentSize = validate.findAll().size();
        assertThat(currentSize, is(previousSize - 1));
    }
}