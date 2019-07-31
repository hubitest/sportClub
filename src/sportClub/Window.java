package sportClub;

import java.io.*;

/*
All classes showes "graphical" interface (in terminal). 
They have to implements Window interface to do this. 
All methods of this interface are used in mehtod main() in class SprotClub 
 */
public interface Window {

    public void showInterface();//shows suitable interface in terminal (depends on where we are in the program)

    public void add() throws FileNotFoundException, IOException;//adds required object to suitable folder

    public void loadFromFiles() throws FileNotFoundException;//loads required object from suitables foles add folders

    public void forward(String command);//goes forward throuh folders tree

    public void back();//goes back from folder (where we are)

    public void up();//moves up the shown list

    public void down();//moves down the shown list

    public void tests();//checks sport efficiency test of chosen discipline 

    public void edit() throws FileNotFoundException;//edits chosen object

    public void schedule();//checks schedule of chosen group

    public void delete() throws FileNotFoundException;//deletes chosen object
}
