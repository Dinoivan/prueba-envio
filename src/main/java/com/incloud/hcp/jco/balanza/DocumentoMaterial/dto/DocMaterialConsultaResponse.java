package com.incloud.hcp.jco.balanza.DocumentoMaterial.dto;

public class DocMaterialConsultaResponse {

    private String pos;
    private String product;
    private String supplier;
    private String dimension;
    private String weight;
    private String price;
    private String currency;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public DocMaterialConsultaResponse() {

    }

    @Override
    public String toString() {
        return "DocMaterialConsultaResponse{" +
                "pos=" + pos +
                ", product='" + product + '\'' +
                ", supplier='" + supplier + '\'' +
                ", dimension='" + dimension + '\'' +
                ", weight=" + weight +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                '}';
    }
}
