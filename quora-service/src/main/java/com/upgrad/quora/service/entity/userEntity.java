package com.upgrad.quora.service.entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="users")
public class userEntity implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private  String uuid;

    @Column(name = "firstname")
    @NotNull
    @Size(max = 30)
    private String firstName;

    @Column(name = "lastname")
    @NotNull
    @Size(max = 30)
    private String lastName;

    @Column(name = "username")
    @NotNull
    @Size(max = 30)
    private String username;

    @Column(name = "email")
    @NotNull
    @Size(max = 50)
    private String email;

    @Column(name = "password")
    @NotNull
    @Size(max = 255)
    private String password;

    @Column(name = "salt")
    @NotNull
    @Size(max = 200)
    private String salt;

    @Column(name = "country")
    @NotNull
    @Size(max = 30)
    private String country;

    @Column(name = "dob")
    @NotNull
    @Size(max = 30)
    private String dob;

    @Column(name = "aboutme")
    @NotNull
    @Size(max = 50)
    private String aboutMe;

    @Column(name = "role")
    @Size(max = 30)
    private String role;

    @Column(name = "contactnumber")
    @NotNull
    @Size(max = 30)
    private String contact;

    @OneToMany(mappedBy = "user" ,fetch = FetchType.EAGER)
    List<questionEntity> questions= new ArrayList<>();

    public List<questionEntity> getQuestions() {
        return questions;
    }

    public void setQuestions(List<questionEntity> questions) {
        this.questions = questions;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Integer getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getCountry() {
        return country;
    }

    public String getDob() {
        return dob;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public String getRole() {
        return role;
    }

    public String getContact() {
        return contact;
    }

    @Override
    public boolean equals(Object obj) {
        return new EqualsBuilder().append(this, obj).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this).hashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
