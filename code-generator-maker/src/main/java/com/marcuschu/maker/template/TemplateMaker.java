package com.marcuschu.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.marcuschu.maker.meta.Meta;
import com.marcuschu.maker.meta.enums.FileGenerateTypeEnum;
import com.marcuschu.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class TemplateMaker {

    public static void main(String[] args) {

        // 一、输入信息
        // 1. 输入项目基本信息
        String name = "acm-template-generator";
        String description = "ACM 示例模板生成器";

        // 2. 输入文件信息
        String projectPath = System.getProperty("user.dir");
        String sourceRootPath = new File(projectPath).getParent() + File.separator + "code-generator-demo-projects/acm-template";
        // 注意 win 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");

        String fileInputPath = "src/com/yupi/acm/MainTemplate.java";
        String fileOutputPath = fileInputPath + ".ftl";

        // 3. 输入模型参数信息
        Meta.ModelConfigDTO.ModelsDTO modelInfo = new Meta.ModelConfigDTO.ModelsDTO();
        modelInfo.setFieldName("outputText");
        modelInfo.setType("String");
        modelInfo.setDefaultValue("sum = ");




        // 二、使用字符串替换，生成模板文件
        String fileInputAbsolutePath = sourceRootPath + File.separator + fileInputPath;
        String fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        String replacement = String.format("${%s}", modelInfo.getFieldName());
        String newFileContent = StrUtil.replace(fileContent, "Sum: ", replacement);

        // 输出模板文件
        String fileOutputAbsolutePath = sourceRootPath + File.separator + fileOutputPath;
        FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);




        // 三、生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";

        // 1. 构造配置参数
        Meta meta = new Meta();
        meta.setName(name);
        meta.setDescription(description);

        Meta.FileConfigDTO fileConfig = new Meta.FileConfigDTO();
        meta.setFileConfig(fileConfig);
        fileConfig.setSourceRootPath(sourceRootPath);
        List<Meta.FileConfigDTO.FilesDTO> fileInfoList = new ArrayList<>();
        fileConfig.setFiles(fileInfoList);
        Meta.FileConfigDTO.FilesDTO fileInfo = new Meta.FileConfigDTO.FilesDTO();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
        fileInfoList.add(fileInfo);

        Meta.ModelConfigDTO modelConfig = new Meta.ModelConfigDTO();
        meta.setModelConfig(modelConfig);
        List<Meta.ModelConfigDTO.ModelsDTO> modelInfoList = new ArrayList<>();
        modelConfig.setModels(modelInfoList);
        modelInfoList.add(modelInfo);

        // 2. 输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(meta), metaOutputPath);

    }
}
