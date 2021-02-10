package com.jinfang.service;

import com.alibaba.fastjson.JSON;
import com.jinfang.entity.EpPracticeReport;
import com.jinfang.entity.EpPracticeReportDoc;
import com.jinfang.httpdto.Result;
import com.jinfang.mapper.EpPracticeReportDocMapper;
import com.jinfang.util.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileService {

    /**
     * 上传磁盘写入路径
     */
    @Value("${storage.disk}")
    private String storageDisk;


    private final EpPracticeReportDocMapper epPracticeReportDocMapper;
    private final EpPracticeReportService epPracticeReportService;

    public Result upload(MultipartFile multipartFile, Long adviserStudentId, Long id) {
        if (multipartFile == null) {
            return Result.error("上传文件数据为空");
        }

        try {
            FileUploadUtil.FileUploadResult fileUploadResult = FileUploadUtil.upload(multipartFile.getBytes(),
                    multipartFile.getOriginalFilename(), storageDisk);

            if (!fileUploadResult.isResult()) {
                return Result.error(fileUploadResult.getMsg());
            }


            return saveFiled(multipartFile.getOriginalFilename(), fileUploadResult, adviserStudentId, id);
        } catch (Exception e) {
            log.error("multipartFile[{}]. type[{}] upload failed", multipartFile.getOriginalFilename(), e);
            return Result.error("文件上传失败");
        }

    }

    /*
     * @Description:将上传文件信息保存到EpPracticeReportDoc表中
     * @Author: Gjm
     * @Date: 2021/2/4 4:20 下午
     * @return: java.lang.Long
     **/
    private Result saveFiled(String originName, FileUploadUtil.FileUploadResult fileUploadResult, Long adviserStudentId, Long id) {
        EpPracticeReportDoc docMeta = EpPracticeReportDoc.builder().originName(originName).newName(fileUploadResult.getFilename())
                .size(fileUploadResult.getFileSize()).path(fileUploadResult
                        .getFileFullName()).url(fileUploadResult.getUrlPathName())
                .createDate(new Date()).build();

        int result = epPracticeReportDocMapper.save(docMeta);
        if (result <= 0) {
            throw new RuntimeException("file[" + fileUploadResult.getFileFullName() + "] insert failed");
        }
        //说明是重新提交的实习报告，把原实习报告作为历史记录
        if (id != null && id != 0) {
            EpPracticeReport practiceReport = new EpPracticeReport();
            practiceReport.setId(id);
            practiceReport.setIsHistory(1);
            int update = epPracticeReportService.update(practiceReport);
        }
        EpPracticeReport epPracticeReport = new EpPracticeReport();
        epPracticeReport.setState(0);
        epPracticeReport.setFileId(docMeta.getId());
        epPracticeReport.setAdviserStudentId(adviserStudentId);
        epPracticeReport.setCreateDate(new Date());
        epPracticeReport.setIsHistory(0);
        epPracticeReport.setIsDel(0);
        int save = epPracticeReportService.save(epPracticeReport);
        if (save > 0) {
            return Result.ok();
        }
        return Result.error("上传失败");
    }

    /**
     * 根据文件ID获取文件 预览路径，需要提前配置好nginx url映射
     *
     * @param id 文件ID
     * @return 预览URL
     */
    public Result getFilePreviewUrl(Long id) {
        if (id == null || id == 0) {
            log.warn("Args[id] is necessary");

            return Result.error("数据资源无效");
        }
        EpPracticeReportDoc reportDoc = epPracticeReportDocMapper.findById(id);

        if (reportDoc == null) {
            log.warn("没有找到文件", id);
            return Result.error("数据资源无效");
        }

        if (StringUtils.isEmpty(reportDoc.getUrl())) {
            log.error("Url is null by id[{}]", id);
            return Result.error("数据资源无效");
        }

//        String url = DecodeUtil.encode(storageDomain + gpDocMeta.getUrl());
        String url = storageDisk + reportDoc.getUrl();
        log.info("Preview file[{}] url -> {}", id, url);

        return Result.ok(url);
    }


    /**
     * 获取携带日期格式路径
     *
     * @param dir 原路径
     * @return 原路径追加日期后的路径
     */
    private FileDirectoryUtil.DirMeta getDirWithDate(String dir) {
        return FileDirectoryUtil.createDir(dir);
    }

    private String getDateTimeFileName(int size) {
        return new SimpleDateFormat("yyyyMMddHHmmSS").format(new Date()) + "-" + size
                + "-" + RandomUtil.randomCode();
    }


}
