package com.jinfang.util;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public final class FileUploadUtil {

    private static final String DEFAULT_IMAGE_EXT_NAME = "jpg";

    private static final char[] N36_CHARS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
            'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};

    /**
     * 网络路径分隔符
     */
    private static final String WEB_URL_SPLIT_CHAR = "/";

    private static String newFileName() {
        SimpleDateFormat f = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return String.format("%s%d", f.format(new Date()), new Random().nextInt(100));
    }

    /**
     * 获取最终重命名后的文件路径
     *
     * @param uploadPath 文件上传路径
     * @param fileName   文件名称
     * @return 最终路径
     */
    public static String getFinalPath(String uploadPath, String fileName, String suffix) {
        return uploadPath + File.separator + fileName + suffix;
    }

    public static String fileName(String fileNamePrefix) {
        return String.format("%s%s", fileNamePrefix, newFileName());
    }

    public static String suffix(String oldFileName) {
        return oldFileName.substring(oldFileName.lastIndexOf("."));
    }

    /**
     * 根据原文件名称提取扩展名
     *
     * @param filename 原文件名称
     * @return 扩展名
     */
    public static String getFileExtName(String filename) {
        if (StringUtils.isBlank(filename)) {
            return DEFAULT_IMAGE_EXT_NAME;
        }

        return filename.substring(filename.lastIndexOf(".")).toLowerCase();
    }

    /**
     * 文件上传
     *
     * @param file       二进制字节数据
     * @param filename   原文件名称
     * @param uploadPath 上传文件目录
     * @return 上传处理结果
     */
    public static FileUploadResult upload(byte[] file, String filename, String uploadPath) {
        try {
            String newFileName = getRandFileName() + getFileExtName(filename);

            FileDirectoryUtil.DirMeta dirMeta = FileDirectoryUtil.createDir(uploadPath);
            if (!dirMeta.isResult()) {
                return FileUploadResult.builder().result(false).msg(dirMeta.getMsg()).build();
            }

            String diskPath = dirMeta.getPath() + File.separator + newFileName;
            File fileName = new File(diskPath);
            if (!fileName.createNewFile()) {
                return FileUploadResult.builder().result(false).msg("创建文件[" + diskPath + "]失败").build();
            }

            Files.write(Paths.get(diskPath), file);

            // This value is not use in this moment
            String urlPathName = dirMeta.getDate() + WEB_URL_SPLIT_CHAR + newFileName;

            return FileUploadResult.builder().filename(newFileName).fileFullName(diskPath).urlPathName(urlPathName)
                    .fileSize(file.length).result(true).msg("处理成功").build();

        } catch (Exception e) {
            return FileUploadResult.builder().result(false).msg("IO处理失败").build();
        }


    }

    private static String getRandFileName() {
        String name = longToN36(System.currentTimeMillis());
        return RandomStringUtils.random(4, N36_CHARS) + name;
    }

    private static String longToN36(long l) {
        return longToNBuf(l).reverse().toString();
    }

    private static StringBuilder longToNBuf(long l) {
        int upgrade = N36_CHARS.length;
        StringBuilder result = new StringBuilder();
        int last;
        while (l > 0) {
            last = (int) (l % upgrade);
            result.append(N36_CHARS[last]);
            l /= upgrade;
        }
        return result;
    }

    @Setter
    @Getter
    @Builder
    public static class FileUploadResult {

        /**
         * 文件名称
         */
        private String filename;

        /**
         * 生成新的文件名称（包含全路径）
         */
        private String fileFullName;

        /**
         * 网络路径名称
         */
        private String urlPathName;

        /**
         * 文件占用硬盘空间大小（字节）
         */
        private long fileSize;

        /**
         * 最终处理结果
         */
        private boolean result;

        /**
         * 返回消息
         */
        private String msg;
    }

}
