package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

import java.util.Date;

public class DocMaterialGenerarSAPDto {

    private String exportaPstngdate;
    private String exportaDocdate;
    private String exportaHeadertxt;
    private String exportaGmcode;
    private String exportaMaterial;
    private String exportaPlant;
    private String exportaStgeloc;
    private String exportaBatch;
    private String exportaMovetype;
    private Integer exportaEntryqnt;
    private String exportaMoveplant;
    private String exportaMovestloc;
    private String exportaMovebatch;


    private String nacionalShippoint;
    private Date nacionalDuedate;
    private String nacionalRefdoc;
    private Integer nacionalRefitem;
    private Integer nacionalDlvqty;
    private String nacionalSalesunit;
    private String nacionalSalesunitiso;
    private String nacionalDelivnumb;
    private String nacionalReturnsdelivnumb;
    private Integer nacionalReturnsdelivitem;



    private Date transportPstngdate;
    private Date transportDocdate;
    private String transportHeadertxt;
    private String transportGmcode;
    private String transportMaterial;
    private String transportPlant;
    private String transportStgeloc;
    private String transportBatch;
    private String transportMovetype;
    private Integer transportEntryqnt;
    private String transportMoveplant;
    private String transportMovestloc;
    private String transportMovebatch;

    private String cantidad;
    private String tipoPesaje;
    private String numeroPedido;
    private String posicion;
    private Integer posicionDocumento;
    private String centro;
    private String material;
    private String almacen;
    private String lote;
    private String unidadMedida;
    private String documentoTraslado;

    private String serieGuia;
    private Integer nroGuia;
    private String ticketPesaje;

    // TIPO PESAJE 101
    private String refDocYr;
    private String refDoc;
    private String refDocIt;
    private String mvtInd;

    // TIPO PESAJE 301
    private Integer index;

    // Transport & Exportacion
    private String referenciaTE;
    // Nacional
    private String referenciaNacional;

    //
    private Integer id;
    private Integer idTicketPesaje;

    private String tipoProducto;

    public String getExportaPstngdate() {
        return exportaPstngdate;
    }

    public void setExportaPstngdate(String exportaPstngdate) {
        this.exportaPstngdate = exportaPstngdate;
    }

    public String getExportaDocdate() {
        return exportaDocdate;
    }

    public void setExportaDocdate(String exportaDocdate) {
        this.exportaDocdate = exportaDocdate;
    }

    public String getExportaHeadertxt() {
        return exportaHeadertxt;
    }

    public void setExportaHeadertxt(String exportaHeadertxt) {
        this.exportaHeadertxt = exportaHeadertxt;
    }

    public String getExportaGmcode() {
        return exportaGmcode;
    }

    public void setExportaGmcode(String exportaGmcode) {
        this.exportaGmcode = exportaGmcode;
    }

    public String getExportaMaterial() {
        return exportaMaterial;
    }

    public void setExportaMaterial(String exportaMaterial) {
        this.exportaMaterial = exportaMaterial;
    }

    public String getExportaPlant() {
        return exportaPlant;
    }

    public void setExportaPlant(String exportaPlant) {
        this.exportaPlant = exportaPlant;
    }

    public String getExportaStgeloc() {
        return exportaStgeloc;
    }

    public void setExportaStgeloc(String exportaStgeloc) {
        this.exportaStgeloc = exportaStgeloc;
    }

    public String getExportaBatch() {
        return exportaBatch;
    }

    public void setExportaBatch(String exportaBatch) {
        this.exportaBatch = exportaBatch;
    }

    public String getExportaMovetype() {
        return exportaMovetype;
    }

    public void setExportaMovetype(String exportaMovetype) {
        this.exportaMovetype = exportaMovetype;
    }

    public Integer getExportaEntryqnt() {
        return exportaEntryqnt;
    }

    public void setExportaEntryqnt(Integer exportaEntryqnt) {
        this.exportaEntryqnt = exportaEntryqnt;
    }

    public String getExportaMoveplant() {
        return exportaMoveplant;
    }

    public void setExportaMoveplant(String exportaMoveplant) {
        this.exportaMoveplant = exportaMoveplant;
    }

    public String getExportaMovestloc() {
        return exportaMovestloc;
    }

    public void setExportaMovestloc(String exportaMovestloc) {
        this.exportaMovestloc = exportaMovestloc;
    }

    public String getExportaMovebatch() {
        return exportaMovebatch;
    }

    public void setExportaMovebatch(String exportaMovebatch) {
        this.exportaMovebatch = exportaMovebatch;
    }

    public String getNacionalShippoint() {
        return nacionalShippoint;
    }

    public void setNacionalShippoint(String nacionalShippoint) {
        this.nacionalShippoint = nacionalShippoint;
    }

    public Date getNacionalDuedate() {
        return nacionalDuedate;
    }

    public void setNacionalDuedate(Date nacionalDuedate) {
        this.nacionalDuedate = nacionalDuedate;
    }

    public String getNacionalRefdoc() {
        return nacionalRefdoc;
    }

    public void setNacionalRefdoc(String nacionalRefdoc) {
        this.nacionalRefdoc = nacionalRefdoc;
    }

    public Integer getNacionalRefitem() {
        return nacionalRefitem;
    }

    public void setNacionalRefitem(Integer nacionalRefitem) {
        this.nacionalRefitem = nacionalRefitem;
    }

    public Integer getNacionalDlvqty() {
        return nacionalDlvqty;
    }

    public void setNacionalDlvqty(Integer nacionalDlvqty) {
        this.nacionalDlvqty = nacionalDlvqty;
    }

    public String getNacionalSalesunit() {
        return nacionalSalesunit;
    }

    public void setNacionalSalesunit(String nacionalSalesunit) {
        this.nacionalSalesunit = nacionalSalesunit;
    }

    public String getNacionalSalesunitiso() {
        return nacionalSalesunitiso;
    }

    public void setNacionalSalesunitiso(String nacionalSalesunitiso) {
        this.nacionalSalesunitiso = nacionalSalesunitiso;
    }

    public String getNacionalDelivnumb() {
        return nacionalDelivnumb;
    }

    public void setNacionalDelivnumb(String nacionalDelivnumb) {
        this.nacionalDelivnumb = nacionalDelivnumb;
    }

    public String getNacionalReturnsdelivnumb() {
        return nacionalReturnsdelivnumb;
    }

    public void setNacionalReturnsdelivnumb(String nacionalReturnsdelivnumb) {
        this.nacionalReturnsdelivnumb = nacionalReturnsdelivnumb;
    }

    public Integer getNacionalReturnsdelivitem() {
        return nacionalReturnsdelivitem;
    }

    public void setNacionalReturnsdelivitem(Integer nacionalReturnsdelivitem) {
        this.nacionalReturnsdelivitem = nacionalReturnsdelivitem;
    }

    public Date getTransportPstngdate() {
        return transportPstngdate;
    }

    public void setTransportPstngdate(Date transportPstngdate) {
        this.transportPstngdate = transportPstngdate;
    }

    public Date getTransportDocdate() {
        return transportDocdate;
    }

    public void setTransportDocdate(Date transportDocdate) {
        this.transportDocdate = transportDocdate;
    }

    public String getTransportHeadertxt() {
        return transportHeadertxt;
    }

    public void setTransportHeadertxt(String transportHeadertxt) {
        this.transportHeadertxt = transportHeadertxt;
    }

    public String getTransportGmcode() {
        return transportGmcode;
    }

    public void setTransportGmcode(String transportGmcode) {
        this.transportGmcode = transportGmcode;
    }

    public String getTransportMaterial() {
        return transportMaterial;
    }

    public void setTransportMaterial(String transportMaterial) {
        this.transportMaterial = transportMaterial;
    }

    public String getTransportPlant() {
        return transportPlant;
    }

    public void setTransportPlant(String transportPlant) {
        this.transportPlant = transportPlant;
    }

    public String getTransportStgeloc() {
        return transportStgeloc;
    }

    public void setTransportStgeloc(String transportStgeloc) {
        this.transportStgeloc = transportStgeloc;
    }

    public String getTransportBatch() {
        return transportBatch;
    }

    public void setTransportBatch(String transportBatch) {
        this.transportBatch = transportBatch;
    }

    public String getTransportMovetype() {
        return transportMovetype;
    }

    public void setTransportMovetype(String transportMovetype) {
        this.transportMovetype = transportMovetype;
    }

    public Integer getTransportEntryqnt() {
        return transportEntryqnt;
    }

    public void setTransportEntryqnt(Integer transportEntryqnt) {
        this.transportEntryqnt = transportEntryqnt;
    }

    public String getTransportMoveplant() {
        return transportMoveplant;
    }

    public void setTransportMoveplant(String transportMoveplant) {
        this.transportMoveplant = transportMoveplant;
    }

    public String getTransportMovestloc() {
        return transportMovestloc;
    }

    public void setTransportMovestloc(String transportMovestloc) {
        this.transportMovestloc = transportMovestloc;
    }

    public String getTransportMovebatch() {
        return transportMovebatch;
    }

    public void setTransportMovebatch(String transportMovebatch) {
        this.transportMovebatch = transportMovebatch;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoPesaje() {
        return tipoPesaje;
    }

    public void setTipoPesaje(String tipoPesaje) {
        this.tipoPesaje = tipoPesaje;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public String getPosicion() {
        return posicion;
    }

    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getAlmacen() {
        return almacen;
    }

    public void setAlmacen(String almacen) {
        this.almacen = almacen;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public String getDocumentoTraslado() {
        return documentoTraslado;
    }

    public void setDocumentoTraslado(String documentoTraslado) {
        this.documentoTraslado = documentoTraslado;
    }

    public String getSerieGuia() {
        return serieGuia;
    }

    public void setSerieGuia(String serieGuia) {
        this.serieGuia = serieGuia;
    }

    public Integer getNroGuia() {
        return nroGuia;
    }

    public void setNroGuia(Integer nroGuia) {
        this.nroGuia = nroGuia;
    }

    public String getTicketPesaje() {
        return ticketPesaje;
    }

    public void setTicketPesaje(String ticketPesaje) {
        this.ticketPesaje = ticketPesaje;
    }

    public String getRefDocYr() {
        return refDocYr;
    }

    public void setRefDocYr(String refDocYr) {
        this.refDocYr = refDocYr;
    }

    public String getRefDoc() {
        return refDoc;
    }

    public void setRefDoc(String refDoc) {
        this.refDoc = refDoc;
    }

    public String getRefDocIt() {
        return refDocIt;
    }

    public void setRefDocIt(String refDocIt) {
        this.refDocIt = refDocIt;
    }

    public String getMvtInd() {
        return mvtInd;
    }

    public void setMvtInd(String mvtInd) {
        this.mvtInd = mvtInd;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public String getReferenciaTE() {
        return referenciaTE;
    }

    public void setReferenciaTE(String referenciaTE) {
        this.referenciaTE = referenciaTE;
    }

    public String getReferenciaNacional() {
        return referenciaNacional;
    }

    public void setReferenciaNacional(String referenciaNacional) {
        this.referenciaNacional = referenciaNacional;
    }

    Date now = new Date();
    public DocMaterialGenerarSAPDto() {
        this.exportaPstngdate = "";
        this.exportaDocdate = "";
        this.exportaHeadertxt = "";
        this.exportaGmcode = "";
        this.exportaMaterial = "";
        this.exportaPlant = "";
        this.exportaStgeloc = "";
        this.exportaBatch = "";
        this.exportaMovetype = "";
        this.exportaEntryqnt = 0;
        this.exportaMoveplant = "";
        this.exportaMovestloc = "";
        this.exportaMovebatch = "";
        this.nacionalShippoint = "";
        this.nacionalDuedate = now;
        this.nacionalRefdoc = "";
        this.nacionalRefitem = 0;
        this.nacionalDlvqty = 0;
        this.nacionalSalesunit = "";
        this.nacionalSalesunitiso = "";
        this.nacionalDelivnumb = "";
        this.nacionalReturnsdelivnumb = "";
        this.nacionalReturnsdelivitem = 0;
        this.transportPstngdate = now;
        this.transportDocdate = now;
        this.transportHeadertxt = "";
        this.transportGmcode = "";
        this.transportMaterial = "";
        this.transportPlant = "";
        this.transportStgeloc = "";
        this.transportBatch = "";
        this.transportMovetype = "";
        this.transportEntryqnt = 0;
        this.transportMoveplant = "";
        this.transportMovestloc = "";
        this.transportMovebatch = "";
        this.cantidad = "";
        this.tipoPesaje = "";
        this.numeroPedido = "";
        this.posicion = "";
        this.centro = "";
        this.material = "";
        this.almacen = "";
        this.lote = "";
        this.unidadMedida = "";
        this.documentoTraslado = "";
        this.serieGuia = "";
        this.nroGuia = 0;
        this.ticketPesaje = "";
        this.refDocYr = "";
        this.refDoc = "";
        this.refDocIt = "";
        this.mvtInd = "";
        this.index = 0;
        this.referenciaNacional = "";
        this.referenciaTE = "";
        this.id = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTipoProducto() {
        return tipoProducto;
    }

    public void setTipoProducto(String tipoProducto) {
        this.tipoProducto = tipoProducto;
    }

    public Integer getIdTicketPesaje() {
        return idTicketPesaje;
    }

    public void setIdTicketPesaje(Integer idTicketPesaje) {
        this.idTicketPesaje = idTicketPesaje;
    }

    public Integer getPosicionDocumento() {
        return posicionDocumento;
    }

    public void setPosicionDocumento(Integer posicionDocumento) {
        this.posicionDocumento = posicionDocumento;
    }

    @Override
    public String toString() {
        return "DocMaterialGenerarSAPDto{" +
                "exportaPstngdate='" + exportaPstngdate + '\'' +
                ", exportaDocdate='" + exportaDocdate + '\'' +
                ", exportaHeadertxt='" + exportaHeadertxt + '\'' +
                ", exportaGmcode='" + exportaGmcode + '\'' +
                ", exportaMaterial='" + exportaMaterial + '\'' +
                ", exportaPlant='" + exportaPlant + '\'' +
                ", exportaStgeloc='" + exportaStgeloc + '\'' +
                ", exportaBatch='" + exportaBatch + '\'' +
                ", exportaMovetype='" + exportaMovetype + '\'' +
                ", exportaEntryqnt=" + exportaEntryqnt +
                ", exportaMoveplant='" + exportaMoveplant + '\'' +
                ", exportaMovestloc='" + exportaMovestloc + '\'' +
                ", exportaMovebatch='" + exportaMovebatch + '\'' +
                ", nacionalShippoint='" + nacionalShippoint + '\'' +
                ", nacionalDuedate=" + nacionalDuedate +
                ", nacionalRefdoc='" + nacionalRefdoc + '\'' +
                ", nacionalRefitem=" + nacionalRefitem +
                ", nacionalDlvqty=" + nacionalDlvqty +
                ", nacionalSalesunit='" + nacionalSalesunit + '\'' +
                ", nacionalSalesunitiso='" + nacionalSalesunitiso + '\'' +
                ", nacionalDelivnumb='" + nacionalDelivnumb + '\'' +
                ", nacionalReturnsdelivnumb='" + nacionalReturnsdelivnumb + '\'' +
                ", nacionalReturnsdelivitem=" + nacionalReturnsdelivitem +
                ", transportPstngdate=" + transportPstngdate +
                ", transportDocdate=" + transportDocdate +
                ", transportHeadertxt='" + transportHeadertxt + '\'' +
                ", transportGmcode='" + transportGmcode + '\'' +
                ", transportMaterial='" + transportMaterial + '\'' +
                ", transportPlant='" + transportPlant + '\'' +
                ", transportStgeloc='" + transportStgeloc + '\'' +
                ", transportBatch='" + transportBatch + '\'' +
                ", transportMovetype='" + transportMovetype + '\'' +
                ", transportEntryqnt=" + transportEntryqnt +
                ", transportMoveplant='" + transportMoveplant + '\'' +
                ", transportMovestloc='" + transportMovestloc + '\'' +
                ", transportMovebatch='" + transportMovebatch + '\'' +
                ", cantidad='" + cantidad + '\'' +
                ", tipoPesaje='" + tipoPesaje + '\'' +
                ", numeroPedido='" + numeroPedido + '\'' +
                ", posicion='" + posicion + '\'' +
                ", centro='" + centro + '\'' +
                ", material='" + material + '\'' +
                ", almacen='" + almacen + '\'' +
                ", lote='" + lote + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                ", documentoTraslado='" + documentoTraslado + '\'' +
                ", serieGuia='" + serieGuia + '\'' +
                ", nroGuia=" + nroGuia +
                ", ticketPesaje='" + ticketPesaje + '\'' +
                ", refDocYr='" + refDocYr + '\'' +
                ", refDoc='" + refDoc + '\'' +
                ", refDocIt='" + refDocIt + '\'' +
                ", mvtInd='" + mvtInd + '\'' +
                ", index=" + index +
                ", referenciaTE='" + referenciaTE + '\'' +
                ", referenciaNacional='" + referenciaNacional + '\'' +
                ", now=" + now +
                '}';
    }
}
