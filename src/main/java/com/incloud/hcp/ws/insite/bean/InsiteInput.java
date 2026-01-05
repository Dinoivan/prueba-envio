package com.incloud.hcp.ws.insite.bean;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsiteInput {

    private String ruc;
    private String username;
    private String hash;
    private String tracking;

}
