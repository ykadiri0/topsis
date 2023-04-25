package com.pfa.topsis.Models;


import java.util.List;


public class AnalyseSens {

    private String id;
    private Projet Projet;
    private List<Alternative>  listrank;

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AnalyseSens() {
    }

    public AnalyseSens(com.pfa.topsis.Models.Projet projet, List<Alternative> listrank, String type) {
        Projet = projet;
        this.listrank = listrank;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public com.pfa.topsis.Models.Projet getProjet() {
        return Projet;
    }

    public void setProjet(com.pfa.topsis.Models.Projet projet) {
        Projet = projet;
    }

    public List<Alternative> getListrank() {
        return listrank;
    }

    public void setListrank(List<Alternative> listrank) {
        this.listrank = listrank;
    }
}
