package com.incloud.hcp.facade;

import com.incloud.hcp.dto.BancoDto;

import java.util.List;

public interface BancoFacade {
    List<BancoDto> getListAll();
}
