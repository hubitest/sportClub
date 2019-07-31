package sportClub;

import java.io.*;
import java.util.*;

public class Tests implements Window {

    // sprot efficiency tests
    public List<String> tests = new ArrayList<String>();
    public static int placementOfShownList = 0;

    public Tests() {;
    }

    public void showInterface() {

        SportClub.clear();

        System.out.println("znajdujesz sie w: testach sprawnosciowych dyscypliny " + SportClub.discipline);
        System.out.println("WROC(r) WYJDZ(w) EDYTUJ(e) DODAJ(o) USUN(u) DOL(d) GORA(g)");
        if (tests.size() >= 6) {
            SportClub.placementOfShownList = 6;
            System.out.println("1: " + tests.get(0 + placementOfShownList));
            System.out.println("2: " + tests.get(1 + placementOfShownList));
            System.out.println("3: " + tests.get(2 + placementOfShownList));
            System.out.println("4: " + tests.get(3 + placementOfShownList));
            System.out.println("5: " + tests.get(4 + placementOfShownList));
            System.out.println("6: " + tests.get(5 + placementOfShownList));
        } else if (tests.size() < 6) {
            placementOfShownList = 0;
            SportClub.placementOfShownList = tests.size();
            int i = 0;
            while (i < tests.size()) {
                System.out.println(i + 1 + " " + tests.get(i));
                i++;
            }
        }
    }

    //adds new test to discipline
    public void add() throws FileNotFoundException, IOException {

        Scanner reader_S = new Scanner(System.in);

        System.out.println("podaj nazwe testu ANULUJ(0):");
        String name = reader_S.nextLine();

        while (name.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            name = reader_S.nextLine();
        }
        //if "0" is pressed, the function is canceled 
        if (name.equals("0")) {
            return;
        }

        while (tests.contains(name)) {
            name = name + "I";
        }

        tests.add(name);

        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\testy.txt"));

        int i = 0;
        while (i < tests.size() - 1) {
            writer.println(tests.get(i));
            i++;
        }
        writer.print(tests.get(tests.size() - 1));
        writer.close();

    }

    public void loadFromFiles() throws FileNotFoundException {

        Scanner reader = new Scanner(new File("dane\\" + SportClub.discipline + "\\testy.txt"));
        tests.clear();

        while (2 < 5) {
            try {
                tests.add(reader.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        reader.close();
    }

    public void forward(String command) {
        SportClub.illegalCommand = true;
    }

    //backs form tests
    public void back() {
        SportClub.tests = false;
    }

    // moves up the list
    public void up() {
        if (tests.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList < (tests.size() - 6)) {
            ++placementOfShownList;
        } else // if the shown list can't be moved up anymore 
        {
            SportClub.illegalCommand = true;
        }
    }

    //moves down the list
    public void down() {
        if (tests.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList > 0) {
            --placementOfShownList;
        } else // if the shown list can't be moved down anymore
        {
            SportClub.illegalCommand = true;
        }
    }

    // edits chosen test
    // when name is changed, the marks are deleted
    public void edit() throws FileNotFoundException {
        Scanner reader;

        System.out.print("podaj numer testu do edycji ANULUJ(0): ");
        String number = "";
        reader = new Scanner(System.in);
        number = reader.nextLine();

        while (number.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            number = reader.nextLine();
        }

        if (tests.size() > 6) {
            while (number.charAt(0) < '0' || number.charAt(0) > '6') {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
                    number = reader.nextLine();
                }
            }
        } else if (tests.size() <= 6) {
            while (number.charAt(0) < '0' || (number.charAt(0) > tests.size() + '0')) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
                    number = reader.nextLine();
                }
            }
        }
        //if "0" is pressed, the command is canceled
        if (number.equals("0")) {
            return;
        }

        System.out.println("podaj nowa nazwe testu ANULUJ(0): ");
        reader = new Scanner(System.in);
        String testsName = reader.nextLine();

        while (testsName.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            testsName = reader.nextLine();
        }

        //if "0" is pressed, the command is canceled
        if (testsName.equals("0")) {
            return;
        }

        while (tests.contains(testsName)) {
            testsName = testsName + "I";
        }

        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\testy.txt"));
        int i = 0;

        while (i < number.charAt(0) - '0' - 1 + placementOfShownList) {
            writer.println(tests.get(i));
            i++;
        }

        // writing tests to file until writing to file edited test
        writer.println(testsName);//writing edited test

        i++;
        //writing other tests 
        while (i <= tests.size() - 1) {
            writer.println(tests.get(i));
            i++;
        }

        writer.close();

    }

    public void tests() {
        SportClub.illegalCommand = true;
    }

    public void schedule() {
        SportClub.illegalCommand = true;
    }

    //deletes chosen test
    public void delete() throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);

        if (tests.isEmpty()) {
            System.out.println("w danej dyscyplinie nie ma jeszcze zadnych testow ROZUMIEM(enter)");
            reader.nextLine();
            return;
        }

        System.out.println("wybierz numer testu do usuniecia ANULUJ(0): ");

        String l = "";
        l = reader.nextLine();

        while (l.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            l = reader.nextLine();
        }
        //number - index of the list 
        int number = (l).charAt(0) - '0' - 1 + placementOfShownList;

        if (tests.size() > 6) {
            while (number < -1 + placementOfShownList || number > 5 + placementOfShownList) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nie zostalo nic podane, sprobuj jeszcze raz: ");
                    l = reader.nextLine();
                }
                number = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        } else if (tests.size() <= 6 + placementOfShownList) {
            while (number < -1 || (number > tests.size() - 1)) {
                System.out.println("nierozpoznana komenda, sprobuj jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nie zostalo nic podane, sprobuj jeszcze raz: ");
                    l = reader.nextLine();
                }
                number = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        }
        tests.remove(tests.get(number));

        PrintWriter writer = new PrintWriter("dane\\" + SportClub.discipline + "\\testy.txt");

        int i = 0;
        while (i < tests.size() - 1) {
            writer.println(tests.get(i));
            i++;
        }
        if (tests.size() > 0) {
            writer.print(tests.get(tests.size() - 1));
        }

        writer.close();
        placementOfShownList = 0;
    }
}
