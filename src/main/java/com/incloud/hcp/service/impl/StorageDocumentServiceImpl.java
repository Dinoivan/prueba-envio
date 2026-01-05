package com.incloud.hcp.service.impl;

import com.incloud.hcp.domain.StorageDocument;
import com.incloud.hcp.exception.StorageDocumentNotFoundException;
import com.incloud.hcp.exception.StorageException;
import com.incloud.hcp.service.StorageDocumentService;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.sap.core.connectivity.api.configuration.ConnectivityConfiguration;
import com.sap.core.connectivity.api.configuration.DestinationConfiguration;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Session;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.exceptions.CmisObjectNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

@Service
public class StorageDocumentServiceImpl implements StorageDocumentService {
    protected final Logger logger = LoggerFactory.getLogger(StorageDocumentServiceImpl.class);

    @Value("${destination.cmis}")
    protected String destinationCmis;

    @Value("${cfg.folder.ecm}")
    private String folderName;

    private CmisBaseService cmisService;

    @Autowired
    public StorageDocumentServiceImpl(CmisBaseService cmisService) {
        this.cmisService = cmisService;
    }

    @Override
    public StorageDocument getDocumentByPath(String path, boolean includeBase64) {
        this.validatePath(path);
        Session session = null;//cmisService.getSession();
        CmisObject cmisObject;
        String valueURL = "";

        try {
            String newFolder = folderName;
            Integer lenghtPath = path.indexOf(newFolder) - 1;
            String shortPath = path.substring(lenghtPath);
            cmisObject = session.getObjectByPath(shortPath);
        } catch (CmisObjectNotFoundException ex) {
            throw new StorageDocumentNotFoundException("No existe archivo con path " + path, ex.getCause());
        }

        if (cmisObject instanceof Document) {
            ContentStream contentStream = ((Document) cmisObject).getContentStream();
            String fileName = contentStream.getFileName();
            String mimeType = contentStream.getMimeType();
            String length = String.valueOf(contentStream.getLength());
            byte[] bytes = null;

            try {
                Context ctx = new InitialContext();
                ConnectivityConfiguration configuration = (ConnectivityConfiguration) ctx.lookup(
                        "java:comp/env/connectivityConfiguration");
                DestinationConfiguration destConfiguration = configuration.getConfiguration(this.destinationCmis);
                valueURL = destConfiguration.getProperty("URL");
            }
            catch(Exception e) {
                e.printStackTrace();
                logger.error("Connectivity operation failed", e);
            }

            if(includeBase64) {
                try {
                    InputStream stream = contentStream.getStream();
                    bytes = FileCopyUtils.copyToByteArray(stream);
                } catch (IOException e) {
                    throw new StorageException("Ocurrio un error al obtener archivo. " + e.getMessage(), e.getCause());
                }
            }

            StorageDocument storageDocument = new StorageDocument();
            storageDocument.setId(cmisObject.getId());
            storageDocument.setName(fileName);
            storageDocument.setMimeType(mimeType);
            storageDocument.setLength(length);
            if(includeBase64)
                storageDocument.setBase64Content(Base64.getEncoder().encodeToString(bytes));
            storageDocument.setLink(valueURL.concat(path));

            return storageDocument;
        }
        throw new StorageDocumentNotFoundException("No existe documento con path " + path);
    }

    private void validatePath(String path) {
        if (path == null || path.isEmpty()) {
            throw new StorageException("No existe path del archivo o no ha sido creado. ");
        }
    }
}
