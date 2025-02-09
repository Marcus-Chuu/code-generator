package com.marcuschu.generator;

import com.marcuschu.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * 核心生成器
 */
public class MainGenerator {


    public static void main(String[] args) throws TemplateException, IOException {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("yupi");
        mainTemplateConfig.setLoop(false);
        mainTemplateConfig.setOutputText("求和结果：");
        doGenerate(mainTemplateConfig);
    }


//    /**
//     * 生成
//     *
//     * @param model 数据模型
//     * @throws TemplateException
//     * @throws IOException
//     */
//    public static void doGenerate(Object model) throws TemplateException, IOException {
//        String projectPath = System.getProperty("user.dir");
//        // 整个项目的根路径
//        File parentFile = new File(projectPath).getParentFile();
//        // 输入路径
//        String inputPath = new File(parentFile, "code-generator-demo-projects/acm-template").getAbsolutePath();
//        String outputPath = projectPath;
//
//        System.out.println("================" + inputPath + "================");
//        System.out.println("================" + outputPath + "================");
//
//        // 生成静态文件
//        com.yupi.generator.StaticGenerator.copyFilesByRecursive(inputPath, outputPath);
//        // 生成动态文件
//        String inputDynamicFilePath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
//        String outputDynamicFilePath = outputPath + File.separator + "acm-template/src/com/yupi/acm/MainTemplate.java";
//
//        System.out.println("================" + inputDynamicFilePath + "================");
//        System.out.println("================" + outputDynamicFilePath + "================");
//
//        DynamicGenerator.doGenerate(inputDynamicFilePath, outputDynamicFilePath, model);
//    }


    /**
     * 生成
     *
     * @param model 数据模型
     * @throws TemplateException
     * @throws IOException
     */
    public static void doGenerate(Object model) throws TemplateException, IOException {

        String inputRootPath = "C:\\01 - 学习资料\\02 - 项目\\星球项目\\05 - 代码生成器\\code-generator\\code-generator-demo-projects\\acm-template-pro";
        String outputRootPath = "C:\\01 - 学习资料\\02 - 项目\\星球项目\\05 - 代码生成器\\code-generator\\acm-template-pro";

        String inputPath;
        String outputPath;

        inputPath = new File(inputRootPath, "src/com/yupi/acm/MainTemplate.java.ftl").getAbsolutePath();
        outputPath = new File(outputRootPath, "src/com/yupi/acm/MainTemplate.java").getAbsolutePath();
        DynamicGenerator.doGenerate(inputPath, outputPath, model);

        inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);

        inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
        outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
    }

}

