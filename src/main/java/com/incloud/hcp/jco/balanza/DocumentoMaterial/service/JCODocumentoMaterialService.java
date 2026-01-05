package com.incloud.hcp.jco.balanza.DocumentoMaterial.service;


import com.incloud.hcp.jco.balanza.DocumentoMaterial.dto.*;

import java.util.List;


public interface JCODocumentoMaterialService {

    List<DocumentoMaterialResponseDto> generarDocumentoMaterialRfc(List<DocMaterialGenerarSAPDto> docMaterialGenerarDto

    )throws Exception;

    List<DocMaterialConsultaResponse> consultaDocumentoMaterialRFC(DocMaterialConsultaDto doc)throws Exception;


}
