package com.jinfang.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

    private static final Logger logger = LoggerFactory.getLogger(ZipUtil.class);

    public static final String ZIP_TYPE = ".zip";


    /**
     * 缓冲器大小
     */
    private static final int BUFFER = 512;

    /**
     * 过滤mac系统压缩可能出现的文件
     */
    private static final String FILTER_NAME = "__MACOSX";

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZipFileMeta {

        private String originFileName;

        private String newFileName;
    }

    /**
     * 解压文件
     *
     * @param inputStream 压缩包
     * @throws Exception 异常抛出
     */
    public static List<byte[]> unzip(InputStream inputStream, String fileSuffix) throws Exception {
        List<byte[]> files = new ArrayList<>();
        ZipInputStream zipInputStream = new ZipInputStream(inputStream, Charset.forName("GBK"));
        try {
            ZipEntry entry = null;
            List<String> fileNames = new ArrayList<>();
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.getName().contains(FILTER_NAME)) {
                    logger.warn("Filter file name [" + entry.getName() + "]");
                    continue;
                }

                if (StringUtils.isNotBlank(fileSuffix) && !entry.getName().toLowerCase().endsWith(fileSuffix.toLowerCase())) {
                    logger.warn("Filter file name [" + entry.getName() + "] by rule [not end with '" + fileSuffix + "']");
                    continue;
                }

                // 解压出来的文件
                byte[] file = toByteArray(zipInputStream);
                zipInputStream.read(file);

                files.add(file);

                zipInputStream.closeEntry();

                fileNames.add(entry.getName());
            }
            logger.info("File[" + fileNames.size() + "] names:" + fileNames + " pickup from zip");

        } catch (Exception e) {
            logger.error("unzip failed", e);
        } finally {
            zipInputStream.close();
            inputStream.close();
        }

        return files;
    }

    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

    /**
     * 压缩方法
     *
     * @param zipFileMetas 文件描述
     * @param zipFileName  目标压缩文件
     * @return 压缩是否成功
     */
    public static boolean zip(List<ZipFileMeta> zipFileMetas, String zipFileName) {
        if (CollectionUtils.isEmpty(zipFileMetas)) {
            logger.warn("zip file files is empty.");
            return false;
        }

        byte[] buffer = new byte[BUFFER];//缓冲器
        ZipEntry zipEntry;
        int readLength;//每次读出来的长度
        try {
            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(zipFileName));
            for (ZipFileMeta zipFileMeta : zipFileMetas) {
                File sourceFile = new File(zipFileMeta.getOriginFileName());
                // 若是文件，则压缩这个文件
                if (sourceFile.isFile()) {
                    String newFileName = sourceFile.getName();
                    if (StringUtils.isNotEmpty(zipFileMeta.getNewFileName())) {
                        newFileName = zipFileMeta.getNewFileName();
                    }

                    zipEntry = new ZipEntry(newFileName);
                    zipEntry.setSize(sourceFile.length());
                    zipEntry.setTime(sourceFile.lastModified());
                    zipOutputStream.putNextEntry(zipEntry);

                    InputStream inputStream = new BufferedInputStream(new FileInputStream(sourceFile));

                    while ((readLength = inputStream.read(buffer, 0, BUFFER)) != -1) {
                        zipOutputStream.write(buffer, 0, readLength);
                    }

                    inputStream.close();
                    logger.info("file compressed: " + sourceFile.getCanonicalPath());
                }
            }    // end for

            zipOutputStream.close();

            return true;
        } catch (Exception e) {
            logger.error("sourceFileNames[{}] zip failed", zipFileMetas, e);
            return false;
        }
    }

}
