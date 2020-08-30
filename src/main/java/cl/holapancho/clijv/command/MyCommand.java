package cl.holapancho.clijv.command;

import java.io.File;
import java.util.concurrent.Callable;
import com.github.lalyos.jfiglet.FigletFont;
import com.sforce.soap.partner.PartnerConnection;
import com.sforce.soap.partner.QueryResult;
import com.sforce.soap.partner.sobject.SObject;
import com.sforce.ws.ConnectionException;

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

            //
            try {
                // Set query batch size
                connectorConfig.setQueryOptions(250);
                
                // SOQL query to use 
                String soqlQuery = "SELECT FirstName, LastName FROM Contact";
                // Make the query call and get the query results
                QueryResult qr = connectorConfig.query(soqlQuery);
                
                boolean done = false;
                int loopCount = 0;
                // Loop through the batches of returned results
                while (!done) {
                    System.out.println("Records in results set " + loopCount++
                            + " - ");
                    SObject[] records = qr.getRecords();
                    // Process the query results
                    for (int i = 0; i < records.length; i++) {
                        SObject contact = records[i];
                        Object firstName = contact.getField("FirstName");
                        Object lastName = contact.getField("LastName");
                        if (firstName == null) {
                            System.out.println("Contact " + (i + 1) + 
                                    ": " + lastName
                            );
                        } else {
                            System.out.println("Contact " + (i + 1) + ": " + 
                                    firstName + " " + lastName);
                        }
                    }
                    if (qr.isDone()) {
                        done = true;
                    } else {
                        qr = connectorConfig.queryMore(qr.getQueryLocator());
                    }
                }
            } catch(ConnectionException ce) {
                ce.printStackTrace();
            }
            System.out.println("\nQuery execution completed.");
        }
        catch(LogicServiceException e){
            System.out.println(e.getMessage());
        }

        return 23;// magic number!
    }

    @Autowired
    private LogicService logicService;
}