package com.incloud.hcp.service._framework;

import com.incloud.hcp.domain._framework.BaseDomain;
import com.incloud.hcp.domain._framework.BaseResponseDomain;
import com.incloud.hcp.service.support.PageResponse;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface JPACustomService<R extends BaseResponseDomain, T extends BaseDomain> {

    /* Metodos de Busqueda */

    List<T> findCondicion(R req) throws Exception;
    PageResponse<T> findCondicionPaginated(R req, PageRequest pageRequest) throws Exception;


}
