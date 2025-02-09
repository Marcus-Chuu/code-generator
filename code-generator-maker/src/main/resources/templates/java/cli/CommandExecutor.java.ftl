package ${basePackage}.cli;

import ${basePackage}.cli.command.GenerateCommand;
import ${basePackage}.cli.command.ListCommand;
import ${basePackage}.cli.command.ConfigCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
* 命令执行器
*/
@Command(name = "${name}", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable {

    private final CommandLine commandLine;

    {
        commandLine = new CommandLine(this)
            .addSubcommand(new GenerateCommand())
            .addSubcommand(new ConfigCommand())
            .addSubcommand(new ListCommand());
    }

    @Override
    public void run() {
        // 不输入子命令时，给出友好提示
        System.out.println("请输入具体命令，或者输入 --help 查看命令提示");
    }

    /**
    * 执行命令
    *
    * @param args
    * @return
    */
    public Integer doExecute(String[] args) {

        // 将命令行参数转换为UTF-8编码
        String[] utf8Args = Arrays.stream(args)
            .map(arg -> new String(arg.getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8))
            .toArray(String[]::new);

        return commandLine.execute(utf8Args);
    }

}

