package com.nemo.neplan.service;

import com.nemo.neplan.model.File;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    File uploadFile(MultipartFile file);
    Resource loadFileAsResource(String fileName);

}