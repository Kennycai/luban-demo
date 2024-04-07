package com.example.uaa.ldap.dao.entity;

import lombok.Data;
import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;
import java.util.List;

@Entry(base = "ou=people", objectClasses = { "inetOrgPerson", "top" })
@Data
public class Person {

    @Id
    private Name id;

    @Attribute(name = "cn")
    private String fullName;

    @Attribute(name = "sn")
    private String lastName;

    @Attribute(name = "mail")
    private String email;

    @Attribute(name = "userPassword")
    private String password;
    private List<String> objectClasses;
}
