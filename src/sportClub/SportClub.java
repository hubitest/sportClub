package sportClub;

import java.io.*;
import java.util.*;

public class SportClub {

    public static boolean running = true;
    public static String command = "";
    public static boolean illegalCommand = false;
    // helps to decide where we are in the program
    public static String discipline = "";
    public static boolean tests = false;
    public static String group = "";
    public static boolean schedule = false;
    public static String client = "";

    //how shown list is moved
    public static int placementOfShownList = 0;

    public static void clear() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {

        Window A = new Menu();
        while (running == true) {

            // calculating where we are in the program
            if (discipline.equals("")) {
                A = new Menu();
            } else {
                if (group.equals("") && tests == false) {
                    A = new Discipline("");
                } else if (tests == true) {
                    A = new Tests();
                } else {
                    if (client.equals("") && schedule == false) {
                        A = new Group("");
                    } else if (schedule == true) {
                        A = new Schedule();
                    } else {
                        A = new Client("");
                    }

                }
            }

            A.loadFromFiles();

            A.showInterface();

            if (illegalCommand == true) {
                System.out.println("podales niedozwolona komende");
                illegalCommand = false;
            }

            System.out.print("linia polecen: ");
            Scanner reader = new Scanner(System.in);
            command = reader.nextLine();

            while (command.equals("")) {
                System.out.println("podaj komende wybierajac litere podana powyzej lub liczbe wskazujaca na odpowiedni element: ");
                command = reader.nextLine();
            }

            //w - closes the program
            if (command.equals("w")) {
                running = false;
            } //goes back to upper folder
            else if (command.equals("r")) {
                A.back();
            } //moves up shown list
            else if (command.equals("g")) {
                A.up();
            } //moves down shown list
            else if (command.equals("d")) {
                A.down();
            }//adds suitable object like discipline, gropu, client, physical efficiency test
            else if (command.equals("o")) {
                A.add();
            }//checks physicla efficiency tests
            else if (command.equals("t")) {
                A.tests();
            }//edits suitable object like discipline, gropu, client, physical efficiency test
            else if (command.equals("e")) {
                A.edit();
            }//checks schedule of chosen group
            else if (command.equals("p")) {
                A.schedule();
            }//deletes suitable object like discipline, gropu, client, physical efficiency test
            else if (command.equals("u")) {
                A.delete();
            }//go forward to chosen folder
            else if ((command.charAt(0) >= '1') && (command.charAt(0) <= ('0' + placementOfShownList))) {
                A.forward(command);
            } else {
                illegalCommand = true;
            }
        }
    }
}
