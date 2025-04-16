package com.accounts.iskcon.patia.account.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Configuration
public class DbConfig {

    Logger logger = LoggerFactory.getLogger(DbConfig.class);

    @PostConstruct
    public void downloadCertIfMissing() {
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Path targetDir;

            if (os.contains("win")) {
                String appData = System.getenv("APPDATA");
                targetDir = Paths.get(appData, "postgresql");
            } else {
                String userHome = System.getProperty("user.home");
                targetDir = Paths.get(userHome, ".postgresql");
            }

            Files.createDirectories(targetDir);
            Path certFile = targetDir.resolve("root.crt");

            if (!Files.exists(certFile)) {
                logger.info("Downloading SSL cert...");
                URL url = new URL("https://cockroachlabs.cloud/clusters/6822bad4-a097-40a9-b9ee-633305afd29b/cert");
                try (InputStream in = url.openStream()) {
                    Files.copy(in, certFile, StandardCopyOption.REPLACE_EXISTING);
                    logger.info("Certificate downloaded to: {}", certFile);
                }
            } else {
                logger.info("Certificate already exists at: {}", certFile);
            }

        } catch (Exception e) {
            logger.error("Failed to setup SSL cert: {}", e.getMessage());
        }
    }

    @Bean(name = "pgDbConnect")
    @Primary
    public PGSimpleDataSource connectDb() {
        PGSimpleDataSource ds = new PGSimpleDataSource();
        try {
            logger.info("==================== DB Connection Begins =================");
            ds.setURL("jdbc:postgresql://himanshu-demo-db-9994.j77.aws-ap-southeast-1.cockroachlabs.cloud:26257/accounts?sslmode=verify-full");
            ds.setUser("himanshu");
            ds.setPassword("sgzQdPlykz1Gr5xa6JrVKw");
        } catch (Exception e) {
            logger.error("DB Configuration :: Exception :: {}", e.getMessage());
        }
        return ds;
    }
}
