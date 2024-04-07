package com.example.uaa.service;

import com.example.commonutils.utils.JwtUtils;
import com.example.uaa.dao.AuthoritiesRepository;
import com.example.uaa.dao.UserRoleRepository;
import com.example.uaa.dao.UsersRepository;
import com.example.uaa.dto.GitHubUserDto;
import com.example.uaa.dto.LoginForm;
import com.example.uaa.entity.Authorities;
import com.example.uaa.entity.UserRole;
import com.example.uaa.entity.Users;
import com.example.uaa.exception.AuthorityException;
import com.example.uaa.ldap.dao.PersonRepository;
import com.example.uaa.ldap.dao.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.ldap.LdapName;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService {
    @Value("${jwt.key}")
    private String secretKey;

    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private AuthoritiesRepository authoritiesRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LdapService ldapService;

    @Autowired
    private GitHubService gitHubService;

    @Autowired
    private UserRoleRepository userRoleRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String login(LoginForm loginForm) {
        if (loginForm.getType().equals(LoginForm.Type.LDAP)) {
            // 使用LDAP登录
            return ldapLogin(loginForm);
        } else {
            // 使用表单登录
            return formLogin(loginForm);
        }
    }

    private String ldapLogin(LoginForm loginForm) {
        String username = loginForm.getUsername();
        LdapName dn = LdapNameBuilder.newInstance()
                .add("ou", "people")
                .add("uid", username)
                .build();
        Person person = new Person();
        person.setId(dn);
        person.setFullName(username);
        person.setLastName(username);
        person.setEmail(username + "@example.com");
        person.setPassword(passwordEncoder.encode(loginForm.getPassword()));

        Person byPerson = ldapService.findByPerson(person);
        String passwordInLdap = ldapService.convertHexPasswordToString(byPerson.getPassword());
        if (!passwordEncoder.matches(loginForm.getPassword(), passwordInLdap)) {
            throw new AuthorityException("Invalid password");
        }
        Users users = ldapService.loadByLdapUser(person);
        List<String> authoritiesList = getAuthorities(users);
        return createToken(users, authoritiesList);
    }

    String formLogin(LoginForm loginForm) {
        Users users = usersRepository.findByUsername(loginForm.getUsername());
        if (users == null) {
            throw new AuthorityException("User not found");
        }
        // 校验密码
        if (!passwordEncoder.matches(loginForm.getPassword(), users.getPassword())) {
            throw new AuthorityException("Invalid password");
        }
        List<String> authoritiesList = getAuthorities(users);
        return createToken(users, authoritiesList);
    }

    private List<String> getAuthorities(Users users) {
        UserRole userRole = userRoleRepository.findByUserId(users.getUserId());
        if (userRole != null) {
            return authoritiesRepository.findByRole(userRole.getRole()).stream().map(Authorities::getAuthority).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @Override
    public String githubCallback(String code) {
        String accessToken = gitHubService.getAccessToken(code);
        GitHubUserDto gitHubUserDto = gitHubService.getGitHubUser(accessToken);
        Users users = gitHubService.loadByGitHubUser(gitHubUserDto);
        List<String> authoritiesList = getAuthorities(users);
        return createToken(users, authoritiesList);
    }

    private String createToken(Users users, List<String> authoritiesList) {

        return JwtUtils.createToken(secretKey, users.getUsername(), authoritiesList);
    }
}
