package cl.holapancho.clijv.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.Callable;
import com.github.lalyos.jfiglet.FigletFont;
 
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import cl.holapancho.clijv.service.LogicService;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;
import org.json.simple.JSONObject;

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

        String user;
        String password;
        String token;

        if (archive != null) {
            // JSON parser object to parse read file
            JSONParser jsonParser = new JSONParser();
            Object obj = null;
            try (FileReader reader = new FileReader(archive)) {
                // Read JSON file
                obj = jsonParser.parse(reader);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
            System.out.println(obj);
            JSONObject jsonObject = (JSONObject)obj;
            user = (String) jsonObject.get("user");
            password = (String)jsonObject.get("password");
            token = (String)jsonObject.get("token");

            ConnectorConfig connectorConfig = new ConnectorConfig();
            connectorConfig.setUsername(user);
            connectorConfig.setAuthEndpoint("https://test.salesforce.com/services/Soap/u/40.0");
            connectorConfig.setPassword(password + token);
            PartnerConnection partnerConnection = null;
            try {
                partnerConnection = Connector.newConnection(connectorConfig);
                System.out.println(partnerConnection);
            } catch (Exception e) {
                System.out.println("me cai");

            }
        }

        return 23;// magic number!
    }

    @Autowired
    private LogicService logicService;
}