package com.example.uaa;

import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;

@SpringBootTest
class UaaApplicationTests {

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {
        // 从LDAP中获取的十六进制密码字符串
        String hexPasswordFromLdap = "36,50,97,36,49,48,36,106,51,76,73,48,53,70,102,73,100,97,74,82,100,101,70,71,66,105,120,46,117,53,98,47,90,48,118,84,101,53,49,100,49,105,86,68,74,54,88,108,107,46,117,46,89,89,82,49,105,70,52,75";
        String s = convertHexPasswordToString(hexPasswordFromLdap);
        // 将十六进制字符串转换为字节数组
        String[] parts = hexPasswordFromLdap.split(",");
        byte[] hashedPassword = new byte[parts.length];
        for (int i = 0; i < parts.length; i++) {
            hashedPassword[i] = (byte) Integer.parseInt(parts[i]);
        }

        // 使用BCryptPasswordEncoder将字节数组转换为原始密码
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String originalPassword = new String(Hex.encodeHex(hashedPassword));

        System.out.println("原始密码: " + originalPassword);
    }

    public static String convertHexPasswordToString(String hexPassword) {
        String[] hexBytes = hexPassword.split(",");
        byte[] bytes = new byte[hexBytes.length];
        for (int i = 0; i < hexBytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hexBytes[i]);
        }
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
