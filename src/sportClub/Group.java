package sportClub;

import java.util.*;
import java.io.*;

public class Group implements Window {

    public String name;

    public String trainersName;

    public List<String> clients_names = new ArrayList<String>();

    public List<Client> clients_objects = new ArrayList<Client>();

    public static int placementOfShownList = 0;

    public Group(String nazwa) {
        this.name = nazwa;
    }

    public void showInterface() {
        SportClub.clear();

        System.out.println("znajdujesz sie w: grupa " + SportClub.group + " dyscypliny " + SportClub.discipline);//polozenie i komendy
        System.out.println("WROC(r) EDYTUJ(e) WYJDZ(w) DODAJ(o) USUN(u) DOL(d) GORA(g) PLAN(p)");
        System.out.println("WYBOR(klawisze numeryczne)");
        System.out.println("trener: " + trainersName);

        if (clients_names.size() >= 6) {
            SportClub.placementOfShownList = 6;
            System.out.println("1: " + clients_names.get(0 + placementOfShownList));
            System.out.println("2: " + clients_names.get(1 + placementOfShownList));
            System.out.println("3: " + clients_names.get(2 + placementOfShownList));
            System.out.println("4: " + clients_names.get(3 + placementOfShownList));
            System.out.println("5: " + clients_names.get(4 + placementOfShownList));
            System.out.println("6: " + clients_names.get(5 + placementOfShownList));
        } else if (clients_names.size() < 6) {
            placementOfShownList = 0;
            SportClub.placementOfShownList = clients_names.size();
            int i = 0;
            while (i < clients_names.size()) {
                System.out.println(i + 1 + " " + clients_names.get(i));
                i++;
            }
        }
    }

    public void add() throws FileNotFoundException, IOException {
        //reads Strings
        Scanner reader_S = new Scanner(System.in);
        //reads numbers
        Scanner reader_i = new Scanner(System.in);

        System.out.println("podaj imie i nazwisko osoby ANULUJ(0):");
        String name = reader_S.nextLine();

        while (name.equals("")) {
            System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
            name = reader_S.nextLine();//wczytanie komendy z klawiatury
        }
        //function is canceled if "0" is pressed
        if (name.equals("0")) {
            return;
        }

        System.out.println("podaj wiek osoby ANULUJ(0):");

        boolean isANumber = false;
        int age = 0;
        while (isANumber == false) {
            isANumber = true;
            try {
                age = reader_i.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("podawana wartosc musi byc w postaci liczby, sprubój jeszcze raz ANULUJ(0): ");
                isANumber = false;
                reader_i.nextLine();//bez tego petla jest nieskonczona, bo w strumieniu jest liczba int
            }
        }
        //we don't have to check nextInt 
        //function is canceled if "0" is pressed
        if (age == 0) {
            return;
        }

        System.out.println("podaj plec [ meska(m), zenska(z)] ANULUJ(0): ");
        String gender = "";
        //sprawdza czy podalismy cokolwiek, plec, lub anulowlismy komende

        boolean isGender = false;
        while (isGender == false) {
            isGender = true;
            gender = reader_S.nextLine();
            System.out.println(gender);
            if ((!gender.equals("m")) && (!gender.equals("z")) && (!gender.equals("0"))) {
                System.out.println("nierozpoznana nazwa plci, spróbuj jeszcze raz [ meska(m), zenska(z)] ANULUJ(0): ");
                isGender = false;
            }
        }
        //the function is caneled when "0" is pressed
        if (gender.equals("0")) {
            return;
        }

        if (clients_names.contains(name)) {
            System.out.println("osoba już istnieje. Podaj ceche charakterystyczna osoby");
            name = name + "(" + reader_S.nextLine() + ")";
        }

        while (clients_names.contains(name)) {
            name = name + "I";
        }

        clients_names.add(name);

        clients_objects.add(new Client(name, age, gender));

        File newFile = new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + name + ".txt");
        newFile.createNewFile();

        PrintWriter writer = new PrintWriter(newFile);
        writer.println(name);
        writer.println(age);
        writer.print(gender);
        writer.close();

        writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\czlonkowie.txt"));

        int i = 0;
        while (i < clients_names.size() - 1) {
            writer.println(clients_names.get(i));
            i++;
        }
        writer.print(clients_names.get(clients_names.size() - 1));
        writer.close();

    }

    public void loadFromFiles() throws FileNotFoundException {
        Scanner reader = new Scanner(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\czlonkowie.txt"));

        while (2 < 5) {
            try {
                clients_names.add(reader.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        reader.close();

        reader = new Scanner(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\trener.txt"));
        trainersName = reader.nextLine();
        reader.close();
        int i = 0;
        File file_clients;

        while (i < clients_names.size()) {
            file_clients = new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + clients_names.get(i) + ".txt");
            Scanner reader_clients = new Scanner(file_clients);
            String client_name = reader_clients.nextLine();
            int client_age = (reader_clients.nextLine()).charAt(0) - '0';
            String client_gender = reader_clients.nextLine();
            clients_objects.add(new Client(client_name, client_age, client_gender));
            reader_clients.close();
            i++;
        }
        reader.close();

    }

    //goes forward to chosen client
    public void forward(String command) {
        SportClub.client = clients_names.get(command.charAt(0) - '0' - 1 + placementOfShownList);
    }

    //goes back to upper folder
    public void back() {
        SportClub.group = "";
    }

    //moves up the shown list
    public void up() {

        if (clients_names.size() <= 6) {
            SportClub.illegalCommand = true;//gwarantuje nam ze wyswietli nam sie napis "podales niedozwolona komende"
        } else if (placementOfShownList < (clients_names.size() - 6)) {
            ++placementOfShownList;
        } else//jak jestesmy na szczycie listy to command sie nie wykona
        {
            SportClub.illegalCommand = true;
        }
    }

    //moves down the shown list
    public void down() {

        if (clients_names.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList > 0) {
            --placementOfShownList;
        } else {
            SportClub.illegalCommand = true;
        }
    }

    public void tests() {
        SportClub.illegalCommand = true;
    }

    //edits the chosen client
    public void edit() throws FileNotFoundException {
        Scanner reader;

        System.out.print("podaj numer osoby do edycji ANULUJ(0): ");
        String number = "";
        reader = new Scanner(System.in);
        number = reader.nextLine();

        while (number.equals("")) {
            System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
            number = reader.nextLine();//wczytanie komendy z klawiatury
        }

        if (clients_names.size() > 6) {
            while (number.charAt(0) < '0' || number.charAt(0) > '6') {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
                    number = reader.nextLine();//wczytanie komendy z klawiatury
                }
            }
        } else if (clients_names.size() <= 6) {
            while (number.charAt(0) < '0' || (number.charAt(0) > clients_names.size() + '0')) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
                    number = reader.nextLine();//wczytanie komendy z klawiatury
                }
            }
        }
        //if "0" is pressed, the function is canceled
        if (number.equals("0")) {
            return;
        }

        System.out.println("podaj nowa nazwe osoby ANULUJ(0): ");
        reader = new Scanner(System.in);
        String name_client = reader.nextLine();

        while (name_client.equals("")) {
            System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
            name_client = reader.nextLine();//wczytanie komendy z klawiatury
        }
        //if "0" is pressed, the function is canceled
        if (name_client.equals("0")) {
            return;
        }

        if (clients_names.contains(name_client)) {
            System.out.println("osoba już istnieje. Podaj ceche charakterystyczna osoby");
            name_client = name_client + "(" + reader.nextLine() + ")";
        }

        while (clients_names.contains(name_client)) {
            name_client = name_client + "I";
        }

        System.out.println("podaj nowy wiek osoby: ANULUJ(0)");
        Scanner reader_i = new Scanner(System.in);

        int age_clients = 0;
        boolean isANumber = false;
        while (isANumber == false) {
            isANumber = true;
            try {
                age_clients = reader_i.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("wiek nalezy podac w postaci liczby, sprubój jeszcze raz ANULUJ(0):");
                reader_i.nextLine();// it has to be here, because loob would be infinite
                isANumber = false;
            }
        }
        //if "0" is pressed, the function is canceled
        if (age_clients == 0) {
            return;
        }

        System.out.println("podaj nowa plec osoby [(m) lub (z)]: ANULUJ(0)");
        String gender_client = reader.nextLine();

        while (!gender_client.equals("m") && !gender_client.equals("z") && !gender_client.equals("0")) {
            System.out.println("musisz podac litere (m) lub (z) ANULUJ(0):");
            gender_client = reader.nextLine();
        }
        //if "0" is pressed, the function is canceled
        if (gender_client.equals("0")) {
            return;
        }

        File file = new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + clients_names.get(number.charAt(0) - '0' - 1 + placementOfShownList) + ".txt");
        file.renameTo(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + name_client + ".txt"));

        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\czlonkowie.txt"));

        int i = 0;
        while (i < number.charAt(0) - '0' - 1 + placementOfShownList) {
            writer.println(clients_names.get(i));
            i++;
        }

        writer.println(name_client);

        i++;
        while (i < clients_names.size()) {

            writer.println(clients_names.get(i));
            i++;
        }

        writer.close();

    }

    //shows us the schedule of group
    public void schedule() {
        SportClub.schedule = true;
    }

    //deletes chosen client form group
    public void delete() throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);

        if (clients_names.isEmpty()) {
            System.out.println("w danej grupie nie ma jeszcze zadnych osob ROZUMIEM(enter)");
            reader.nextLine();
            return;
        }

        System.out.println("wybierz numer osoby do usuniecia ANULUJ(0): ");

        String l = "";
        l = reader.nextLine();
        while (l.equals("")) {
            System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
            l = reader.nextLine();//wczytanie komendy z klawiatury
        }

        int numer = (l).charAt(0) - '0' - 1 + placementOfShownList;

        if (clients_names.size() > 6) {
            while (numer < -1 + placementOfShownList || numer > 5 + placementOfShownList) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
                    l = reader.nextLine();//wczytanie komendy z klawiatury
                }
                numer = (l).charAt(0) - '0' - 1 + placementOfShownList;

            }
        } else if (clients_names.size() <= 6 + placementOfShownList) {
            while (numer < -1 || (numer > clients_names.size() - 1)) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nie zostalo niec podane, sprubój jeszcze raz: ");
                    l = reader.nextLine();//wczytanie komendy z klawiatury
                }
                numer = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        }

        //if "0" is pressed, the function is canceled
        if (numer == -1 + placementOfShownList) {
            return;
        }

        File plik = new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\" + clients_names.get(numer) + ".txt");
        plik.delete();
        clients_names.remove(clients_names.get(numer));

        PrintWriter writer = new PrintWriter("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\czlonkowie.txt");

        int i = 0;
        while (i < clients_names.size() - 1) {
            writer.println(clients_names.get(i));
            System.out.println("wchodze " + clients_names);
            i++;
        }
        if (clients_names.size() > 0) {
            writer.print(clients_names.get(clients_names.size() - 1));
        }

        writer.close();
        placementOfShownList = 0;
    }
}
