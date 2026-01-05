package com.incloud.hcp.ws.ias.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import com.incloud.hcp.dto.GroupDto;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IASUserInfoResponse implements Serializable {

    private Integer totalResults;
    private Integer itemsPerPage;
    private List<String> schemas;
    @JsonProperty("Resources")
    private List<Resource> resources;
    private List<GroupDto> groups;

    public List<GroupDto> getGroups() {
        return groups;
    }

    public void setGroups(List<GroupDto> groups) {
        this.groups = groups;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    public List<String> getSchemas() {
        return schemas;
    }

    public void setSchemas(List<String> schemas) {
        this.schemas = schemas;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Resource implements Serializable {

        private String passwordStatus;
        private String passwordPolicy;
        private String passwordSetTime;
        private String displayName;
        private String sourceSystem;
        private boolean active;
        private String userName;
        private String passwordLoginTime;
        private String passwordFailedLoginAttempts;
        @JsonProperty("emails")
        private List<Emails> emails;
        private List<String> schemas;
        private String userUuid;
        private NameInfo name;
        private String id;
        private String userType;
        private String mailVerified;

        public String getPasswordStatus() {
            return passwordStatus;
        }

        public void setPasswordStatus(String passwordStatus) {
            this.passwordStatus = passwordStatus;
        }

        public String getPasswordPolicy() {
            return passwordPolicy;
        }

        public void setPasswordPolicy(String passwordPolicy) {
            this.passwordPolicy = passwordPolicy;
        }

        public String getPasswordSetTime() {
            return passwordSetTime;
        }

        public void setPasswordSetTime(String passwordSetTime) {
            this.passwordSetTime = passwordSetTime;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getSourceSystem() {
            return sourceSystem;
        }

        public void setSourceSystem(String sourceSystem) {
            this.sourceSystem = sourceSystem;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPasswordLoginTime() {
            return passwordLoginTime;
        }

        public void setPasswordLoginTime(String passwordLoginTime) {
            this.passwordLoginTime = passwordLoginTime;
        }

        public String getPasswordFailedLoginAttempts() {
            return passwordFailedLoginAttempts;
        }

        public void setPasswordFailedLoginAttempts(String passwordFailedLoginAttempts) {
            this.passwordFailedLoginAttempts = passwordFailedLoginAttempts;
        }

        public List<Emails> getEmails() {
            return emails;
        }

        public void setEmails(List<Emails> emails) {
            this.emails = emails;
        }

        public List<String> getSchemas() {
            return schemas;
        }

        public void setSchemas(List<String> schemas) {
            this.schemas = schemas;
        }

        public String getUserUuid() {
            return userUuid;
        }

        public void setUserUuid(String userUuid) {
            this.userUuid = userUuid;
        }

        public NameInfo getName() {
            return name;
        }

        public void setName(NameInfo name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUserType() {
            return userType;
        }

        public void setUserType(String userType) {
            this.userType = userType;
        }

        public String getMailVerified() {
            return mailVerified;
        }

        public void setMailVerified(String mailVerified) {
            this.mailVerified = mailVerified;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class NameInfo implements Serializable {
        private String givenName;
        private String familyName;

        public String getGivenName() {
            return givenName;
        }

        public void setGivenName(String givenName) {
            this.givenName = givenName;
        }

        public String getFamilyName() {
            return familyName;
        }

        public void setFamilyName(String familyName) {
            this.familyName = familyName;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Emails implements Serializable {
        @JsonProperty("value")
        private String value;
        @JsonProperty("primary")
        private boolean primary;

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public boolean isPrimary() {
            return primary;
        }

        public void setPrimary(boolean primary) {
            this.primary = primary;
        }
    }

}
