package com.jinfang.controller.system;

import com.jinfang.controller.BaseController;
import com.jinfang.entity.EpPracticeReportDoc;
import com.jinfang.httpdto.Result;
import com.jinfang.httpdto.ResultEnum;
import com.jinfang.mapper.EpPracticeReportDocMapper;
import com.jinfang.service.FileService;
import com.jinfang.util.FileDownloadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/file")
@Slf4j
@Api(tags = "文件操作接口")
public class FileController extends BaseController {
    @Autowired
    private FileService fileService;
    @Autowired
    private EpPracticeReportDocMapper epPracticeReportDocMapper;

    @PostMapping(value = "/upload")
    @ApiOperation(value = "上传文件", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name="file",value = "文件"),
            @ApiImplicitParam(name="adviserStudentId",value = "指导老师分配学生ID"),
    })
    public Result upload(@RequestParam("file") MultipartFile file,Long adviserStudentId) {
        if (file==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"实习报告文件未获取到，请检查!");
        }
        if (adviserStudentId==null){
            return Result.error(ResultEnum.PARAM_ERROR.getCode(),"指导老师分配学生ID未获取到，请检查!");
        }
        return fileService.upload(file,adviserStudentId);
    }

   /* @PostMapping(value = "/uploadWithType")
    @ApiOperation(value = "根据文件名称上传", httpMethod = "POST")
    public HttpResult uploadWithName(@RequestParam("file") MultipartFile file,
                                     @RequestParam("type") String type) {
        return fileService.upload(file, type);
    }
*/
    @GetMapping(value = "/preview")
    @ApiOperation(value = "获取预览实习报告文件URL", httpMethod = "GET")
    @ApiImplicitParam(name="fileId",value = "实习报告文件ID")
    public Result preview(Long fileId) {
        return fileService.getFilePreviewUrl(fileId);
    }

    @ApiOperation("下载实习报告")
    @ApiImplicitParam(name = "fileId",value = "实习报告文件ID")
    @GetMapping("/downloadReport")
    public void download(Long fileId, HttpServletResponse response) throws UnsupportedEncodingException {
        if (fileId==null){
            log.error("fileId为空。");
        }
        EpPracticeReportDoc reportDoc = epPracticeReportDocMapper.findById(fileId);
        String path = reportDoc.getPath();
        if (path==null){
            log.error("fileId"+fileId+"：文件路径为空");
        }
        String originName = reportDoc.getOriginName();
        File file = new File(path);
        if (!file.exists()){
            log.error("文件没有找到！");
            Result.error("文件没有找到");
        }
        FileDownloadUtil.downLoadfile(path,originName,response);
    }

   /* @GetMapping(value = "/download")
    @ApiOperation(value = "下载文件", httpMethod = "GET")
    public ResponseEntity<InputStreamResource> download(@RequestParam("ids") List<String> ids) {
        return fileService.download(ids);
    }*/

}
