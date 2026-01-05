package com.incloud.hcp.facade;

import com.incloud.hcp.dto.CondicionPagoDto;

import java.util.List;

public interface CondicionPagoFacade {
    List<CondicionPagoDto> getListAll();
}
