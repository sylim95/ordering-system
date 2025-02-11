package com.example.common.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseEncryptConfig {

    /**
     * 설정 파일 내 데이터 소스를 암호화하는 Jasypt 인크립터 생성
     *
     * - `PBEWithMD5AndDES` 알고리즘을 사용하여 암호화
     * - 출력 형식은 `Base64`
     * - 암호화 키(`secret-key`)를 설정하여 보안 강화
     *
     * @return StringEncryptor
     */
    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        var encryptor = new StandardPBEStringEncryptor();
        var config = new SimpleStringPBEConfig();
        config.setPassword("secret-key");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);

        return encryptor;
    }
}
