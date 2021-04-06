package com.ontology.project.utils;

import com.ontology.project.configuration.*;
import com.ontology.project.emuns.SparqlClass;
import net.minidev.json.JSONObject;
import org.apache.jena.query.QuerySolution;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Component
public class SparqlUtils {

    private static final String PREFIX = " prj:";

    private final Candidate candidate;
    private final Career career;
    private final Exam exam;
    private final ExamNote examNote;
    private final ExamRecord examRecord;
    private final Postulation postulation;
    private final PostulationResult postulationResult;
    private final University university;

    @Autowired
    public SparqlUtils(Candidate candidate, Career career, Exam exam, ExamNote examNote, ExamRecord examRecord, Postulation postulation, PostulationResult postulationResult, University university) {
        this.candidate = candidate;
        this.career = career;
        this.exam = exam;
        this.examNote = examNote;
        this.examRecord = examRecord;
        this.postulation = postulation;
        this.postulationResult = postulationResult;
        this.university = university;
    }

    public String findAndBuildDataTriplet(String property) {
        String triplet = Strings.EMPTY;
        if(candidate.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.Candidate.label;
        }
        else if(career.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.Career.label;
        }
        else if(exam.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.Exam.label;
        }
        else if(examNote.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.ExamNote.label;
        }
        else if(examRecord.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.ExamRecord.label;
        }
        else if(postulation.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.Postulation.label;
        }
        else if(postulationResult.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.PostulationResult.label;
        }
        else if(university.getDataProperties().stream().anyMatch(dp -> dp.equals(property))){
            triplet += " ?" + SparqlClass.University.label;
        }

        if(Strings.isNotBlank(triplet)) {
            return triplet + PREFIX + property + " ?" + property;
        }
        return Strings.EMPTY;
    }

    public String findAndBuildClassesTriplets(List<String> classes) {
        StringBuilder triplets = new StringBuilder(Strings.EMPTY);
        if(Objects.isNull(classes) || classes.size()<2) {
            return Strings.EMPTY;
        }

        for(int index=0; index<classes.size()-1; index++) {
            String domain = classes.get(index);
            String range = classes.get(index+1);
            SparqlClass domainClass = Objects.requireNonNull(SparqlClass.valueOfLabel(domain));
            triplets.append(" ?").append(domain);
            triplets.append(PREFIX).append(getObjectProperty(domainClass, range));
            triplets.append(" ?").append(range);
            triplets.append(" .");
        }

        return triplets.toString();
    }

    public JSONObject convertToJsonObject(QuerySolution solution, List<String> properties) {
        JSONObject obj = new JSONObject();
        properties.forEach(property -> {
            if(Objects.nonNull(solution.get(property))){
                String value = solution.get(property).toString();
                if(value.contains("^^")) {
                    value = value.split("\\^")[0];
                }
                value = (value.equals("true")) ? "Si" : value;
                value = (value.equals("false")) ? "No" : value;
                obj.putIfAbsent(property, value);
            }
        });
        return obj;
    }

    private String getObjectProperty(SparqlClass domain, String range) {
        switch (domain) {
            case Candidate: return getKey(candidate.getObjectProperties(), range);
            case Career: return getKey(career.getObjectProperties(), range);
            case Exam: return getKey(exam.getObjectProperties(), range);
            case ExamNote: return getKey(examNote.getObjectProperties(), range);
            case ExamRecord: return getKey(examRecord.getObjectProperties(), range);
            case Postulation: return getKey(postulation.getObjectProperties(), range);
            case PostulationResult: return getKey(postulationResult.getObjectProperties(), range);
            case University: return getKey(university.getObjectProperties(), range);
            default: return Strings.EMPTY;
        }
    }

    private String getKey(Map<String, String> properties, String value) {
        for (Map.Entry<String, String> entry : properties.entrySet()) {
            if (entry.getValue().equals(value)) {
                return entry.getKey();
            }
        }
        return null;
    }
}
