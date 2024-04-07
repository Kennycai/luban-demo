package com.example.uaa.service;

import com.example.uaa.dao.UserRoleRepository;
import com.example.uaa.dao.UsersRepository;
import com.example.uaa.entity.UserRole;
import com.example.uaa.entity.Users;
import com.example.uaa.ldap.dao.PersonRepository;
import com.example.uaa.ldap.dao.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.NameNotFoundException;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Service;

import javax.naming.Name;
import javax.naming.ldap.LdapName;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import static com.example.uaa.entity.Users.USER_TYPE_LDAP;

@Service
public class LdapServiceImpl extends LoginBaseService implements LdapService {
    @Autowired
    LdapTemplate ldapTemplate;
    @Autowired
    PersonRepository personRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public Person findByPerson(Person person) {
//        通过uid查找
        Optional<Person> personOptional = personRepository.findById(person.getId());
        return personOptional.orElse(null);
    }

    @Override
    public Users loadByLdapUser(Person person) {
        Users users = usersRepository.findByPlatformIdAndUserType(String.valueOf(person.getFullName()), USER_TYPE_LDAP);
        if (users == null) {
            users = new Users();
            users.setPlatformId(String.valueOf(person.getFullName()));
            users.setUsername(generateUsername(person.getFullName()));
            users.setUserType(Users.USER_TYPE_LDAP);
            usersRepository.save(users);
            UserRole userRole = new UserRole();
            userRole.setUserId(users.getUserId());
            userRole.setRole(UserRole.ROLE_PRODUCT_ADMIN);
            userRoleRepository.save(userRole);
        }
        return users;
    }

    @Override
    public void createPerson(Person person) {
        Name dn = person.getId();
        try {
            // 检查 DN 是否存在
            ldapTemplate.lookup(dn);
        } catch (NameNotFoundException e) {
            // DN 不存在，自动创建新的 DN
            createMissingDn(dn, person);
        }
        // 保存 Person
        personRepository.save(person);
    }

    private void createMissingDn(Name dn, Person person) {
        try {
            // 逐级创建缺失的DN
            Name parentDn = (Name) dn.clone();
            parentDn.remove(parentDn.size() - 1); // 移除最后一个RDN
            createMissingDn(parentDn, person);

            // 创建当前级别的DN
            DirContextAdapter context = new DirContextAdapter(dn);
            context.setAttributeValues("objectClass", new String[] {"top", "inetOrgPerson"}); // 设置正确的objectClass属性
            context.setAttributeValue("sn", person.getLastName());
            context.setAttributeValue("cn", person.getFullName());
            context.setAttributeValue("uid", person.getFullName());
            context.setAttributeValue("mail", person.getEmail());
            context.setAttributeValue("userPassword", person.getPassword());
            ldapTemplate.bind(context);
        } catch (Exception e) {
            // 处理异常
            e.printStackTrace();
        }
    }

    @Override
    public void updatePerson(Person person) {
        personRepository.save(person);
    }

    @Override
    public void deletePerson(LdapName dn) {
        personRepository.deleteById(dn);
    }

    @Override
    public String convertHexPasswordToString(String hexPassword) {
        String[] hexBytes = hexPassword.split(",");
        byte[] bytes = new byte[hexBytes.length];
        for (int i = 0; i < hexBytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexBytes[i]);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
