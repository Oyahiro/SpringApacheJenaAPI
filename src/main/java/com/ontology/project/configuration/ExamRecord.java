package com.ontology.project.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "exam-record")
@Getter @Setter
public class ExamRecord {

    List<String> dataProperties;
    Map<String, String> objectProperties;

}
