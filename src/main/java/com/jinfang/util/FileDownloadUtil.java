package com.jinfang.util;

import com.jinfang.httpdto.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@Slf4j
public class FileDownloadUtil {

    private static final String separator = File.separator;

    /**
     * 下载样表
     * @param fileFullName 文件全名（包含全路径）
     * @param newName  下载的展示文件名
     * @return 响应
     */
    public static ResponseEntity<InputStreamResource> download(String fileFullName, String newName) {
        ResponseEntity<InputStreamResource> response = null;
        try {
            InputStream inputStream = new FileInputStream(fileFullName);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
            headers.add("Content-Disposition",
                    "attachment; filename=" + new String(newName.getBytes("gbk"), "iso8859-1"));
            headers.add("Pragma", "no-cache");
            headers.add("Expires", "0");

            response = ResponseEntity.ok().headers(headers)
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(inputStream));
        } catch (FileNotFoundException e1) {
            log.error("找不到指定的文件", e1);
        } catch (IOException e) {
            log.error("获取不到文件流", e);
        }

        return response;
    }

    public static void downLoadfile( String fileUrl,String fileName,HttpServletResponse response) throws UnsupportedEncodingException {
        File file = new File(fileUrl);
        //原始文件名
        if (file.exists()) { //判断文件是否存在
            // 配置文件下载
            response.setContentType("application/vnd.ms-excel;charset=UTF-8");
            response.setCharacterEncoding("UTF-8");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null; //文件输入流
            BufferedInputStream bis = null;
            OutputStream os = null; //输出流
            try {
                os = response.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
                bis.close();
                fis.close();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                log.error("数据流错误",e.getMessage());
                e.printStackTrace();

            }
            log.info("----------file download success---" + fileUrl);
        }

    }
}
