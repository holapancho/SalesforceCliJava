package cl.holapancho.clijv.command;

import java.io.File;
import java.util.concurrent.Callable;
import com.github.lalyos.jfiglet.FigletFont;
import com.sforce.soap.partner.PartnerConnection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cl.holapancho.clijv.dto.Config;
import cl.holapancho.clijv.exception.LogicServiceException;
import cl.holapancho.clijv.service.interfaces.LogicService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Component
@Command(name = "mycommand", mixinStandardHelpOptions = true)
public class MyCommand implements Callable<Integer> {
    @Option(names = "-x", description = "optional option")
    public String x;

    @Option(names = { "-f", "--file" }, paramLabel = "ARCHIVE", description = "the archive file")
    public File archive;

    @Override
    public Integer call() {
        if (x == null) {
            x = "AHOLA";
        }
        String asciiArt = FigletFont.convertOneLine(x);
        System.out.println(asciiArt);

        try{
            System.out.println("reading config file");
            Config config = logicService.readConfig(archive);
            System.out.println("reading config file DONE");
    
            System.out.println("get SF config");
            PartnerConnection connectorConfig = logicService.getConnectorConfig(config);
            System.out.println("get SF config DONE");
        }
        catch(LogicServiceException e){
            System.out.println(e.getMessage());
        }

        return 23;// magic number!
    }

    @Autowired
    private LogicService logicService;
}