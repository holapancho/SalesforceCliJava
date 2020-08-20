package cl.holapancho.clijv.command;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

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
        System.out.println("Hello in Spanish is Hola!");
        return 23;//magic number!
    }
}