package com.example.jasypt;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JasyptEncryptorTest {
    @Test
    @DisplayName("프로퍼티 암호화")
    void encryptProperty() {
        PropertiesEncryptor encryptor = new PropertiesEncryptor();
        String encrypted = encryptor.encrypt("");
        System.out.println("### encrypt result - " + encrypted);
        Assertions.assertTrue(true);
    }

    @Test
    @DisplayName("프로퍼티 복호화")
    void decryptProperty() {
        PropertiesEncryptor encryptor = new PropertiesEncryptor();
        String decrypted = encryptor.decrypt("");
        System.out.println("### decrypt result - " + decrypted);
        Assertions.assertTrue(true);
    }

    static class PropertiesEncryptor {
        private final StandardPBEStringEncryptor encryptor;

        public PropertiesEncryptor() {
            StandardPBEStringEncryptor sEncryptor = new StandardPBEStringEncryptor();
            SimpleStringPBEConfig config = new SimpleStringPBEConfig();
            config.setPassword("secret-key");
            config.setAlgorithm("PBEWithMD5AndDES");
            config.setStringOutputType("base64");
            sEncryptor.setConfig(config);

            this.encryptor = sEncryptor;
        }

        public String encrypt(String message) {
            return this.encryptor.encrypt(message);
        }

        public String decrypt(String message) {
            return this.encryptor.decrypt(message);
        }
    }
}