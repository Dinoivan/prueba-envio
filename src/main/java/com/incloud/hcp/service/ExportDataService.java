package com.incloud.hcp.service;

import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;
import java.util.Map;

public interface ExportDataService {

	Workbook mapToWorkbook(Map map);

	Workbook mapToWorkbook(String titulo, List<Map<String, Object>> cabecera, Map map);
    Workbook mapToWorkbook(String titulo, Map map);
	void addSheet(Workbook workbook,Map map,String titulo);

}
