package com.incloud.hcp.myibatis.mapper;

import com.incloud.hcp.dto.SupplierUserDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ISupplierUserMapper {

    List<SupplierUserDto> getAllSuppliers();

}