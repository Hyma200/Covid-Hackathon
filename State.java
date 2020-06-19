package webApplication.pack;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author hyma2
 */
public class State {
    public String name;
    public int confirmed = 0;
    public double confirmedRatio = 0;
    public int recovered = 0;
    public int deaths = 0;
    public int beds = 0;
    public int staff = 0;
    
    public State(){}
    public State (String name){
        this.name = name;
    }
    
    public State (String name, int confirmed, double confirmedRatio, int recovered, int deaths, int beds, int staff){
        this.name = name;
        this.confirmed = confirmed;
        this.confirmedRatio = confirmedRatio;
        this.recovered = recovered;
        this.deaths = deaths;
        this.beds = beds;
        this.staff = staff;
    }
    /**
     * Cost was determined based on guidelines by the CDC.
     * @return double medical cost of nurse's PPE.
     */
    public double getMedicalCost(){
        return staff * 38.4;
    }
    
    /**
     * Around 12% of patients inflicted with covid-19 have serious medical issues.
     * Therefore, ventilator support is necessary.
     * @return the average medical cost of patients with serious need of medical support.
     */
    public double getPatientCost(){
        return confirmed * 0.12 * 88114;
    }
    
    public double getTotalCost(){
        return this.getMedicalCost() + this.getPatientCost();
    }
    
}
