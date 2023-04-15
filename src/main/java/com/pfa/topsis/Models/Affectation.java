package com.pfa.topsis.Models;

public class Affectation {

    private String id;

    private Projet projet;
    private Users users;

    public Affectation() {
    }

    public Affectation(Projet projet, Users users) {
        this.projet = projet;
        this.users = users;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Projet getProjet() {
        return projet;
    }

    public void setProjet(Projet projet) {
        this.projet = projet;
    }

    public Users getUsers() {
        return users;
    }

    public void setUsers(Users users) {
        this.users = users;
    }
}
