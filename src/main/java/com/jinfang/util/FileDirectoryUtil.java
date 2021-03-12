package com.jinfang.util;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class FileDirectoryUtil {

    /**
     * 当前日期20190807 文件夹存在则不创建文件夹，不存在则创建并通知下次不用在创建
     */
    private static volatile Map<String, DirMeta> CURRENT_DATE_DIR = new ConcurrentHashMap<>();

    private static final Lock MONITOR = new ReentrantLock();

    public static DirMeta createDir(String home) {
        return createDir(home, "");
    }

    /**
     * 创建目录
     *
     * @param home 基础目录
     * @return 创建结果
     */
    public static DirMeta createDir(String home, String childPath) {
        if (StringUtils.isEmpty(home)) {
            return DirMeta.builder().result(false).msg("home dir is empty").build();
        }

        String date = getDate();

        String dirName = home + date;
        if (StringUtils.isNotEmpty(childPath)) {
            dirName += File.separator + childPath;
        }

        if (CURRENT_DATE_DIR.containsKey(dirName)) {
            return CURRENT_DATE_DIR.get(dirName);
        }

        //如果不存在,创建文件
        File dir = new File(dirName);

        if (dir.exists()) {
            CURRENT_DATE_DIR.put(dirName, DirMeta.builder().result(true).path(dirName).date(date).build());
            return CURRENT_DATE_DIR.get(dirName);
        }

        MONITOR.lock();
        try {
            // 加锁判断，二次判断如果日期路径存在则直接返回
            if (CURRENT_DATE_DIR.containsKey(dirName)) {
                return CURRENT_DATE_DIR.get(dirName);
            }

            boolean isOk = dir.mkdirs();
            if (!isOk) {
                return DirMeta.builder().result(false).msg("dir[" + dirName + "] create failed").build();
            }

            CURRENT_DATE_DIR.put(dirName, DirMeta.builder().result(true).path(dirName).date(date).build());
            return CURRENT_DATE_DIR.get(dirName);
        } finally {
            MONITOR.unlock();
        }

    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            log.error("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            if (file.delete()) {
                log.info("【删除单个文件】==>" + fileName + "成功！");
                return true;
            } else {
                log.error("删除单个文件】==>" + fileName + "失败！");
                return false;
            }
        } else {
            log.error("删除单个文件】==>：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            log.error("【删除文件夹】==>" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
           log.error("【删除文件夹】==>" + dir + "失败！");
            return false;
        }
        // 删除当前目录
        if (dirFile.delete()) {
           log.info("【删除文件夹】==>"+ dir + "成功！");
            return true;
        } else {
            return false;
        }
    }






    public static String getDate() {
        return new SimpleDateFormat("yyyyMMdd").format(new Date());
    }

    @Builder
    @Getter
    public static class DirMeta {

        private boolean result;
        private String msg;
        private String path;
        private String date;

    }

}
