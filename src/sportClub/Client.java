package sportClub;

import java.util.*;
import java.io.*;

public class Client extends Trainer implements Window {

    int age;
    String gender;
    List<String> marks = new ArrayList<String>();
    //i-th sport efficiency test
    List<String> tests = new ArrayList<String>();

    public static int placementOfShownList = 0;

    public Client(String name) {
        super(name);//Trainers constructor
        this.age = 0;
        this.gender = "m";
    }

    public Client(String name, int age, String gender) {
        super(name);//Trainers constructor
        try {
            this.age = age;
        } catch (InputMismatchException e) {
            this.age = 0;
        }
        if (gender.equals("z") || gender.equals("m")) {
            this.gender = gender;
        } else {
            this.gender = "m";
        }
    }

    public void showInterface() {
        SportClub.clear();

        System.out.println("znajdujesz sie w: osoba " + name + " z grupy " + SportClub.group + " dyscypliny " + SportClub.discipline);//polozenie i komendy
        System.out.println("WROC(r) EDYTUJ_WYNIK(e) WYJDZ(w) DOL(d) GORA(g)");
        System.out.println("WYBOR(klawisze numeryczne)");
        System.out.print("imie i nazwisko: " + name + " | ");
        System.out.print("wiek: ");
        System.out.print(age);
        System.out.print(" | ");
        if (gender.equals("m")) {
            System.out.println("plec: meska");
        } else {
            System.out.println("plec: zenska");
        }

        if (tests.size() >= 6) {
            SportClub.placementOfShownList = 6;
            System.out.println("1: " + tests.get(0 + placementOfShownList) + " " + marks.get(0 + placementOfShownList));
            System.out.println("2: " + tests.get(1 + placementOfShownList) + " " + marks.get(1 + placementOfShownList));
            System.out.println("3: " + tests.get(2 + placementOfShownList) + " " + marks.get(2 + placementOfShownList));
            System.out.println("4: " + tests.get(3 + placementOfShownList) + " " + marks.get(3 + placementOfShownList));
            System.out.println("5: " + tests.get(4 + placementOfShownList) + " " + marks.get(4 + placementOfShownList));
            System.out.println("6: " + tests.get(5 + placementOfShownList) + " " + marks.get(5 + placementOfShownList));
        } else if (tests.size() < 6) {
            placementOfShownList = 0;
            SportClub.placementOfShownList = tests.size();
            int i = 0;
            while (i < tests.size()) {
                System.out.println(i + 1 + " " + tests.get(i) + " " + marks.get(i));
                i++;
            }
        }
    }

    public void add() throws FileNotFoundException, IOException {
        SportClub.illegalCommand = true;
    }

    public void loadFromFiles() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("dane\\" + SportClub.discipline + "\\testy.txt"));
        while (2 < 5) {
            try {
                tests.add(reader.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        reader.close();

        reader = new Scanner(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + SportClub.client + ".txt"));
        List<String> loadedLines = new ArrayList<String>();
        int i = 0;
        while (2 < 5) {
            if (i == 1) {
                age = reader.nextInt();//the second line (i=1) has to be readed as a number
            } else {
                try {
                    loadedLines.add(reader.nextLine());
                } catch (NoSuchElementException e) {
                    break;
                }
            }
            i++;
        }
        reader.close();
        name = loadedLines.get(0);

        gender = loadedLines.get(2);

        i = 0;
        while (i < tests.size()) {
            if (loadedLines.contains(tests.get(i))) {
                int l;
                l = loadedLines.indexOf(tests.get(i));
                marks.add(loadedLines.get(l + 1));
            } else {
                marks.add("brak");
            }
            i++;
        }

        //disciplines which are remembered by loadedLines and there are not exists in testy.txt file, are deleted automaticly
        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + SportClub.client + ".txt"));
        writer.println(name);
        writer.println(age);
        writer.println(gender);
        i = 0;

        int t = 0;
        int o = 0;
        while (t < tests.size()) {
            if (t == o) {
                writer.println(tests.get(t));
                o++;
            } else if (t < o) {
                writer.println(marks.get(o - 1));
                t++;
            }
        }
        writer.close();

    }

    public void forward(String command) {
        SportClub.illegalCommand = true;
    }

    public void back() {
        SportClub.client = "";
    }

    // move up the shown list
    public void up() {
        if (tests.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList < (tests.size() - 6)) {
            ++placementOfShownList;
        } else//if the shown list can't be moved up anymore 
        {
            SportClub.illegalCommand = true;
        }
    }

    //move down the shown list
    public void down() {
        if (tests.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList > 0) {
            --placementOfShownList;
        } else//if the shown list can't be moved down anymore
        {
            SportClub.illegalCommand = true;
        }
    }

    public void tests() {
        SportClub.illegalCommand = true;
    }

    public void edit() throws FileNotFoundException {
        Scanner reader;

        System.out.print("podaj numer oceny do edycji ANULUJ(0): ");
        String number = "";
        reader = new Scanner(System.in);
        number = reader.nextLine();

        while (number.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            number = reader.nextLine();//wczytanie komendy z klawiatury
        }

        if (tests.size() > 6) {
            while (number.charAt(0) < '0' || number.charAt(0) > '6') {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
            }//... i w przypadku jesli jest mniej niz 6 testow sprawnosciowych 
        } else if (tests.size() <= 6) {
            while (number.charAt(0) < '0' || (number.charAt(0) > tests.size() + '0')) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
            }
        }

        if (number.equals("0")) {
            return;
        }

        System.out.println("podaj nowa ocene ANULUJ(0): ");
        reader = new Scanner(System.in);
        String newMark = reader.nextLine();

        while (newMark.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            newMark = reader.nextLine();//wczytanie komendy z klawiatury
        }
        //if "0" is pressed, the function is canceled
        if (newMark.equals("0")) {
            return;
        }

        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + SportClub.client + ".txt"));

        writer.println(name);
        writer.println(age);
        writer.println(gender);

        int t = 0;
        int o = 0;
        while (o < number.charAt(0) - '0' + placementOfShownList) {
            if (t == o) {
                writer.println(tests.get(t));
                o++;
            } else if (t < o) {
                writer.println(marks.get(o - 1));
                t++;
            }
        }

        writer.println(newMark);
        t++;

        while (t < tests.size()) {
            if (t == o) {
                writer.println(tests.get(t));
                o++;
            } else if (t < o) {
                writer.println(marks.get(o - 1));
                t++;
            }
        }

        writer.close();
    }

    public void schedule() {
        SportClub.illegalCommand = true;
    }

    public void delete() throws FileNotFoundException {
        SportClub.illegalCommand = true;
    }
}
