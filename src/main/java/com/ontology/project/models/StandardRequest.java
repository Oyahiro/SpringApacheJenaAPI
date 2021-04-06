package com.ontology.project.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StandardRequest {

    List<String> classes;
    List<String> properties;

}
