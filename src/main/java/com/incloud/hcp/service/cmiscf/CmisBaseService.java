package com.incloud.hcp.service.cmiscf;

import com.incloud.hcp.bean.FileBase64;
import com.incloud.hcp.service.cmiscf.bean.CmisFile;
import com.incloud.hcp.service.cmiscf.bean.CmisFolder;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Repository;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Administrador on 26/09/2017.
 */
public interface CmisBaseService {

    String getToken() throws Exception;

    List<Repository> getListaRepositoryToken() throws Exception;

    CmisFolder createFolder(String nameFolder) throws Exception;

    CmisFile createDocumento(String nameFolder, MultipartFile file) throws Exception;

    CmisFile createDocumentoByBase64(String folderName, FileBase64 fileBase64) throws Exception;

    ByteArrayOutputStream getDocumento(String archivoId) throws Exception;

    CmisFile createDocumentoFromBytes(String nameFolder, String extension, String fileNewName, String contentType, ByteArrayOutputStream file) throws Exception;

    List<CmisFile> updateFileAndMoveVerificar(List<CmisFile> files, String nameFolder);

    void deleteFiles(List<String> archivoIds, String nameFolder);

    void updateFileLastVersionTrue(List<String> filesId, String nameFolder);

    CmisObject getDocumentByFolderAndId(String nameFolder, String archivoId);
    CmisObject getDocumentByFolderAndPath(String nameFolder, String archivoId);

    CmisObject getFolder(String nameFolder);

    List<CmisFile> getListFileByFolderId(String nameFolder);



}
