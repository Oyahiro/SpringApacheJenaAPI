package com.ontology.project.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "exam-note")
@Getter @Setter
public class ExamNote {

    List<String> dataProperties;
    Map<String, String> objectProperties;

}
