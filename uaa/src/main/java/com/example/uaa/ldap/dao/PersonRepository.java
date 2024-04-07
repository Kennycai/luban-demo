package com.example.uaa.ldap.dao;

import com.example.uaa.ldap.dao.entity.Person;
import org.springframework.data.ldap.repository.LdapRepository;

public interface PersonRepository extends LdapRepository<Person> {
}
