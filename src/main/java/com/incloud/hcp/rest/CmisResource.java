package com.incloud.hcp.rest;

import com.incloud.hcp.exception.PortalException;
import com.incloud.hcp.service.cmiscf.CmisBaseService;
import com.incloud.hcp.service.cmiscf.bean.CmisBean;
import com.incloud.hcp.service.notificacion.ActualizacionDeDatosNotificacion;
import com.incloud.hcp.util.Utils;
import org.apache.chemistry.opencmis.client.api.CmisObject;
import org.apache.chemistry.opencmis.client.api.Document;
import org.apache.chemistry.opencmis.client.api.Folder;
import org.apache.chemistry.opencmis.client.api.ItemIterable;
import org.apache.chemistry.opencmis.commons.data.ContentStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/cmis-resource")
public class CmisResource {

    @Autowired
    private CmisBaseService cmisBaseServicecf;

    @Autowired
    private ActualizacionDeDatosNotificacion actualizacionDeDatosNotificacion;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/document")
    public ByteArrayResource getDocument(@RequestParam(value = "archivoId") String archivoID,
                                                @RequestParam(value = "nameFolder") String nameFolder) throws IOException {

        if (archivoID.equals("")) {
            throw new PortalException("No se ha encontrado archivo...");
        }
        CmisObject objectByPath = cmisBaseServicecf.getDocumentByFolderAndId(nameFolder, archivoID);

        if (objectByPath instanceof Document) {
            ContentStream contentStream = ((Document) objectByPath).getContentStream();
            String fileName = contentStream.getFileName();
            String mimeType = contentStream.getMimeType();

            InputStream stream = contentStream.getStream();

            byte[] bytes = FileCopyUtils.copyToByteArray(stream);
            logger.error("Archivo encontrado InputStream "+archivoID+"--**> "+bytes);
            ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
            logger.error("Archivo encontrado InputStream ");
            return byteArrayResource;//String.valueOf(byteArrayResource);
            /*long length = bytes.length;

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
            httpHeaders.add("Pragma", "no-cache");
            httpHeaders.add("Expires", "0");
            httpHeaders.setContentLength(length);
            httpHeaders.setContentDispositionFormData("attachment", fileName);*/

            //logger.error("File metadata. fileName: {}, length: {}, mimeType: {}", fileName, length, mimeType);

            /*return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .contentLength(length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(byteArrayResource);*/
//            return byteArrayResource;
        }

        throw new PortalException("No se ha encontrado archivo...");

    }

    @GetMapping("/documentForPath")
    public byte[] getDocumentForPath(@RequestParam(value = "archivoId") String archivoID,
                                         @RequestParam(value = "nameFolder") String nameFolder) throws IOException {

        if (archivoID.equals("")) {
            throw new PortalException("No se ha encontrado archivo...");
        }
        CmisObject objectByPath = cmisBaseServicecf.getDocumentByFolderAndPath(nameFolder, archivoID.replaceAll("/94d8fcd00036a5f8760edc66/root",""));

        if (objectByPath instanceof Document) {




            ContentStream contentStream = ((Document) objectByPath).getContentStream();

            String fileName = contentStream.getFileName();
            String mimeType = contentStream.getMimeType();
            InputStream stream = contentStream.getStream();
            byte[] bytes = FileCopyUtils.copyToByteArray(stream);
            logger.error("Archivo encontrado InputStream "+archivoID+"--**> "+bytes);
            //ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);
            logger.error("Archivo encontrado InputStream ");
            return bytes;//String.valueOf(byteArrayResource);
            /*long length = bytes.length;

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
            httpHeaders.add("Pragma", "no-cache");
            httpHeaders.add("Expires", "0");
            httpHeaders.setContentLength(length);
            httpHeaders.setContentDispositionFormData("attachment", fileName);*/

            //logger.error("File metadata. fileName: {}, length: {}, mimeType: {}", fileName, length, mimeType);

            /*return ResponseEntity.ok()
                    .headers(httpHeaders)
                    .contentLength(length)
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(byteArrayResource);*/
//            return byteArrayResource;
        }

        throw new PortalException("No se ha encontrado archivo...");

    }

    @GetMapping("/getFolderId")
    public String getFolderId(
                                         @RequestParam(value = "nameFolder") String nameFolder) throws IOException {
        try {
            String folderId = cmisBaseServicecf.createFolder(nameFolder).getNameFolder();//Id();
            return folderId;
        } catch (Exception e) {
            String error = Utils.obtieneMensajeErrorException(e);

            throw new RuntimeException(error);

        }
    }

        @GetMapping(path = "/cmis")
    public ResponseEntity<List<CmisBean>> getAll(@RequestParam(value = "nameFolder") String nameFolder) {
        CmisObject objectByPath = cmisBaseServicecf.getFolder(nameFolder);
        List<CmisBean> docs = new ArrayList<>();

        if (objectByPath instanceof Folder) {
            ItemIterable<CmisObject> children = ((Folder) objectByPath).getChildren();
            for (CmisObject cmisObject : children) {
                docs.add(new CmisBean(cmisObject.getName(), cmisObject.getId()));
            }
        }
        if (objectByPath instanceof Document) {
            docs.add(new CmisBean(objectByPath.getName(), objectByPath.getId()));
        }
        return new ResponseEntity<>(docs, HttpStatus.OK);
    }
    @GetMapping(path = "/pruebaCorreo")
    public ResponseEntity<String> pruebaCorreo(@RequestParam(value = "nameFolder") String nameFolder) {
        actualizacionDeDatosNotificacion.enviarPrueba(null,null,null);
        return new ResponseEntity<>("Ok", HttpStatus.OK);
    }



}
