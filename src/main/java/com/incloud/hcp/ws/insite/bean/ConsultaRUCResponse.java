package com.incloud.hcp.ws.insite.bean;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "consultaRUCResponse", namespace = "http://ws.insite.pe/sunat/ruc.php?wsdl")
@XmlAccessorType(XmlAccessType.FIELD)
public class ConsultaRUCResponse {

    @XmlElement(name = "return")
    private String jsonResponse;

}
