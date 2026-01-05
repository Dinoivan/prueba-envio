package com.incloud.hcp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.incloud.hcp.util.DateUtils;
import lombok.*;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "supplier_user")
public class SupplierUser implements Serializable {

    public static String entityName = "SupplierUser";

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    @Column( name = "supplier_user_id", unique = true, nullable = false )
    private Integer id;

    @Column( name = "email", nullable = false, length = 70 )
    private String email;

    @Column( name = "cel", nullable = true, length = 30 )
    private String cel;

    @Column( name = "creation_date", nullable = false )
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT_RESOURCES, locale = "es_PE", timezone = "America/Lima" )
    private Date creationDate;

    @Column( name = "update_date", nullable = true )
    @JsonFormat( shape = JsonFormat.Shape.STRING, pattern = DateUtils.DATE_TIME_FORMAT_RESOURCES, locale = "es_PE", timezone = "America/Lima" )
    private Date updateDate;

    @Column( name = "first_name", nullable = false, length = 70 )
    private String firstName;

    @Column( name = "last_name", nullable = false, length = 100 )
    private String lastName;

    @Column( name = "display_name", nullable = false, length = 180 )
    private String displayName;

    @Column( name = "identity_provider_code", length = 20 )
    private String identityProviderCode;

    @Column( name = "user_name", length = 40 )
    private String userName;

    @Column( name = "supplier_id", nullable = false )
    private Integer supplierId;

}