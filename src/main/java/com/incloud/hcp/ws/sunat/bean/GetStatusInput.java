package com.incloud.hcp.ws.sunat.bean;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStatusInput {

    private String rucCliente; //company
    private String rucComprobante; //rucProveedor
    private String tipoComprobante;
    private String serieComprobante;
    private Integer numeroComprobante;

}
