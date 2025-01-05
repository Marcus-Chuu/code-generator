package com.marcuschu.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.marcuschu.model.MainTemplateConfig;
import picocli.CommandLine;

import java.lang.reflect.Field;

@CommandLine.Command(name = "config", description = "查看参数信息", mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable {


    @Override
    public void run() {

        // 实现 config 命令的逻辑
        System.out.println("查看参数信息");

        // 1. JDK 原生反射语法
        // 获取要打印属性信息的类
        Class<?> clazz = MainTemplateConfig.class;
        // 获取类中的所有属性
        Field[] fields = clazz.getDeclaredFields();

        // 2. Hutool 反射语法
        Field[] fields1 = ReflectUtil.getFields(MainTemplateConfig.class);

        // 遍历属性并打印
        for (Field field : fields) {
            System.out.println("字段名称: " + field.getName());
            System.out.println("字段类型: " + field.getType());
            System.out.println("---");
        }
    }
}
