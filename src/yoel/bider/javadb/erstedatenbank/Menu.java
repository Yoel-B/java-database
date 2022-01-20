package yoel.bider.javadb.erstedatenbank;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Menu {

    //Scanner
    static Scanner scanner;

    public Menu() {
        //Neuer Scanner
        scanner = new Scanner(System.in);
    }


    Datenbanksynchronisierung dbsync = new Datenbanksynchronisierung();


    public void menu() {

        int auswahl;

        do {
            //Titel
            System.out.println("Menu");
            System.out.println("----");

            //Auswahl
            System.out.println("\nFolgende Möglichkeiten bestehen:");
            System.out.println("Mitarbeiter hinzufügen(1)");
            System.out.println("Mitarbeiter editieren(2)");
            System.out.println("Mitarbeiter löschen(3)");
            System.out.println("Alle Mitarbeiter anzeigen(4)");
            System.out.println("Programm beenden(5)");
            System.out.print("\nWas möchtest du ausführen?");
            auswahl = scanner.nextInt();

            scanner.nextLine();

            if (auswahl < 1 || auswahl > 5) {
                System.out.println("Falsche Eingabe!\nBitte erneute Auswahl treffen:");
                auswahl = scanner.nextInt();
            } else if (auswahl == 1) {
                mitarbeiterHinzufuegen();

            } else if (auswahl == 2) {
                mitarbeiterEditieren();

            } else if (auswahl == 3) {
                mitarbeiterLoeschen();

            } else if (auswahl == 4) {
                dbsync.auflisten();
            }

        } while (auswahl != 5);

    }

    public void mitarbeiterHinzufuegen(){

        Mitarbeiter m = new Mitarbeiter();

        //Einlesen
        System.out.println("\nNeuer Mitarbeiter:");

        System.out.print("Gpn-Nummer:");
        m.setGpnnummer(scanner.nextLine());


        System.out.print("Name:");
        m.setName(scanner.nextLine());

        System.out.print("Vorname:");
        m.setVorname(scanner.nextLine());

        System.out.print("Abteilung:");
        m.setAbteilung(scanner.nextLine());

        System.out.print("Email:");
        m.setEmail(scanner.nextLine());

        System.out.print("Alter:");
        m.setAlter(scanner.nextInt());

        System.out.println("Geburtsdatum:");
        String geburtstag = scanner.nextLine();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(geburtstag, dtf);
        date.format(dtf);
        m.setGeburtsdatum(date);



        System.out.print("Lohn:");
        m.setLohn(scanner.nextFloat());

        scanner.nextLine();

        System.out.print("Aktiv in der Arbeit?(Ja = 1/Nein = 0):");
        m.setAktiv(scanner.nextInt());

        //Datenbank synchronisieren
        dbsync.hinzufuegen(m);


    }

    public void mitarbeiterEditieren(){
        int gpnnummer;
        dbsync.auflisten();

        System.out.print("\nGib die GPN-nummer des zu bearbeitenden Mitarbeiter ein:");
        gpnnummer = scanner.nextInt();
        scanner.nextLine();

        Mitarbeiter n = new Mitarbeiter();

        System.out.println("\nNeue Werte:");

        System.out.print("Gpn-Nummer:");
        n.setGpnnummer(scanner.nextLine());


        System.out.print("Name:");
        n.setName(scanner.nextLine());

        System.out.print("Vorname:");
        n.setVorname(scanner.nextLine());

        System.out.print("Abteilung:");
        n.setAbteilung(scanner.nextLine());

        System.out.print("Email:");
        n.setEmail(scanner.nextLine());

        System.out.print("Alter:");
        n.setAlter(scanner.nextInt());

        System.out.println("Geburtsdatum:");
        String geburtstag = scanner.nextLine();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate date = LocalDate.parse(geburtstag, dtf);
        date.format(dtf);
        n.setGeburtsdatum(date);


        System.out.print("Lohn:");
        n.setLohn(scanner.nextFloat());

        scanner.nextLine();

        System.out.print("Aktiv in der Arbeit?(Ja = 1/Nein = 0):");
        n.setAktiv(scanner.nextInt());

        dbsync.editieren(gpnnummer, n);


    }

    public void mitarbeiterLoeschen(){
        int mitarbeiterLoeschen;

        dbsync.auflisten();
        System.out.print("\nGibt die GPN-Nummer des zu löschenden Mitarbeiter ein:");
        mitarbeiterLoeschen = scanner.nextInt();

        dbsync.loeschen(mitarbeiterLoeschen);
    }





}
