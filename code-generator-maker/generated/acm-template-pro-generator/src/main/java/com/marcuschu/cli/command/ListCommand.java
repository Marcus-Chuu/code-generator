package com.marcuschu.cli.command;

import cn.hutool.core.io.FileUtil;
import picocli.CommandLine.Command;

import java.io.File;
import java.util.List;

@Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {

    public void run() {

        // 输入路径
        String inputPath = "C:\\01 - 学习资料\\02 - 项目\\星球项目\\05 - 代码生成器\\code-generator\\code-generator-demo-projects\\acm-template-pro";
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }
    }
}