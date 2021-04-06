package com.ontology.project.services;

import com.ontology.project.models.StandardRequest;
import com.ontology.project.utils.SparqlUtils;
import net.minidev.json.JSONObject;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.FileManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@Service
public class JenaService {

    private static final Logger LOGGER = Logger.getLogger(JenaService.class.getName());

    private final SparqlUtils sparqlUtils;

    private QueryExecution queryExecution;
    private String ontology = "final_project.owl";

    @Autowired
    public JenaService(SparqlUtils sparqlUtils) {
        this.sparqlUtils = sparqlUtils;
    }

    private ResultSet executeQuery(String queryString) {
        String prefix = "PREFIX prj:<http://www.semanticweb.org/chris/ontologies/2021/3/topicos-especiales-software#> ";
        queryString = prefix + queryString;
        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM, null);
        try {
            InputStream in = FileManager.get().open(ontology);
            try {
                ontoModel.read(in, null);
                Query query = QueryFactory.create(queryString);
                queryExecution = QueryExecutionFactory.create(query, ontoModel);
                return queryExecution.execSelect();
            } catch (Exception e) {
                e.printStackTrace();
            }
            LOGGER.info("Ontology " + ontology + " loaded.");
        } catch (JenaException e) {
            System.err.println("ERROR" + e.getMessage());
            e.printStackTrace();
            System.exit(0);
        }
        return null;
    }

    private String buildQuery(List<String> classes, List<String> objectProperties) {
        List<String> dataTriplets = objectProperties.stream()
                .map(sparqlUtils::findAndBuildDataTriplet)
                .collect(Collectors.toList());

        String query = "SELECT DISTINCT ?";
        query += String.join(" ?", objectProperties);
        query += " WHERE {";
        query += sparqlUtils.findAndBuildClassesTriplets(classes);
        query += String.join(" .", dataTriplets);
        query += " }";
        return query;
    }

    public List<JSONObject> generalExecute(StandardRequest standardRequest) {
        List<JSONObject> list = new ArrayList<>();
        String query = buildQuery(standardRequest.getClasses(), standardRequest.getProperties());
        ResultSet resultSet = executeQuery(query);
        LOGGER.info("Result set obtained with query: " + query);
        while (resultSet.hasNext()) {
            QuerySolution solution = resultSet.nextSolution();
            list.add(sparqlUtils.convertToJsonObject(solution, standardRequest.getProperties()));
        }
        return list;
    }

}
