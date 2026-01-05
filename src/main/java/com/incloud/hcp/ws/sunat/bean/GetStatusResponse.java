package com.incloud.hcp.ws.sunat.bean;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "getStatusResponse", namespace = "http://service.sunat.gob.pe")
@XmlAccessorType(XmlAccessType.FIELD)
public class GetStatusResponse implements Serializable {

    @XmlElement(name = "status")
    private SoapStatus status;

}
