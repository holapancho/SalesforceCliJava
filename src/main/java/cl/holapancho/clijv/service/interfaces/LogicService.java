package cl.holapancho.clijv.service.interfaces;

import java.io.File;

import com.sforce.soap.partner.PartnerConnection;

import cl.holapancho.clijv.dto.Config;
import cl.holapancho.clijv.exception.LogicServiceException;

public interface LogicService {
    public Config readConfig(File file) throws LogicServiceException;
    public PartnerConnection getConnectorConfig(Config config) throws LogicServiceException;
}