package com.pfa.topsis.Models;

import com.pfa.topsis.Fuzzy.CostFuzzy;



public class SousCritereAlternative {

    private String id;
    private SousCritere sousCritere;
    private Alternative alternative;
    private Users users;

    private CostFuzzy poids;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public SousCritere getSousCritere() {
        return sousCritere;
    }

    public void setSousCritere(SousCritere sousCritere) {
        this.sousCritere = sousCritere;
    }

    public Alternative getAlternative() {
        return alternative;
    }

    public void setAlternative(Alternative alternative) {
        this.alternative = alternative;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }

    public CostFuzzy getCostFuzzy() {
        return poids;
    }

    public void setCostFuzzy(CostFuzzy poids) {
        this.poids = poids;
    }
}
