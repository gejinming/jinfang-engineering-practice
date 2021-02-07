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
