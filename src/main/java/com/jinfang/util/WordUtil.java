package com.jinfang.util;

import freemarker.template.Configuration;
import freemarker.template.Template;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
@Slf4j
public class WordUtil {  
  
    /** 
     * 生成word文件 
     * @param dataMap word中需要展示的动态数据，用map集合来保存 
     * @param templateName word模板名称，例如：test.ftl 
     * @param filePath 文件生成的目标路径，例如：D:/wordFile/ 
     * @param fileName 生成的文件名称，例如：test.doc 
     */  
    @SuppressWarnings("unchecked")  
    public static void createWord(Map dataMap,String templateName,String filePath,String fileName){  
        try {  
            //创建配置实例  
            Configuration configuration = new Configuration();  
  
            //设置编码  
            configuration.setDefaultEncoding("UTF-8");  
  
            //// 设置ftl模板文件加载方式
            configuration.setClassForTemplateLoading(WordUtil.class, "/wordTemplate");
  
            //获取模板  
            Template template = configuration.getTemplate(templateName);  
  
            //输出文件  
            File outFile = new File(filePath+File.separator+fileName);  
  
            //如果输出目标文件夹不存在，则创建  
            if (!outFile.getParentFile().exists()){  
                outFile.getParentFile().mkdirs();  
            }  
  
            //将模板和数据模型合并生成文件  
            Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile),"UTF-8"));
            //生成文件  
            template.process(dataMap, out);
            //关闭流  
            out.flush();  
            out.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }

    /**
     * 生成zip文件,根据文件路径不带子文件夹(全局可用)
     * @param zipfullPath 压缩后的zip文件全路径
     * @param filePaths 压缩前的文件全路径
     */
    public static void createZip(String zipfullPath, ArrayList<Map<String, String>> filePaths) {
        InputStream inputStream = null;
        ZipOutputStream zip = null;

        try {
            zip = new ZipOutputStream(new FileOutputStream(zipfullPath));
           // zip.setEncoding("gbk");
            for(Map<String, String> fullPath : filePaths) {
                log.info("【createZip:fullPath】：==>" + fullPath);
                if(fullPath==null || fullPath .equals("")) {
                    continue;
                }
                //创建文件
                File file = new File(fullPath.get("filePath"));
                if (!file.exists()){
                    log.error(fullPath+"-----文件没有找到！");
                    continue;
                }
                String fileName = fullPath.get("fileName");
                //String fileName = file.getName();
                //读文件流
                inputStream = new BufferedInputStream(new FileInputStream(file));
                byte[] buffer = new byte[inputStream.available()];
                inputStream.read(buffer);
                inputStream.close();

                //将读取的文件输出到zip中
                zip.putNextEntry(new ZipEntry(fileName));
                zip.write(buffer);
                zip.closeEntry();
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                if (zip != null) {
                    zip.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}  