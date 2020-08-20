package cl.holapancho.clijv.command;

import java.util.concurrent.Callable;

import com.github.lalyos.jfiglet.FigletFont;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.holapancho.clijv.service.LogicService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "mycommand", 
    mixinStandardHelpOptions = true)
public class MyCommand implements Callable<Integer> {
    @Option(names = "-x", description = "optional option")
    public String x;
    
    @Override
    public Integer call() {
        String asciiArt = FigletFont.convertOneLine("CLI>>");
        System.out.println(asciiArt);
        return 23;//magic number!
    }

    @Autowired
    private LogicService logicService;
}