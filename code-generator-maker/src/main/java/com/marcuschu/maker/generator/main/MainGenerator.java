package com.marcuschu.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.marcuschu.maker.generator.JarGenerator;
import com.marcuschu.maker.generator.ScriptGenerator;
import com.marcuschu.maker.generator.file.DynamicFileGenerator;
import com.marcuschu.maker.meta.Meta;
import com.marcuschu.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator extends GenerateTemplate {


    @Override
    protected void buildDist(String outputPath, String sourceCopyDestPath, String jarPath, String shellOutputFilePath) {
        System.out.println("不要给我输出 dist 啦！");
    }


}
