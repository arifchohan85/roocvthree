package com.egeroo.roocvthree.userprofile;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.egeroo.roocvthree.core.base.Base;


@Entity
@Table( name = "ms_app_userprofile" )
public class UserProfileIndex extends Base{

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    private int userProfileid;


    private int roleId;

    //@NotNull(message ="Username is a required field")
    //@Size(min=10, max=50)
    //@Email(message = "Please provide valid email address for username")
    private String userName;

    //@NotNull(message ="Password is a required field")
    //@Size(min=8, max=255)


    //@NotNull
    //@Size(min=2, max=255)
    private String name;





    private String source;


    private int isActive;


    private String emailAddress;



    private String roleName;

    private String createdBy;




    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public int getUserProfileid() {
        return userProfileid;
    }

    public void setUserProfileid(int userProfileid) {
        this.userProfileid = userProfileid;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
}