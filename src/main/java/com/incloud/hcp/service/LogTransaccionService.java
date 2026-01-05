package com.incloud.hcp.service;

import com.sap.cloud.security.xsuaa.token.Token;

public interface LogTransaccionService {

    void grabarNuevaLineaLogTransaccion(String tipoTransaccion, String tipoRegistro, String envioTrama, Integer idRegistro, String respuestaCodigo, String respuestaTexto, boolean isJob, Token token);

    String getUsuarioTransaccionBD(String transaccion, String tipoRegistro, Integer idRegistro);
}
