package yoel.bider.javadb.erstedatenbank;

import java.sql.*;

public class Datenbanksynchronisierung {

    Connection con = null;
    String url = "jdbc:mysql://127.0.0.1:3306/mydb";
    String user = "root";
    String password = "";

    public void hinzufuegen(Mitarbeiter m) {

        try {
            con = DriverManager.getConnection(url, user, password);

            Statement stm = con.createStatement();
            stm.executeUpdate("insert into mitarbeiter (GPNNummer, Name, Vorname, Abteilung, Emailadresse, Geburtsdatum, Lohn, Age, Aktiv) values ('" + m.getGpnnummer() + "', '" + m.getName() + "', '" + m.getVorname() + "', '" + m.getAbteilung() + "', '" + m.getEmail() + "', '" + m.getGeburtsdatum() + "', '" + m.getLohn() + "', '" + m.getAlter() + "', '" + m.getAktiv() + "')");
            stm.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void auflisten() {

        try {
            con = DriverManager.getConnection(url, user, password);

            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("select * from mitarbeiter");

            //Anzahl Spalten finden
            int anzahlSpalten = rs.getMetaData().getColumnCount();

            //Neue Linien für Design
            System.out.println();
            //Spaltennamen ausgeben
            for (int j = 1; j <= anzahlSpalten; j++) {
                System.out.print(rs.getMetaData().getColumnLabel(j) + "\t\t\t");
            }
            System.out.println("\n");

            //Ausgabe
            while (rs.next()) {
                for (int i = 1; i <= anzahlSpalten; i++) {
                    String s = rs.getString(i);
                    System.out.print(s);
                    System.out.print("\t\t\t");
                }
                System.out.println();
            }
            System.out.println();

            stm.close();
            rs.close();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public void loeschen(int gpnnummer){

        try {
            con = DriverManager.getConnection(url, user, password);
            Statement stm = con.createStatement();
            stm.executeUpdate("delete from mitarbeiter where GPNNummer='"+ gpnnummer +"'");

            stm.close();
            con.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void editieren(int gpnnummer, Mitarbeiter n){

        try {
            con = DriverManager.getConnection(url, user, password);

            Statement stm = con.createStatement();
            stm.executeUpdate("update mitarbeiter set GPNNummer='"+ n.getGpnnummer()+"', Name='"+ n.getName()+"', Vorname='"+n.getVorname()+"', Abteilung='"+n.getAbteilung()+"', Emailadresse='"+n.getEmail()+"', Geburtsdatum='"+n.getGeburtsdatum()+"', Lohn='"+n.getLohn()+"', Age='"+n.getAlter()+"', Aktiv='"+ n.getAktiv()+"' where GPNNummer='"+ gpnnummer+"'");

            stm.close();
            con.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
