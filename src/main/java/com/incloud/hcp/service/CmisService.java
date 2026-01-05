package com.incloud.hcp.service;

import com.incloud.hcp.bean.CmisFile;
import org.apache.chemistry.opencmis.client.api.Session;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrador on 26/09/2017.
 */
public interface CmisService {

    /*Session getSession();

    CmisFile createDocumento(String folderId, MultipartFile file);

    String createFolder(String folder);

    boolean folderExist(String folderId);

    void deleteFile(String archivoId);
    void deleteFiles(List<String> archivoIds);

    List<CmisFile> getListFileByFolderId(String folderId);

    void updateFileLastVersionTrue(List<String> archivoIds);

    List<CmisFile> updateFileAndMove(List<CmisFile> archivos, String targetFolderId);

    List<CmisFile> updateFileAndMoveVerificar(List<CmisFile> files, String folderId) ;*/
}
