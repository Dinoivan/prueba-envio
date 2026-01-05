package com.incloud.hcp.ws.ias.service;

import com.incloud.hcp.ws.ias.bean.IASResponse;
import com.incloud.hcp.ws.ias.dto.IASUserDto;

public interface IUserIASService {

    IASResponse getUserByEmail(String email);

    IASResponse getUserAll();

    IASResponse createUserProveedor(IASUserDto iasUserDto);

    IASResponse getUserByLoginName(String loginName);

    IASResponse deleteUsuarioIas(String usuarioId);
}
