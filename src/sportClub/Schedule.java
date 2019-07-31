package sportClub;
//potrzebne biblioteki

import java.util.*;
import java.io.*;

public class Schedule implements Window {

    //table which remembers a schedule
    public static String[/* day */][/* block/hour */] schedule = new String[6][6];
    static int day = 0;//remembers chosen day

    public Schedule() {;
    }

    public void showInterface() {

        System.out.println("podaj dzien tygodnia: ");
        System.out.println("pn(1) wt(2) sr(3) czw(4) pt(5) so(6)");
        Scanner reader = new Scanner(System.in);

        String l = "";
        l = reader.nextLine();
        while (l.equals("")) {
            System.out.println("nie zostalo nic podane, sprobuj jeszcze raz: ");
            l = reader.nextLine();
        }

        this.day = (l).charAt(0) - '0';
        while (this.day < 1 || this.day > 6) {
            System.out.println("podales zla wartosc, sprubój jeszcze raz: ");
            l = reader.nextLine();
            while (l.equals("")) {
                System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
                l = reader.nextLine();
            }

            this.day = (l).charAt(0) - '0';
        }

        SportClub.clear();

        System.out.println("znajdujesz sie w: plan zajec grupy " + SportClub.group + " dyscypliniy " + SportClub.discipline);//polozenie i komendy
        System.out.println("WROC(r) EDYTUJ(e) WYJDZ(w)");
        System.out.println("WYBOR(klawisze numeryczne)");

        SportClub.placementOfShownList = 6;
        System.out.println("1: blok 1 8:15-10:00 " + schedule[day - 1][0]);
        System.out.println("2: blok 2 10:15-12:00 " + schedule[day - 1][1]);
        System.out.println("3: blok 3 12:15-14:00 " + schedule[day - 1][2]);
        System.out.println("4: blok 4 14:15-16:00 " + schedule[day - 1][3]);
        System.out.println("5: blok 5 16:15-18:00 " + schedule[day - 1][4]);
        System.out.println("6: blok 6 18:15-20:00 " + schedule[day - 1][5]);
    }

    public void add() throws FileNotFoundException, IOException {
        SportClub.illegalCommand = true;
    }

    public void loadFromFiles() throws FileNotFoundException {
        Scanner reader = new Scanner(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\plan.txt"));

        int day = 0;
        while (day < 6) {
            int block = 0;
            while (block < 6) {
                schedule[day][block] = reader.nextLine();
                block++;
            }
            day++;
        }
        reader.close();

    }

    public void forward(String command) {
        SportClub.illegalCommand = true;
    }

    //back form schedule
    public void back() {
        SportClub.schedule = false;
    }

    public void up() {
        SportClub.illegalCommand = true;
    }

    public void down() {
        SportClub.illegalCommand = true;
    }

    public void tests() {
        SportClub.illegalCommand = true;
    }

    //edits the chosen schedule
    public void edit() throws FileNotFoundException {
        Scanner reader;

        System.out.print("podaj numer bloku do edycji ANULUJ(0): ");
        String number = "";
        reader = new Scanner(System.in);

        number = reader.nextLine();

        while (number.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            number = reader.nextLine();
        }

        while (number.charAt(0) < '0' || number.charAt(0) > '6') {
            System.out.println("nierozpoznana komenda, sprubój jeszcze raz");
            while (number.equals("")) {
                System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
                number = reader.nextLine();
            }

            number = reader.nextLine();
        }
        //if "0" is pressed, the function is canceled
        if (number.equals("0")) {
            return;
        }

        System.out.println("podaj nowe zajecia w tym bloku ANULUJ(0) USUN(u): ");
        reader = new Scanner(System.in);
        String trainingsName = reader.nextLine();

        while (trainingsName.equals("")) {
            System.out.println("nie zostalo nic podane, sprubój jeszcze raz: ");
            trainingsName = reader.nextLine();
        }
        //if "0" is pressed, the function is canceled
        if (trainingsName.equals("0")) {
            return;
        } //if "u" is pressed, the training block is deleted
        else if (trainingsName.equals("u")) {
            trainingsName = "-";
        }

        schedule[this.day - 1][number.charAt(0) - '0' - 1] = trainingsName;
        PrintWriter writer = new PrintWriter(new File("dane\\" + SportClub.discipline + "\\" + SportClub.group + "\\plan.txt"));

        int day = 0;
        while (day < 6) {
            int block = 0;
            while (block < 6) {
                writer.println(schedule[day][block]);
                block++;
            }
            day++;
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
