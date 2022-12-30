package com.example.myapplication;

import java.util.ArrayList;
import java.util.List;

public class Personne {

    private int id;

    private String username;

    private int age;


    private enum Genre {M,F}

    private enum BloodPressure {HIGH,LOW,NORMAL}

    private enum Cholesterol {HIGH,NORMAL}

    private double na;

    private double k;

    Genre genre;

    BloodPressure bloodPressure;

    Cholesterol cholesterol;

    private String drug;

    // les moyennes des valeurs numériques
    private static final double Na = 0.6928814897959186;
    private static double K  = 0.05009618367346938;
    private static int moyAge = 44;

    // getters

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public String getGenre() {
        return genre.toString();
    }

    public String getBloodPressure() {
        return bloodPressure.toString();
    }

    public String getCholesterol() {
        return cholesterol.toString();
    }

    public double getNa() {
        return na;
    }

    public double getK() {
        return k;
    }

    public String getDrug() {
        return drug;
    }

    // setters

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setGenre(String genre) {
        this.genre = Genre.valueOf(genre);
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
    }

    public void setCholesterol(String  cholesterol) {
        this.cholesterol = Cholesterol.valueOf(cholesterol);
    }

    public void setK(double k) {
        this.k = k;
    }

    public void setNa(double na) {
        this.na = na;
    }

    public void setDrug(String drug) {
        this.drug = drug;
    }

    // constructors

    public Personne() {}

    public Personne(
            String username,
            int age, String genre, String bloodPressure,
                    String cholesterol, double na , double k , String drug) {
        this.username = username;
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;
        this.drug = drug;
    }

    public Personne(
            int age, String genre, String bloodPressure,
            String cholesterol, double na , double k , String drug) {
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;
        this.drug = drug;
    }

    public Personne(
            int age, String genre, String bloodPressure,
            String cholesterol, double na , double k ) {
        this.age = age;
        this.genre = Genre.valueOf(genre);
        this.bloodPressure = BloodPressure.valueOf(bloodPressure);
        this.cholesterol = Cholesterol.valueOf(cholesterol);
        this.na = na;
        this.k = k;

    }

    @Override
    public String toString() {
        return "Personne{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", na=" + na +
                ", k=" + k +
                ", genre=" + genre +
                ", bloodPressure=" + bloodPressure +
                ", cholesterol=" + cholesterol +
                ", drug='" + drug + '\'' +
                '}';
    }

    List<String> toList(){
        List<String> personneList = new ArrayList<>();
        personneList.add(getAgeCat());
        personneList.add(getGenre());
        personneList.add(getBloodPressure());
        personneList.add(getCholesterol());
        personneList.add(getNaCat());
        personneList.add(getKCat());
//        personneList.add(getDrug());
        return personneList;
    }

    public String getAgeCat(){
        String cat;
        if(this.age > Personne.moyAge)
            cat = "adult";
        else
            cat = "young";
        return cat;
    }

    public String getKCat(){
        String cat;
        if(this.getK() > Personne.K)
            cat = "High";
        else
            cat = "Low";
        return cat;
    }

    public String getNaCat(){
        String cat;
        if(this.getNa() > Personne.Na)
            cat = "High";
        else
            cat = "Low";
        return cat;
    }

    public double distanceEuclidienne(Personne p){
        double distance=0.0;
        int ageCat	= this.getAgeCat().equalsIgnoreCase(p.getAgeCat()) ? 0 : 1; // si la categorie d'age est la même ageCat=0 ,et =1 sinon
        int sexe	= this.getGenre().equalsIgnoreCase(p.getGenre()) ? 0 : 1; // si le sexe est le même sexe=0 ,et =1 sinon
        int bp = 0;

        if(this.getBloodPressure().equalsIgnoreCase(p.getBloodPressure()))
            bp = 0;
        else
        if(this.getBloodPressure().equalsIgnoreCase("NORMAL") || p.getBloodPressure().equalsIgnoreCase("NORMAL"))
            bp = 1;
        else
        if( (p.getBloodPressure().equalsIgnoreCase("LOW") || this.getBloodPressure().equalsIgnoreCase("LOW")) && (p.getBloodPressure().equalsIgnoreCase("HIGH") || this.getBloodPressure().equalsIgnoreCase("HIGH")) )
            bp = 2*2;

        int cholesterol = this.getCholesterol().equalsIgnoreCase(p.getCholesterol()) ? 0 : 1;
        double na = Math.pow(this.getNa() - p.getNa(), 2);
        double k  = Math.pow(this.getK() - p.getK(), 2);

        distance = Math.sqrt(bp + ageCat + sexe + cholesterol + na + k);

        return distance;
    }

    public double distanceManahattan(Personne p){
        double distance=0.0;
        int ageCat	=	this.getAgeCat().equalsIgnoreCase(p.getAgeCat()) ? 0 : 1;
        int sexe	=	this.getGenre().equalsIgnoreCase(p.getGenre()) ? 0 : 1;
        int bp 		= 	0;

        if(this.getBloodPressure().equalsIgnoreCase(p.getBloodPressure()))
            bp = 0;
        else
        if(this.getBloodPressure().equalsIgnoreCase("NORMAL") || p.getBloodPressure().equalsIgnoreCase("NORMAL"))
            bp = 1;
        else
        if( (p.getBloodPressure().equalsIgnoreCase("LOW") || this.getBloodPressure().equalsIgnoreCase("LOW")) && (p.getBloodPressure().equalsIgnoreCase("HIGH") || this.getBloodPressure().equalsIgnoreCase("HIGH")) )
            bp = 2;

        int cholesterol = this.getCholesterol().equalsIgnoreCase(p.getCholesterol()) ? 0 : 1;
        double na 		= Math.abs(this.getNa() - p.getNa());
        double k  		= Math.abs(this.getK() - p.getK());

        distance = bp + ageCat + sexe + cholesterol + na + k;

        return distance;
    }




}
