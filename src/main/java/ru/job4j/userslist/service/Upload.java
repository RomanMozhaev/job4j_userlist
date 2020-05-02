package ru.job4j.userslist.service;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * the auxiliary class shares two methods.
 */
public class Upload {

    public static Upload getUploadInstance()  {
        return new Upload();
    }

    /**
     * the method collects all fields' values to the map
     *
     * @param req
     * @return
     */
    public Map getFields(HttpServletRequest req, File repository) {
        Map<String, Object> fields = new HashMap<>();
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(repository);
        ServletFileUpload upload = new ServletFileUpload(factory);
        try {
            List<FileItem> items = upload.parseRequest(req);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    fields.put("pic", item);
                } else {
                    fields.put(item.getFieldName(), item.getString());
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        }
        return fields;
    }

    /**
     * the method uploads picture of the user.
     *
     * @param photoId
     * @return the map with fields.
     */
    public String uploadPhoto(FileItem photoId, File repository) {
        String newFilePath = "";
        File folder = new File(repository + "/images");
        if (!folder.exists()) {
            folder.mkdir();
        }
        if (photoId != null) {
            String filePath = folder + File.separator + photoId.getName();
            int i = 0;
            newFilePath = filePath;
            File file = new File(newFilePath);
            while (file.exists()) {
                i++;
                newFilePath = folder + File.separator + i + "_" + photoId.getName();
                file = new File(newFilePath);
            }
            try (FileOutputStream out = new FileOutputStream(file)) {
                out.write(photoId.getInputStream().readAllBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newFilePath;
    }

}