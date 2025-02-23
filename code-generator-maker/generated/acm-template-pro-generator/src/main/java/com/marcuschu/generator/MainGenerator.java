package com.marcuschu.generator;

import com.marcuschu.model.DataModel;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;


/**
* 核心生成器
*/
public class MainGenerator {

/**
* 生成
*
* @param model 数据模型
* @throws TemplateException
* @throws IOException
*/
public static void doGenerate(DataModel model) throws TemplateException, IOException {

    String inputRootPath = ".source/acm-template-pro";
    String outputRootPath = "C:/01 - 学习资料/02 - 项目/星球项目/05 - 代码生成器/code-generator/acm-template-pro";

    String inputPath;
    String outputPath;

    boolean needGit = model.needGit;
    boolean loop = model.loop;
    String author = model.mainTemplate.author;
    String outputText = model.mainTemplate.outputText;

    inputPath = new File(inputRootPath, "src/com/yupi/acm/MainTemplate.java.ftl").getAbsolutePath();
    outputPath = new File(outputRootPath, "src/com/yupi/acm/MainTemplate.java").getAbsolutePath();
    DynamicGenerator.doGenerate(inputPath, outputPath, model);
    if (needGit) {
        inputPath = new File(inputRootPath, ".gitignore").getAbsolutePath();
        outputPath = new File(outputRootPath, ".gitignore").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
                }
        inputPath = new File(inputRootPath, "README.md").getAbsolutePath();
        outputPath = new File(outputRootPath, "README.md").getAbsolutePath();
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
    }
}