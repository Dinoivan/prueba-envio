package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.CmisFile;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.service.CmisService;
import com.incloud.hcp.util.constant.CmisRepositorioConstant;
import org.apache.chemistry.opencmis.client.api.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrador on 26/09/2017.
 */
//@Profile("devlocal | dev")
//@Profile("devlocal")
//@Component("cmisService")
public class CmisServiceFicticioImpl implements CmisService, CmisRepositorioConstant {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /*public Session getSession() {
        return null;
    }

    @Override
    public CmisFile createDocumento(String folderId, MultipartFile file) {
        return null;
    }

    @Override
    public String createFolder(String nameFolder) throws PortalException {
        return null;
    }

    @Override
    public void deleteFile(String archivoId) {
        return;
    }

    @Override
    public boolean folderExist(String folderId) {
        return true;
    }

    @Override
    public List<CmisFile> getListFileByFolderId(String folderId) {
        return null;
    }

    @Override
    public void updateFileLastVersionTrue(List<String> filesId) {
        return;
    }

    @Override
    public List<CmisFile> updateFileAndMove(List<CmisFile> files, String folderId) {
        return null;

    }

    @Override
    public List<CmisFile> updateFileAndMoveVerificar(List<CmisFile> files, String folderId) {
        return null;
    }

    @Override
    public void deleteFiles(List<String> archivoIds) {
        return;
    }*/
}
