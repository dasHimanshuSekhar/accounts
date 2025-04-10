package com.accounts.iskcon.patia.account.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DbConfig {
    Logger logger = LoggerFactory.getLogger(DbConfig.class);

    @Bean(name = "pgDbConnect")
    @Primary
    public PGSimpleDataSource connectDb(){
        PGSimpleDataSource ds = new PGSimpleDataSource();
        try{
            logger.info("==================== DB Connection Begins =================");

            ds.setURL("jdbc:postgresql://himanshu-demo-db-9994.j77.aws-ap-southeast-1.cockroachlabs.cloud:26257/accounts?sslmode=verify-full");
            ds.setUser("himanshu");
            ds.setPassword("sgzQdPlykz1Gr5xa6JrVKw");

        } catch (Exception e){
            logger.error("DB Configuration :: Exception :: {}", e.getMessage());
        }
        return ds;
    }
}
