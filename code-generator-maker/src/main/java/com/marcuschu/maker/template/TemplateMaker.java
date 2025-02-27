package com.marcuschu.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.marcuschu.maker.meta.Meta;
import com.marcuschu.maker.meta.enums.FileGenerateTypeEnum;
import com.marcuschu.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class TemplateMaker {






    public static void main(String[] args) {

        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = new File(projectPath).getParent() + File.separator + "code-generator-demo-projects/acm-template";
        String inputFilePath = "src/com/yupi/acm/MainTemplate.java";


        // 模型参数信息（首次）
//        Meta.ModelConfigDTO.ModelsDTO modelInfo = new Meta.ModelConfigDTO.ModelsDTO();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("sum = ");

        // 模型参数信息（第二次）
        Meta.ModelConfigDTO.ModelsDTO modelInfo = new Meta.ModelConfigDTO.ModelsDTO();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");

        // 替换变量（首次）
//        String searchStr = "Sum: ";
        // 替换变量（第二次）
        String searchStr = "MainTemplate";

        long id = makeTemplate(meta, originProjectPath, inputFilePath, modelInfo, searchStr, 1895129195615174656L);
        System.out.println(id);
    }




    /**
     * 制作模板
     *
     * @param newMeta
     * @param originProjectPath
     * @param inputFilePath
     * @param modelInfo
     * @param searchStr
     * @param id
     * @return id
     */
    public static long makeTemplate(Meta newMeta, String originProjectPath, String inputFilePath, Meta.ModelConfigDTO.ModelsDTO modelInfo, String searchStr, Long id) {

        // 没有 id 则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }

        // 复制目录
        String projectPath = System.getProperty("user.dir");
        String tempDirPath = projectPath + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;

        // 是否为首次制作模板
        // 目录不存在，则是首次制作
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }

        // 一、输入信息
        // 输入文件信息
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        // 注意 win 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");

        String fileInputPath = inputFilePath;
        String fileOutputPath = fileInputPath + ".ftl";

        // 二、使用字符串替换，生成模板文件
        String fileInputAbsolutePath = sourceRootPath + File.separator + fileInputPath;
        String fileOutputAbsolutePath = sourceRootPath + File.separator + fileOutputPath;

        String fileContent;
        // 如果已有模板文件，说明不是第一次制作，则在模板基础上再次挖坑
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        String replacement = String.format("${%s}", modelInfo.getFieldName());
        String newFileContent = StrUtil.replace(fileContent, searchStr, replacement);

        // 输出模板文件
        FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);

        // 文件配置信息
        Meta.FileConfigDTO.FilesDTO fileInfo = new Meta.FileConfigDTO.FilesDTO();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        // 三、生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";

        // 如果已有 meta 文件，说明不是第一次制作，则在 meta 基础上进行修改
        if (FileUtil.exist(metaOutputPath)) {

            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;

            // 1. 追加配置参数
            List<Meta.FileConfigDTO.FilesDTO> fileInfoList = newMeta.getFileConfig().getFiles();
            fileInfoList.add(fileInfo);
            List<Meta.ModelConfigDTO.ModelsDTO> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

            // 配置去重
            newMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            newMeta.getModelConfig().setModels(distinctModels(modelInfoList));

        } else {

            // 1. 构造配置参数
            Meta.FileConfigDTO fileConfig = new Meta.FileConfigDTO();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfigDTO.FilesDTO> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.add(fileInfo);

            Meta.ModelConfigDTO modelConfig = new Meta.ModelConfigDTO();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfigDTO.ModelsDTO> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);
        }

        // 2. 输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        return id;
    }




    /**
     * 文件去重
     *
     * @param fileInfoList
     * @return
     */
    private static List<Meta.FileConfigDTO.FilesDTO> distinctFiles(List<Meta.FileConfigDTO.FilesDTO> fileInfoList) {
        List<Meta.FileConfigDTO.FilesDTO> newFileInfoList = new ArrayList<>(
                fileInfoList.stream()
                        .collect(
                                Collectors.toMap(Meta.FileConfigDTO.FilesDTO::getInputPath, o -> o, (e, r) -> r)
                        ).values()
        );
        return newFileInfoList;
    }




    /**
     * 模型去重
     *
     * @param modelInfoList
     * @return
     */
    private static List<Meta.ModelConfigDTO.ModelsDTO> distinctModels(List<Meta.ModelConfigDTO.ModelsDTO> modelInfoList) {
        List<Meta.ModelConfigDTO.ModelsDTO> newModelInfoList = new ArrayList<>(
                modelInfoList.stream()
                        .collect(
                                Collectors.toMap(Meta.ModelConfigDTO.ModelsDTO::getFieldName, o -> o, (e, r) -> r)
                        ).values()
        );
        return newModelInfoList;
    }


}
