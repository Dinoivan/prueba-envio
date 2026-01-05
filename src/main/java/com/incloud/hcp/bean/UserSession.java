package com.incloud.hcp.bean;

import com.incloud.hcp.dto.GroupDto;

import java.util.List;

/**
 * Created by Administrador on 07/11/2017.
 */
public class UserSession {
    private String id;
    private String userName;
    private String ruc;
    private String mail;
    private String displayName;
    private String firstName;
    private String lastName;
//    private String sapUsername;

    private List<String> groupList;
    private List<String> roleList;
    private String correoSociedad;
    private List<String> codigoSociedades;
    private List<GroupDto> listaGrupos;
    private String body;

    public UserSession() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<String> getGroupList() {
        return groupList;
    }

    public void setGroupList(List<String> groupList) {
        this.groupList = groupList;
    }

    public List<String> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

//    public String getSapUsername() {
//        return sapUsername;
//    }
//
//    public void setSapUsername(String sapUsername) {
//        this.sapUsername = sapUsername;
//    }


    public String getCorreoSociedad() {
        return correoSociedad;
    }

    public void setCorreoSociedad(String correoSociedad) {
        this.correoSociedad = correoSociedad;
    }

    public List<String> getCodigoSociedades() {
        return codigoSociedades;
    }

    public void setCodigoSociedades(List<String> codigoSociedades) {
        this.codigoSociedades = codigoSociedades;
    }

    public List<GroupDto> getListaGrupos() {
        return listaGrupos;
    }

    public void setListaGrupos(List<GroupDto> listaGrupos) {
        this.listaGrupos = listaGrupos;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "UserSession{" +
                "id='" + id + '\'' +
                ", userName='" + userName + '\'' +
                ", ruc='" + ruc + '\'' +
                ", mail='" + mail + '\'' +
                ", displayName='" + displayName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
//                ", sapUsername='" + sapUsername + '\'' +
                ", groupList=" + groupList +
                ", roleList=" + roleList +
                '}';
    }
}
