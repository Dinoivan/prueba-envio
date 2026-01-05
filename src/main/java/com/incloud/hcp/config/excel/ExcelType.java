package  com.incloud.hcp.config.excel;

public enum ExcelType {

    XLS("application/vnd.ms-excel", ".xls"),
    XLSX("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", ".xlsx"),
    XLSM("application/vnd.ms-excel.sheet.macroEnabled.12", ".xlsm");

    private String contentType;
    private String extension;

    ExcelType(String contentType, String extension) {
        this.contentType = contentType;
        this.extension = extension;
    }

    public String getContentType() {
        return contentType;
    }

    public String getExtension() {
        return extension;
    }
}
