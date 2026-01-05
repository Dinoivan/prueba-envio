package com.incloud.hcp.ws.ias.dto;

import java.io.Serializable;
import java.util.List;

public class IASUserDto implements Serializable{

    private IASName name;
    private String displayName;
    private String userName;
    private String contactPreferenceEmail;
    private String sendMail;
    private List<IASValue> groups;
    private List<IASValue> emails;

    private Boolean active;



    public IASName getName() {
        return name;
    }

    public void setName(IASName name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactPreferenceEmail() {
        return contactPreferenceEmail;
    }

    public void setContactPreferenceEmail(String contactPreferenceEmail) {
        this.contactPreferenceEmail = contactPreferenceEmail;
    }

    public String getSendMail() {
        return sendMail;
    }

    public void setSendMail(String sendMail) {
        this.sendMail = sendMail;
    }

    public List<IASValue> getGroups() {
        return groups;
    }

    public void setGroups(List<IASValue> groups) {
        this.groups = groups;
    }

    public List<IASValue> getEmails() {
        return emails;
    }

    public void setEmails(List<IASValue> emails) {
        this.emails = emails;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "IASUserDto{" +
                "name=" + name +
                ", displayName='" + displayName + '\'' +
                ", userName='" + userName + '\'' +
                ", contactPreferenceEmail='" + contactPreferenceEmail + '\'' +
                ", sendMail='" + sendMail + '\'' +
                ", groups=" + groups +
                ", emails=" + emails +
                '}';
    }

    public static class IASName implements Serializable {
        private String givenName;
        private String familyName;

        public IASName(String givenName, String familyName) {
            this.givenName = givenName;
            this.familyName = familyName;
        }

        public IASName() {
        }

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

        @Override
        public String toString() {
            return "IASName{" +
                    "givenName='" + givenName + '\'' +
                    ", familyName='" + familyName + '\'' +
                    '}';
        }
    }

    public static class IASValue implements Serializable {
        private String value;
        private Boolean primary;

        public IASValue(String value) {
            this.value = value;
        }

        public IASValue() {
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        public Boolean getPrimary() {
            return primary;
        }

        public void setPrimary(Boolean primary) {
            this.primary = primary;
        }

        @Override
        public String toString() {
            return "IASValue{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }

}
