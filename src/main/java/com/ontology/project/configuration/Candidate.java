package com.ontology.project.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "candidate")
@Getter @Setter
public class Candidate {

    List<String> dataProperties;
    Map<String, String> objectProperties;

}
