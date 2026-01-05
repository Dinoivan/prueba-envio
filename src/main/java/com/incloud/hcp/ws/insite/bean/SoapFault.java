
package com.incloud.hcp.ws.insite.bean;

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
@XmlRootElement(name = "Fault", namespace = "http://schemas.xmlsoap.org/soap/envelope/")
@XmlAccessorType(XmlAccessType.FIELD)
public class SoapFault implements Serializable {

    @XmlElement(name = "faultcode")
    private String faultCode;

    @XmlElement(name = "faultstring")
    private String faultString;

    @XmlElement(name = "faultactor")
    private String faulActor;

    @XmlElement(name = "detail")
    private String detail;

}