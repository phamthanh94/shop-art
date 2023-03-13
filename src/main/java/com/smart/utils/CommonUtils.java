package com.smart.utils;

import com.smart.constants.AppConstant;
import com.smart.helper.Message;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CommonUtils {

    public static void alertSuccess(HttpSession session) {
        session.setAttribute("message", new Message(AppConstant.MESSAGE_COMM.SUCCESS, "alert-success"));
    }

    public static void alertFail(HttpSession session) {
        session.setAttribute("message", new Message(AppConstant.MESSAGE_COMM.FAIL, "alert-danger"));
    }

    public static void alertWarn(HttpSession session) {
        session.setAttribute("message", new Message("Something went wrong !! Try again ", "alert-danger"));
    }

    public static Path writeFile(MultipartFile multipartFile) throws IOException {
        File fileObject = new ClassPathResource("static/img").getFile();
        Path path = Paths.get(fileObject.getAbsolutePath() + File.separator + multipartFile.getOriginalFilename());
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        return path;
    }


}
