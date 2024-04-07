package com.example.uaa.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.InitialDirContext;
import java.util.Hashtable;

@Configuration
public class LdapConfig {

    @Bean
    public InitialDirContext initialDirContext() throws NamingException {
        Hashtable<String, String> env = new Hashtable<>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, "ldap://localhost:389");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, "cn=admin,dc=example,dc=com");
        env.put(Context.SECURITY_CREDENTIALS, "654321");
        return new InitialDirContext(env);
    }

    //todo: ldapTemplate
    @Bean
    public ContextSource contextSource() {
        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setUrl("ldap://localhost:389");
        contextSource.setBase("dc=example,dc=com");
        contextSource.setUserDn("cn=admin,dc=example,dc=com");
        contextSource.setPassword("654321");
        contextSource.setPooled(false);
        contextSource.afterPropertiesSet(); // important
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {

        LdapTemplate template = new LdapTemplate(contextSource());
        return template;
    }
}
