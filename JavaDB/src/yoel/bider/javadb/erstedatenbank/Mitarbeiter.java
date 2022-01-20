package yoel.bider.javadb.erstedatenbank;

import java.time.LocalDate;

public class Mitarbeiter {

    private String gpnnummer, name, vorname, email, abteilung;
    private int alter;
    private LocalDate geburtsdatum;
    private float lohn;
    private int aktiv;

    //ToString
    @Override
    public String toString() {
        return "\nGpn-Nummer: " + gpnnummer +
                "\nName: " + name +
                "\nVorname: " + vorname +
                "\nEmail: " + email +
                "\nAlter: " + alter +
                "\nGeburtsdatum: " + geburtsdatum +
                "\nLohn: CHF " + lohn +
                "\nAktiv: " + aktiv;
    }


    //Getter & Setter

    public void setGpnnummer(String gpnnummer) {
        this.gpnnummer = gpnnummer;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVorname(String vorname) {
        this.vorname = vorname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAlter(int alter) {
        this.alter = alter;
    }

    public void setGeburtsdatum(LocalDate datum){
        this.geburtsdatum = datum;
    }

    public void setLohn(float lohn) {
        this.lohn = lohn;
    }

    public void setAbteilung(String abteilung) {
        this.abteilung = abteilung;
    }

    public void setAktiv(int aktiv) {
        this.aktiv = aktiv;
    }

    public String getGpnnummer() {
        return gpnnummer;
    }

    public String getName() {
        return name;
    }

    public String getAbteilung() {
        return abteilung;
    }

    public String getVorname() {
        return vorname;
    }

    public String getEmail() {
        return email;
    }

    public int getAlter() {
        return alter;
    }

    public LocalDate getGeburtsdatum() {
        return geburtsdatum;
    }

    public float getLohn() {
        return lohn;
    }

    public int getAktiv() {
        return aktiv;
    }
}
