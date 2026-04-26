package com.sky.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@RestController
@RequestMapping("/user/common")
@Api(tags = "用户端通用接口")
@Slf4j
public class UserCommonController {

    @Value("${sky.upload.path:upload}")
    private String uploadPath;

    @GetMapping("/download")
    @ApiOperation("图片下载")
    public void download(@RequestParam("name") String name, HttpServletResponse response) {
        log.info("图片下载：{}", name);

        OutputStream outputStream = null;
        FileInputStream fis = null;
        try {
            String projectPath = System.getProperty("user.dir");
            
            if (name.startsWith("/upload/")) {
                name = name.substring("/upload/".length());
            } else if (name.startsWith("upload/")) {
                name = name.substring("upload/".length());
            }
            
            File file = new File(projectPath, uploadPath + "/" + name);

            if (!file.exists()) {
                log.error("文件不存在：{}", file.getAbsolutePath());
                response.setStatus(404);
                return;
            }

            response.setContentType("image/jpeg");

            fis = new FileInputStream(file);
            outputStream = response.getOutputStream();

            byte[] buffer = new byte[1024];
            int len;
            while ((len = fis.read(buffer)) != -1) {
                outputStream.write(buffer, 0, len);
            }
            outputStream.flush();
        } catch (IOException e) {
            log.error("图片下载失败：{}", e);
            response.setStatus(500);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
