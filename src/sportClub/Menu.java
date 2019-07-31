package sportClub;

import java.io.*;
import java.util.*;

public class Menu implements Window {

    public List<String> disciplines_names = new ArrayList<String>();
    public List<Discipline> disciplines_objects = new ArrayList<Discipline>();
    public static int placementOfShownList = 0;

    public void loadFromFiles() throws FileNotFoundException {
        Scanner writer = new Scanner(new File("dane\\dyscypliny.txt"));

        while (2 < 5) {
            try {
                disciplines_names.add(writer.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }

        int i = 0;
        while (i < disciplines_names.size()) {
            disciplines_objects.add(new Discipline(disciplines_names.get(i)));
            i++;
        }
        writer.close();
    }

    public void showInterface() {
        SportClub.clear();

        System.out.println("znajdujesz sie w: menu glowne");
        System.out.println("WYJDZ(w) EDYTUJ(e) DODAJ(o) USUN(u) DOL(d) GORA(g)");
        System.out.println("WYBOR(klawisze numeryczne)");

        if (disciplines_names.size() >= 6) {
            SportClub.placementOfShownList = 6;
            System.out.println("1: " + disciplines_names.get(0 + placementOfShownList));
            System.out.println("2: " + disciplines_names.get(1 + placementOfShownList));
            System.out.println("3: " + disciplines_names.get(2 + placementOfShownList));
            System.out.println("4: " + disciplines_names.get(3 + placementOfShownList));
            System.out.println("5: " + disciplines_names.get(4 + placementOfShownList));
            System.out.println("6: " + disciplines_names.get(5 + placementOfShownList));
        } else if (disciplines_names.size() < 6) {
            placementOfShownList = 0;
            SportClub.placementOfShownList = disciplines_names.size();
            int i = 0;
            while (i < disciplines_names.size()) {
                System.out.println(i + 1 + " " + disciplines_names.get(i));
                i++;
            }
        }

    }

    public void add() throws FileNotFoundException, IOException {
        Scanner reader = new Scanner(System.in);

        System.out.println("podaj nazwe dyscypliny [dyscyplina, ktora juz istnieje nie zostanie dodana] ANULUJ(0)");
        String name = reader.nextLine();

        while (name.equals("")) {
            System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
            name = reader.nextLine();
        }
        //if "0" is pressed or discipline already exists, the function is canceled 
        if (disciplines_names.contains(name) || name.equals("0")) {
            return;
        }

        disciplines_names.add(name);
        disciplines_objects.add(new Discipline(name));

        File folder = new File("dane\\" + name);
        folder.mkdir();

        File newFile = new File("dane\\" + name + "\\testy.txt");
        newFile.createNewFile();
        newFile = new File("dane\\" + name + "\\grupy.txt");
        newFile.createNewFile();

        PrintWriter writer = new PrintWriter(new File("dane\\dyscypliny.txt"));
        int i = 0;
        while (i < disciplines_names.size() - 1) {
            writer.println(disciplines_names.get(i));
            i++;
        }
        writer.print(disciplines_names.get(disciplines_names.size() - 1));
        writer.close();
    }

    public void forward(String command) {
        SportClub.discipline = disciplines_names.get(command.charAt(0) - '0' - 1 + placementOfShownList);
    }

    public void back() {
        SportClub.illegalCommand = true;
    }

    //moves up the shown list
    public void up() {

        if (disciplines_names.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList < (disciplines_names.size() - 6)) {
            ++placementOfShownList;
        } else//if the shown list can't be moved up anymore
        {
            SportClub.illegalCommand = true;
        }
    }

    //moves down the shown list
    public void down() {

        if (disciplines_names.size() <= 6) {
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
        System.out.print("podaj numer dyscypliny do edycji ANULUJ(0): ");
        String number = "";
        reader = new Scanner(System.in);
        number = reader.nextLine();

        while (number.equals("")) {
            System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
            number = reader.nextLine();
        }

        if (disciplines_names.size() > 6) {
            while (number.charAt(0) < '0' || number.charAt(0) > '6') {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
                    number = reader.nextLine();
                }
            }
        } else if (disciplines_names.size() <= 6) {
            while (number.charAt(0) < '0' || (number.charAt(0) > disciplines_names.size() + '0')) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
                    number = reader.nextLine();
                }
            }
        }
        //if "0" is pressed, the function is canceled
        if (number.equals("0")) {
            return;
        }

        System.out.println("podaj nowa nazwe dyscypliny [gdy nowa dyscyplina juz istnieje, nie zostanie dokonana edycja] ANULUJ(0): ");
        reader = new Scanner(System.in);
        String disciplinesName = reader.nextLine();

        while (disciplinesName.equals("")) {
            System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
            disciplinesName = reader.nextLine();
        }
        //if "0" is pressed or discipline already exists, the function is canceled
        if (disciplines_names.contains(disciplinesName) || disciplinesName.equals("0")) {
            return;
        }

        File folder = new File("dane\\" + disciplines_names.get(number.charAt(0) - '0' - 1 + placementOfShownList));
        folder.renameTo(new File("dane\\" + disciplinesName));

        PrintWriter writer = new PrintWriter(new File("dane\\dyscypliny.txt"));
        int i = 0;

        while (i < number.charAt(0) - '0' - 1 + placementOfShownList) {
            writer.println(disciplines_names.get(i));
            i++;
        }
        writer.println(disciplinesName);

        i++;
        while (i < disciplines_names.size()) {
            writer.println(disciplines_names.get(i));
            i++;
        }
        writer.close();

    }

    public void schedule() {
        SportClub.illegalCommand = true;
    }

    public void delete() throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);

        if (disciplines_names.isEmpty()) {
            System.out.println("w bazie danych nie ma jeszcze zadnej dyscypliny ROZUMIEM(enter)");
            reader.nextLine();
            return;
        }

        System.out.println("wybierz numer dyscypliny do usuniecia ANULUJ(0): ");

        String l = "";
        l = reader.nextLine();
        while (l.equals("")) {
            System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
            l = reader.nextLine();
        }
        //index of disciplines_names list
        int number = (l).charAt(0) - '0' - 1 + placementOfShownList;

        if (disciplines_names.size() > 6) {
            while (number < -1 + placementOfShownList || number > 5 + placementOfShownList) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
                    l = reader.nextLine();
                }
                number = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        } else if (disciplines_names.size() <= 6 + placementOfShownList) {
            while (number < -1 || (number > disciplines_names.size() - 1)) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nic nie zostalo wprowadzone, sprubój jeszcze raz: ");
                    l = reader.nextLine();
                }
                number = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        }

        //if "0" is pressed, the function is canceled
        if (number == -1 + placementOfShownList) {
            return;
        }
        //folder of chosen discipline is deleted
        delete_discipline(disciplines_names.get(number));

        disciplines_names.remove(disciplines_names.get(number));

        PrintWriter writer = new PrintWriter("dane\\dyscypliny.txt");

        int i = 0;
        while (i < disciplines_names.size() - 1) {
            writer.println(disciplines_names.get(i));
            i++;
        }
        if (disciplines_names.size() > 0) {
            writer.print(disciplines_names.get(disciplines_names.size() - 1));
        }

        writer.close();
        placementOfShownList = 0;
    }

    //empties and deletes disciplines folder
    public static void delete_discipline(String folder_discipline) throws FileNotFoundException {
        File plik;

        List<String> listOfFolders = new ArrayList<String>();

        Scanner reader = new Scanner(new File("dane\\" + folder_discipline + "\\grupy.txt"));
        while (2 < 5) {
            try {
                listOfFolders.add(reader.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        reader.close();

        int i = 0;
        while (i < listOfFolders.size()) {

            Discipline.deleteGroup(folder_discipline, listOfFolders.get(i));
            i++;
        }

        plik = new File("dane\\" + folder_discipline + "\\grupy.txt");
        plik.delete();
        plik = new File("dane\\" + folder_discipline + "\\testy.txt");
        plik.delete();
        // this folder is already empty
        plik = new File("dane\\" + folder_discipline);
        plik.delete();
    }
}
