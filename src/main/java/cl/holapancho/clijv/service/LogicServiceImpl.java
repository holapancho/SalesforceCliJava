package cl.holapancho.clijv.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cl.holapancho.clijv.dto.Config;
import cl.holapancho.clijv.exception.LogicServiceException;
import cl.holapancho.clijv.service.interfaces.LogicService;
import cl.holapancho.clijv.util.StringUtil;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Component;

import com.sforce.ws.ConnectorConfig;
import com.sforce.soap.partner.Connector;
import com.sforce.soap.partner.PartnerConnection;

@Component
public class LogicServiceImpl implements LogicService{

    private static final String USER_FIELD = "user";
    private static final String PASSWORD_FIELD = "password";
    private static final String TOKEN_FIELD = "token";
    private static final String SANDBOX_FIELD = "isSandbox";

    @Override
    public Config readConfig(File file) throws LogicServiceException {
        if (file == null) {
            throw new LogicServiceException("null file");
        }

        // JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();
        Object obj = null;
        try (FileReader reader = new FileReader(file)) {
            // Read JSON file
            obj = jsonParser.parse(reader);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new LogicServiceException(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            throw new LogicServiceException(e.getMessage());
        } catch (ParseException e) {
            e.printStackTrace();
            throw new LogicServiceException(e.getMessage());
        }
        JSONObject jsonObject = (JSONObject)obj;
        String user = (String) jsonObject.get(USER_FIELD);
        String password = (String)jsonObject.get(PASSWORD_FIELD);
        String token = (String)jsonObject.get(TOKEN_FIELD);
        Boolean isSandbox = (Boolean)jsonObject.get(SANDBOX_FIELD);

        if (!StringUtil.isNotBlank(user)){
            throw new LogicServiceException("empty user");
        }
        if (!StringUtil.isNotBlank(password)){
            throw new LogicServiceException("empty password");
        }
        if (!StringUtil.isNotBlank(token)){
            throw new LogicServiceException("empty token");
        }
        if(isSandbox == null){
            throw new LogicServiceException("empty isSandbox");
        }

        return new Config(user,password,token,isSandbox);
    }

    @Override
    public PartnerConnection getConnectorConfig(Config config) throws LogicServiceException{
        ConnectorConfig connectorConfig = new ConnectorConfig();
        connectorConfig.setUsername(config.getUser());
        if(config.getIsSandbox()){
            connectorConfig.setAuthEndpoint("https://test.salesforce.com/services/Soap/u/40.0");
        }
        else{
            connectorConfig.setAuthEndpoint("https://salesforce.com/services/Soap/u/40.0");
        }
        connectorConfig.setPassword(config.getPasswordAndToken());
        try {
            return Connector.newConnection(connectorConfig);
        } catch (Exception e) {
            e.printStackTrace();
            throw new LogicServiceException(e.getMessage());
        }
    }
}