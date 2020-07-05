package ru.geekbrains.spring.ishop.service;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.UUID;

@Service
public class ImageSaverService {
    private static final String UPLOADED_FOLDER = "./images/amin/";

//    public String saveFile(MultipartFile file) {
//        if (file.isEmpty()) {
//            return "";
//        }
//        String fileName = UUID.randomUUID().toString() + file.getOriginalFilename();
//        try {
//            Path path = Paths.get(UPLOADED_FOLDER + fileName);
//            file.transferTo(path);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return fileName;
//    }
    public String saveFile(MultipartFile file, String subDirName) {
        if (file.isEmpty()) {
            return "";
        }
        String fileName = Paths.get(subDirName, file.getOriginalFilename()).toString();
        try {
            Path path = Paths.get(UPLOADED_FOLDER, fileName);
            file.transferTo(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }

    public void deleteFile(String pathToFile) {
        try {
            Files.deleteIfExists(Paths.get(UPLOADED_FOLDER, pathToFile));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MultipartFile getFile(String pathToFile) {
//        String pathname = Paths.get(UPLOADED_FOLDER, "products/", pathToFile).toString();
        String pathname = Paths.get(UPLOADED_FOLDER, pathToFile).toString();
        try {
//            File file = new File(pathname);
//            System.out.println(file);
//
//            MultipartFile multipartFile = new MockMultipartFile(pathToFile,
//                    new FileInputStream(file));
//
//            return multipartFile;
            return new MockMultipartFile(pathToFile,
                    new FileInputStream(new File(pathname)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}

