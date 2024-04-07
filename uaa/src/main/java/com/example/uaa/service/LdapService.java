package com.example.uaa.service;

import com.example.uaa.entity.Users;
import com.example.uaa.ldap.dao.entity.Person;

import javax.naming.ldap.LdapName;

public interface LdapService {
    Person findByPerson(Person person);

    Users loadByLdapUser(Person person);

    void createPerson(Person person);
    void updatePerson(Person person);
    void deletePerson(LdapName dn);

    String convertHexPasswordToString(String hexPassword);
}
