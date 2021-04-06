package com.ontology.project.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "university")
@Getter @Setter
public class University {

    List<String> dataProperties;
    Map<String, String> objectProperties;

}
