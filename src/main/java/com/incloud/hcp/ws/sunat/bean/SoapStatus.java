package com.incloud.hcp.ws.sunat.bean;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "status")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapStatus {

    @XmlElement(name = "statusCode")
    private String statusCode;

    @XmlElement(name = "statusMessage")
    private String statusMessage;

}
