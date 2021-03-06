package cn.az.code;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchRepositoriesAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author az
 * @version 2020-03-07
 */
@SpringBootApplication(exclude = {ElasticsearchDataAutoConfiguration.class, ElasticsearchRepositoriesAutoConfiguration.class})
@ServletComponentScan(basePackages = {"cn.az.code.servlet"})
@MapperScan(basePackages = "cn.az.code.mapper")
public class LittleJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(LittleJavaApplication.class, args);
    }

}
