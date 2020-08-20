package cl.holapancho.clijv.command;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Component;

import picocli.CommandLine.Command;

@Component
@Command(name = "mycommand", 
    mixinStandardHelpOptions = true)
public class MyCommand implements Callable<Integer> {
    @Override
    public Integer call() {
        System.out.println("Hello in Spanish is Hola!");
        return 23;//magic number!
    }
}