package com.ontology.project.emuns;

public enum SparqlClass {
    Candidate("Aspirante"),
    Career("Carrera"),
    Exam("Examen"),
    ExamNote("NotaExamen"),
    ExamRecord("RegistroExamen"),
    Postulation("Postulacion"),
    PostulationResult("ResultadoPostulación"),
    University("Universidad");

    public final String label;

    SparqlClass(String label) {
        this.label = label;
    }

    public static SparqlClass valueOfLabel(String label) {
        for (SparqlClass e : values()) {
            if (e.label.equals(label)) {
                return e;
            }
        }
        return null;
    }
}
