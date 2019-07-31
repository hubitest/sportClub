package sportClub;

import java.util.*;
import java.io.*;

public class Discipline implements Window {

    public String name;
    public List<String> groups_names = new ArrayList<String>();
    public List<Group> groups_objects = new ArrayList<Group>();
    //where we are in the program
    public static int placementOfShownList = 0;
    public static final int blocksPerWeek = 36;

    public Discipline(String name) {
        this.name = name;
    }

    public void showInterface() {

        SportClub.clear();
        System.out.println("znajdujesz sie w: dyscyplinia " + SportClub.discipline);
        System.out.println("WROC(r) EDYTUJ(e) WYJDZ(w) DODAJ(o) USUN(u) DOL(d) GORA(g) TESTY(t)");
        System.out.println("WYBOR(klawisze numeryczne)");

        if (groups_names.size() >= 6) {
            SportClub.placementOfShownList = 6;
            System.out.println("1: " + groups_names.get(0 + placementOfShownList));
            System.out.println("2: " + groups_names.get(1 + placementOfShownList));
            System.out.println("3: " + groups_names.get(2 + placementOfShownList));
            System.out.println("4: " + groups_names.get(3 + placementOfShownList));
            System.out.println("5: " + groups_names.get(4 + placementOfShownList));
            System.out.println("6: " + groups_names.get(5 + placementOfShownList));
        } else if (groups_names.size() < 6) {
            placementOfShownList = 0;
            SportClub.placementOfShownList = groups_names.size();
            int i = 0;
            while (i < groups_names.size()) {
                System.out.println(i + 1 + " " + groups_names.get(i));
                i++;
            }
        }
    }

    public void add() throws FileNotFoundException, IOException {

        Scanner reader = new Scanner(System.in);
        System.out.println("podaj nazwe grupy ANULUJ(0)");
        String name = reader.nextLine();

        while (name.equals("")) {
            System.out.println("nie zostalo nic wprowadzone, spróbuj jeszcze raz: ");
            name = reader.nextLine();
        }
        //the function is canceled if "0" is pressed
        if (name.equals("0")) {
            return;
        }

        while (groups_names.contains(name)) {
            name = name + "I";
        }

        System.out.println("podaj nazwe trenera ANLULJ(0): ");
        String trainersName = reader.nextLine();

        while (trainersName.equals("")) {
            System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
            trainersName = reader.nextLine();
        }
        //the function is canceled if "0" is pressed 
        if (trainersName.equals("0")) {
            return;
        }

        groups_names.add(name);
        groups_objects.add(new Group(name));

        File folder = new File("dane\\" + SportClub.discipline + "\\" + name);
        folder.mkdir();

        File nowyplik = new File("dane\\" + SportClub.discipline + "\\" + name + "\\plan.txt");
        nowyplik.createNewFile();

        PrintWriter dopliku = new PrintWriter(nowyplik);
        int i = 0;
        while (i < blocksPerWeek) {
            dopliku.println("-");
            i++;
        }
        dopliku.close();

        nowyplik = new File("dane\\" + SportClub.discipline + "\\" + name + "\\trener.txt");
        nowyplik.createNewFile();

        nowyplik = new File("dane\\" + SportClub.discipline + "\\" + name + "\\czlonkowie.txt");
        nowyplik.createNewFile();

        dopliku = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\grupy.txt"));
        i = 0;
        while (i < groups_names.size() - 1) {
            dopliku.println(groups_names.get(i));
            i++;
        }
        dopliku.print(groups_names.get(groups_names.size() - 1));
        dopliku.close();
        dopliku = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + name + "\\trener.txt"));
        dopliku.print(trainersName);
        dopliku.close();
    }

    public void loadFromFiles() throws FileNotFoundException {

        Scanner writer = new Scanner(new File("dane\\" + SportClub.discipline + "\\grupy.txt"));
        while (2 < 5) {
            try {
                groups_names.add(writer.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        int i = 0;

        while (i < groups_names.size()) {
            groups_objects.add(new Group(groups_names.get(i)));
            i++;
        }
        writer.close();
    }

    //goes forward to chosen group
    public void forward(String komenda) {
        SportClub.group = groups_names.get(komenda.charAt(0) - '0' - 1 + placementOfShownList);
    }

    //goes back form discipline
    public void back() {
        SportClub.discipline = "";
    }

    //moves up shown list
    public void up() {
        if (groups_names.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList < (groups_names.size() - 6)) {
            ++placementOfShownList;
        } else {
            SportClub.illegalCommand = true;
        }
    }

    //moves down shown list
    public void down() {
        if (groups_names.size() <= 6) {
            SportClub.illegalCommand = true;
        } else if (placementOfShownList > 0) {
            --placementOfShownList;
        } else {
            SportClub.illegalCommand = true;
        }
    }

    //shows for us tests
    public void tests() {
        SportClub.tests = true;
    }

    public void edit() throws FileNotFoundException {
        Scanner reader;
        System.out.print("podaj numer grupy do edycji ANULUJ(0): ");
        String number = "";
        reader = new Scanner(System.in);
        number = reader.nextLine();

        while (number.equals("")) {
            System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
            number = reader.nextLine();
        }

        if (groups_names.size() > 6) {
            while (number.charAt(0) < '0' || number.charAt(0) > '6') {
                System.out.println("nierozpoznana komenda, spróbuj jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nie zostalo nic wprowadzone, spróbuj jeszcze raz: ");
                    number = reader.nextLine();
                }
            }
        } else if (groups_names.size() <= 6) {
            while (number.charAt(0) < '0' || (number.charAt(0) > groups_names.size() + '0')) {
                System.out.println("nierozpoznana komenda, spróbuj jeszcze raz ANULUJ(0)");
                number = reader.nextLine();
                while (number.equals("")) {
                    System.out.println("nie zostalo nic wprowadzone, spróbuj jeszcze raz: ");
                    number = reader.nextLine();
                }
            }
        }
        //function is canceled when "0" is pressed
        if (number.equals("0")) {
            return;
        }

        System.out.println("podaj nowa nazwe grupy ANULUJ(0): ");
        reader = new Scanner(System.in);
        String groupsName = reader.nextLine();

        while (groupsName.equals("")) {
            System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
            groupsName = reader.nextLine();
        }

        while (groups_names.contains(groupsName)) {
            groupsName = groupsName + "I";
            System.out.println(groupsName);
        }
        //function is canceled if "0" is pressed
        if (groupsName.equals("0")) {
            return;
        }

        System.out.println("podaj nowa nazwe trenera ANULUJ(0): ");
        String trainersName = reader.nextLine();

        while (trainersName.equals("")) {
            System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
            trainersName = reader.nextLine();
        }
        //function is canceled when "0" is pressed
        if (trainersName.equals("0")) {
            return;
        }

        File folder = new File("dane\\" + SportClub.discipline + "\\" + groups_names.get(number.charAt(0) - '0' - 1 + placementOfShownList));
        folder.renameTo(new File("dane\\" + SportClub.discipline + "\\" + groupsName));

        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\grupy.txt"));

        int i = 0;
        while (i < number.charAt(0) - '0' - 1 + placementOfShownList) {
            writer.println(groups_names.get(i));
            i++;
        }

        writer.println(groupsName);

        i++;
        while (i < groups_names.size()) {
            writer.println(groups_names.get(i));
            i++;
        }

        writer.close();

        writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + groupsName + "\\" + "trener.txt"));
        writer.print(trainersName);
        writer.close();
    }

    public void schedule() {
        SportClub.illegalCommand = true;
    }

    //deletes group
    public void delete() throws FileNotFoundException {
        Scanner reader = new Scanner(System.in);

        if (groups_names.isEmpty()) {
            System.out.println("w danej dyscyplinie nie ma jeszcze zadnej grupy ROZUMIEM(enter)");
            reader.nextLine();
            return;
        }

        System.out.println("wybierz numer grupy do usuniecia ANULUJ(0): ");
        String l = "";
        l = reader.nextLine();
        while (l.equals("")) {
            System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
            l = reader.nextLine();
        }
        int number = (l).charAt(0) - '0' - 1 + placementOfShownList;

        if (groups_names.size() > 6) {
            while (number < -1 + placementOfShownList || number > 5 + placementOfShownList) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
                    l = reader.nextLine();
                }
                number = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        } else if (groups_names.size() <= 6 + placementOfShownList) {
            while (number < -1 || (number > groups_names.size() - 1)) {
                System.out.println("nierozpoznana komenda, sprubój jeszcze raz ANULUJ(0)");
                l = reader.nextLine();
                while (l.equals("")) {
                    System.out.println("nie zostalo nic wprowadzone, sprubój jeszcze raz: ");
                    l = reader.nextLine();
                }

                number = (l).charAt(0) - '0' - 1 + placementOfShownList;
            }
        }

        //function is canceled when "0" is pressed
        if (number == -1 + placementOfShownList) {
            return;
        }

        deleteGroup(SportClub.discipline, groups_names.get(number));

        groups_names.remove(groups_names.get(number));

        PrintWriter writer = new PrintWriter("dane\\" + SportClub.discipline + "\\grupy.txt");

        int i = 0;
        while (i < groups_names.size() - 1) {
            writer.println(groups_names.get(i));
            i++;
        }
        if (groups_names.size() > 0) {
            writer.print(groups_names.get(groups_names.size() - 1));
        }

        writer.close();

        placementOfShownList = 0;
    }

    public static void deleteGroup(String folder_dyscyplina, String folder_grupa) throws FileNotFoundException {

        File file;

        List<String> filesList = new ArrayList<String>();

        Scanner writer = new Scanner(new File("dane\\" + folder_dyscyplina + "\\" + folder_grupa + "\\czlonkowie.txt"));
        while (2 < 5) {
            try {
                filesList.add(writer.nextLine());
            } catch (NoSuchElementException e) {
                break;
            }
        }
        writer.close();
        int i = 0;

        while (i < filesList.size()) {
            file = new File("dane\\" + folder_dyscyplina + "\\" + folder_grupa + "\\" + filesList.get(i) + ".txt");
            file.delete();
            i++;
        }

        file = new File("dane\\" + folder_dyscyplina + "\\" + folder_grupa + "\\plan.txt");
        file.delete();
        file = new File("dane\\" + folder_dyscyplina + "\\" + folder_grupa + "\\czlonkowie.txt");
        file.delete();
        file = new File("dane\\" + folder_dyscyplina + "\\" + folder_grupa + "\\trener.txt");
        file.delete();

        file = new File("dane\\" + folder_dyscyplina + "\\" + folder_grupa);
        file.delete();
    }
}
