package com.inn.collab.POJO;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;


//brand signup create table
@NamedQuery(name = "User_Signup.findByEmailId",query = "select bs from User_Signup bs where bs.email=:email")

@NamedQuery(name = "User_Signup.findRowById",query = "select bs from User_Signup bs where bs.id=:id")

@NamedQuery(name = "User_Signup.findRoleById", query = "select u.role from User_Signup u where u.id=:id")


@NamedQuery(name = "User_Signup.getAllUser", query = "Select new com.inn.collab.wrapper.UserWrapper(u.id, u.email, u.name, u.password, u.gender, u.bin_nid_number, u.origin_city, u.location, u.status, u.phone, u.established_year, u.website, u.facebook, u.instagram, u.twitter, u.youtube, u.experience_year, u.english_proficiency) from User_Signup u where u.role in ('influencer', 'brand')")

@NamedQuery(name = "User_Signup.Profile", query = "SELECT new com.inn.collab.wrapper.UserWrapper(u.id, u.email, u.name, u.gender, u.origin_city, u.location, u.status, u.role, u.phone, u.established_year, u.website, u.facebook, u.instagram, u.twitter, u.youtube, u.experience_year, u.english_proficiency) FROM User_Signup u WHERE u.id = :id")

@NamedQuery(name = "User_Signup.updateStatus", query = "update User_Signup u set u.status=:status where u.id=:id")

@NamedQuery(name = "User_Signup.getAllAdmin", query = "select u.email from User_Signup u where u.role='admin'")

@NamedQuery(name = "User_Signup.SeeAllProfiles", query = "SELECT new com.inn.collab.wrapper.UserWrapper(u.id, u.email, u.name, u.gender, u.origin_city, u.location, u.status, u.role, u.phone, u.established_year, u.website, u.facebook, u.instagram, u.twitter, u.youtube, u.experience_year, u.english_proficiency) FROM User_Signup u WHERE u.id <> :id AND u.role in ('influencer', 'brand') ORDER BY u.role,u.name")

@NamedQuery(name = "User_Signup.SeeAllUserProfiles", query = "SELECT new com.inn.collab.wrapper.UserWrapper(u.id, u.email, u.name, u.gender, u.origin_city, u.location, u.status, u.role, u.phone, u.established_year, u.website, u.facebook, u.instagram, u.twitter, u.youtube, u.experience_year, u.english_proficiency) FROM User_Signup u WHERE u.id <> :id AND u.role in ('influencer', 'brand') ORDER BY u.role,u.name")

@NamedQuery(name = "User_Signup.deleteRowById", query = "Delete From User_Signup u Where u.id =:id")


//Using getter and setter by default
@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user_signup")
public class User_Signup implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name= "email", length = 50)
    private String email;
    
    @Column(name= "name", length = 50)
    private String name;
    
    @Column(name= "password", length = 150)
    private String password;

    @Column(name= "gender", length = 20)
    private String gender;

    @Column(name= "bin_nid_number", length = 13)
    private String bin_nid_number;

    @Column(name= "origin_city", length = 20)
    private String origin_city;

    @Column(name= "location", length = 100)
    private String location;

    @Column(name= "status", length = 10)
    private String status;

    @Column(name= "role", length = 10)
    private String role;

    //Extra
    @Column(name = "phone", length = 11)
    private String phone;

    @Column(name = "established_year", length = 50)  //For Brand Only
    private String established_year;

    @Column(name = "website", length = 100)
    private String website;

    @Column(name = "facebook", length = 100)
    private String facebook;

    @Column(name = "instagram", length = 100)
    private String instagram;

    @Column(name = "twitter", length = 100)
    private String twitter;

    @Column(name = "youtube", length = 100)
    private String youtube;

    @Column(name = "experience_year", length = 50)   //For Influencer Only
    private String experience_year;

    @Column(name = "english_proficiency", length = 50)  //For Influencer Only
    private String english_proficiency;

}