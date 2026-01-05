package com.incloud.hcp.service.impl;

import com.incloud.hcp.bean.CmisFile;
import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.exception.ServiceException;
import com.incloud.hcp.service.CmisService;
import com.incloud.hcp.util.constant.CmisRepositorioConstant;
import com.sap.ecm.api.EcmService;
import org.apache.chemistry.opencmis.client.api.*;
import org.apache.chemistry.opencmis.commons.PropertyIds;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.apache.chemistry.opencmis.commons.enums.VersioningState;
import org.apache.chemistry.opencmis.commons.exceptions.CmisNameConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * Created by Administrador on 26/09/2017.
 */
//@Profile("!devlocal & !dev")
//@Profile("!devlocal")
//@Component("cmisService")
public class CmisServiceImpl implements CmisService, CmisRepositorioConstant {

    private final String LOOKUP_NAME = "java:comp/env/EcmService";
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${sm.portal.dev}")
    private Boolean isDev;

    @Value("${cfg.portal.cmis.name}")
    private String repositoryName;

    @Value("${cfg.portal.cmis.key}")
    private String repositoryKey;


    //@Override
    public Session getSession() {
        try {
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);
            Session session = ecmSvc.connect(repositoryName, repositoryKey);
            return session;
        } catch (NamingException ex) {
            throw new PortalException("Error de conexión al repositorio");
        }
    }

    //@Override
    public CmisFile createDocumento(String folderId, MultipartFile file) {
        try {
            logger.error("createDocumento::001:: " + LOOKUP_NAME);
            logger.error("createDocumento::002:: " + repositoryName);
            logger.error("createDocumento::003:: " + repositoryKey);
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);
            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);

            if (!openCmisSession.exists(folderId)) {
                logger.error("createDocumento::ERROR:: " + folderId);
                throw new PortalException("El folder Id no existe");
            }

            String extension = Optional.ofNullable(file.getOriginalFilename())
                    .map(s -> s.split("\\."))
                    .filter(s -> s.length > 0)
                    .map(s -> "." + s[s.length - 1])
                    .orElse("");
            logger.error("createDocumento::004:: " + extension);
            String fileNewName = file.getOriginalFilename().replace(extension, "") /*+
                    "-" + System.currentTimeMillis() +
                    extension*/;
            logger.error("createDocumento::005:: " + fileNewName);
            //***********************************************
            String original = "ÀÁÂÃÄÅÆÇÈÉÊËÌÍÎÏÐÑÒÓÔÕÖØÙÚÛÜÝßàáâãäåæçèéêëìíîïðñòóôõöøùúûüýÿ";
            // Cadena de caracteres ASCII que reemplazarán los originales.
            String ascii = "AAAAAAACEEEEIIIIDNOOOOOOUUUUYBaaaaaaaceeeeiiiionoooooouuuuyy";
            //String nombreFinal =fileNewName;
            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.
                fileNewName = fileNewName.replace(original.charAt(i), ascii.charAt(i));
            }
            fileNewName = fileNewName.replaceAll("[^a-zA-Z0-9]", "");
            logger.error("createDocumento::006:: " + fileNewName);
            logger.debug("createDocumento: " + extension + " fileName: " + fileNewName);
            logger.error("createDocumento: " + extension + " fileName: " + fileNewName);
            fileNewName = fileNewName +
                    "-" + System.currentTimeMillis() +
                    extension;
            //***********************************************
            logger.error("createDocumento::007:: " + fileNewName);
            Folder folder = (Folder) openCmisSession.getObject(folderId);
            Map<String, Object> properties = new HashMap<String, Object>();
            properties.put(PropertyIds.OBJECT_TYPE_ID, "cmis:document");
            properties.put(PropertyIds.NAME, fileNewName);
            properties.put(PropertyIds.DESCRIPTION, "NO REGISTRADO");
            //properties.put(PropertyIds.SECONDARY_OBJECT_TYPE_IDS, Collections.singletonList("custom:registrado"));
            //properties.put("registrado", Boolean.FALSE);
            logger.error("createDocumento::008:: " + properties.toString());
            InputStream stream = new ByteArrayInputStream(file.getBytes());
            logger.error("createDocumento::009:: ");

            ContentStream contentStream = openCmisSession.getObjectFactory()
                    .createContentStream(fileNewName, file.getSize(), file.getContentType(), stream);
            logger.error("createDocumento::010:: ");
            Document doc = folder.createDocument(properties, contentStream, VersioningState.NONE);
            logger.error("createDocumento::011::");
            CmisFile cmisFile = new CmisFile();
            cmisFile.setId(doc.getId());
            cmisFile.setName(file.getOriginalFilename());
            cmisFile.setType(file.getContentType());
            logger.error("createDocumento::012:: " + cmisFile.toString());
            StringBuilder path = new StringBuilder("/");
            path.append(openCmisSession.getRootFolder().getId());
            path.append("/root/");
            path.append(folder.getName());
            path.append("/");
            path.append(fileNewName);
            cmisFile.setUrl(path.toString());
            logger.error("createDocumento::013:: " + path.toString());
            return cmisFile;
        } catch (NamingException ex) {
            throw new PortalException("Error de conexión al repositorio");
        } catch (CmisNameConstraintViolationException ex) {
            throw new PortalException("Existe un archivo con el mismo nombre");
        } catch (IOException ex) {
            throw new PortalException("Error al leer");
        }
    }

    //@Override
    public String createFolder(String nameFolder) throws PortalException {
        try {
            logger.debug("Creando una carpeta");
            if (nameFolder.trim().isEmpty()) {
                logger.error("El nombre del folder esta vacia");
                throw new PortalException("El nombre del folder esta vacía");
            }

            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);

            Folder root = openCmisSession.getRootFolder();

            ItemIterable<CmisObject> children = root.getChildren();
            for (CmisObject cmisObject : children) {
                if (cmisObject.getName().equals(nameFolder)) {
                    logger.debug("La carpeta " + nameFolder + " existe!");
                    return cmisObject.getId();
                }
            }

            Map<String, String> newFolderProps = new HashMap<>();
            newFolderProps.put(PropertyIds.OBJECT_TYPE_ID, "cmis:folder");
            newFolderProps.put(PropertyIds.NAME, nameFolder);

            Folder newFolder = root.createFolder(newFolderProps);
            logger.debug("Se ha creado un folder en " + newFolder.getPath());
            return newFolder.getId();
        } catch (NamingException ex) {
            logger.error("Error de conexión al repositorio");
            throw new PortalException("Error de conexión al repositorio");
        } catch (CmisNameConstraintViolationException ex) {
            logger.error("El folder ya existe");
            throw new PortalException("El folder ya existe");
        }
    }

    //@Override
    public void deleteFile(String archivoId) {
        try {
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);
            logger.info("Eliminar el archivo " + archivoId);
            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);
            Document doc = (Document) openCmisSession.getObject(archivoId);
            doc.delete(true);
        } catch (Exception ex) {
            logger.error("Error al adjuntar archivo", ex);
            throw new PortalException("Error al eliminar archivo");
        }
    }

    //@Override
    public boolean folderExist(String folderId) {
        try {
            logger.debug("Folder exists : [ " + folderId + " ]");
            if (folderId.trim().isEmpty()) {
                logger.debug("El folder ID esta en blanco");
                return false;
            }
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);
            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);
            return openCmisSession.exists(folderId);
        } catch (NamingException ex) {
            logger.error("Error de conexión al repositorio");
            throw new PortalException("Error de conexión al repositorio");
        }
    }

    //@Override
    public List<CmisFile> getListFileByFolderId(String folderId) {
        try {
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);
            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);

            if (!openCmisSession.exists(folderId)) {
                throw new PortalException("El folder Id no existe");
            }

            Folder folder = (Folder) openCmisSession.getObject(folderId);
            List<CmisFile> list = new ArrayList<>();

            folder.getChildren().forEach(cmiObject -> {
                CmisFile cmisFile = new CmisFile();
                cmisFile.setId(cmiObject.getId());
                cmisFile.setName(cmiObject.getName());
                cmisFile.setUrl(folder.getPath() + "/" + cmiObject.getName());
                Optional.ofNullable(cmiObject.getType()).map(type -> type.getDescription())
                        .ifPresent(cmisFile::setType);
                list.add(cmisFile);
            });

            return list;
        } catch (NamingException ex) {
            throw new PortalException("Error de conexión al repositorio");
        }
    }

    //@Override
    public void updateFileLastVersionTrue(List<String> filesId) {
        if (isDev) {
            return;
        }
        try {
            logger.debug("Actualización de version del archivo");
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);
            Optional.ofNullable(filesId)
                    .ifPresent(l -> l.stream()
                            .peek(id -> logger.debug("Actualizando el archivo id " + id))
                            .filter(openCmisSession::exists)
                            .forEach(id -> {
                                Document doc = (Document) openCmisSession.getObject(id);
                                Map<String, Object> properties = new HashMap<>();
                                properties.put(PropertyIds.DESCRIPTION, "REGISTRADO");
                                doc.updateProperties(properties, true);
                            }));

        } catch (Exception ex) {
            logger.error("Error al actualizar la version de los archivos", ex);
            throw new PortalException("Error al actualizar la vserion de los archivos");
        }
    }

    //@Override
    public List<CmisFile> updateFileAndMove(List<CmisFile> files, String folderId) {
        if (isDev) {
            return null;
        }
        try {
            logger.debug("Actualización de version del archivo");
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);

            List<CmisFile> newFiles = new ArrayList<CmisFile>();
            Optional.ofNullable(files)
                    .ifPresent(l -> l.stream()
                            .peek(item -> logger.debug("Actualizando el archivo id " + item.getId()))
                            .filter(item -> openCmisSession.exists(item.getId()))
                            .forEach(item -> {
                                FileableCmisObject object = (FileableCmisObject) openCmisSession.getObject(item.getId());
                                CmisObject sourceFolderId = object.getParents().get(0);

                                CmisObject targetFolderId = openCmisSession.getObject(folderId);
                                Folder folder = (Folder) openCmisSession.getObject(folderId);

                                CmisObject fileMove = object.move(sourceFolderId, targetFolderId);

                                StringBuilder path = new StringBuilder("/");
                                path.append(openCmisSession.getRootFolder().getId());
                                path.append("/root/");
                                path.append(folder.getName());
                                path.append("/");
                                path.append(fileMove.getName());
                                item.setUrl(path.toString());

                                newFiles.add(item);
                                logger.debug("ARCHIVO MOVIDO !!!: ");
                                Map<String, Object> properties = new HashMap<>();
                                properties.put(PropertyIds.DESCRIPTION, "REGISTRADO");
                                fileMove.updateProperties(properties, true);
                            }));
            return  newFiles;
        } catch (Exception ex) {
            logger.error("Error al actualizar la version de los archivos", ex);
            throw new PortalException("Error al actualizar la version de los archivos");
        }

    }

    //@Override
    public List<CmisFile> updateFileAndMoveVerificar(List<CmisFile> files, String folderId) {
        try {
            logger.error("Actualización de version del archivo");
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);

            List<CmisFile> newFiles = new ArrayList<CmisFile>();
            Optional.ofNullable(files)
                    .ifPresent(l -> l.stream()
                            .peek(item -> logger.error("Actualizando el archivo id " + item.getId()))
                            .filter(item -> openCmisSession.exists(item.getId()))
                            .forEach(item -> {
                                FileableCmisObject object = (FileableCmisObject) openCmisSession.getObject(item.getId());
                                CmisObject sourceFolderId = object.getParents().get(0);
                                CmisObject targetFolderId = openCmisSession.getObject(folderId);
                                Folder folder = (Folder) openCmisSession.getObject(folderId);

                                if (!sourceFolderId.getId().equals(targetFolderId.getId())) {

                                    CmisObject fileMove = object.move(sourceFolderId, targetFolderId);

                                    StringBuilder path = new StringBuilder("/");
                                    path.append(openCmisSession.getRootFolder().getId());
                                    path.append("/root/");
                                    path.append(folder.getName());
                                    path.append("/");
                                    path.append(fileMove.getName());
                                    item.setUrl(path.toString());

                                    newFiles.add(item);
                                    logger.error("ARCHIVO MOVIDO !!!: ");
                                    Map<String, Object> properties = new HashMap<>();
                                    properties.put(PropertyIds.DESCRIPTION, "REGISTRADO");
                                    fileMove.updateProperties(properties, true);
                                }
                            }));
            return newFiles;
        } catch (Exception ex) {
            logger.error("Error al actualizar la version de los archivos", ex);
            throw new ServiceException("Error al actualizar la version de los archivos");
        }

    }

    //@Override
    public void deleteFiles(List<String> archivoIds) {
        try {
            logger.debug("Actualización de version del archivo");
            InitialContext ctx = new InitialContext();
            EcmService ecmSvc = (EcmService) ctx.lookup(LOOKUP_NAME);

            Session openCmisSession = ecmSvc.connect(repositoryName, repositoryKey);
            Optional.ofNullable(archivoIds)
                    .ifPresent(l -> l.stream()
                            .peek(id -> logger.debug("Actualizando el archivo id " + id))
                            .filter(openCmisSession::exists)
                            .forEach(id -> {
                                Document doc = (Document) openCmisSession.getObject(id);
                                doc.delete();
                            }));

        } catch (Exception ex) {
            logger.error("Error al elilminar los archivos", ex);
            throw new PortalException("Error al elilminar los archivos");
        }
    }
}
