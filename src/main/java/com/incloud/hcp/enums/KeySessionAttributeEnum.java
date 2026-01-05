package com.incloud.hcp.enums;

/**
 * Created by Administrador on 09/10/2017.
 */
public enum KeySessionAttributeEnum {
    USUARIO_PORTAL("usuarioPortal");

    private final String key;

    private KeySessionAttributeEnum(String key) {
        this.key = key;
    }

    public String getKey() {
        return this.key;
    }
}
