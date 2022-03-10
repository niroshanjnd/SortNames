import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <h1>Program to order person name by lastname and first name!</h1>
 * The OrderPersonNames program reads data from a text file
 * compare last names and first names
 * and writes output to separate text file.
 *
 * @author Janaka Dassanayake
 *
 */
public class OrderPersonNames {

    private static String fileName  = "inputNames";
    private final static Logger LOGGER = Logger.getLogger(OrderPersonNames.class.getName());

    /**
     * This is the main method which makes use of displaySortedNames method.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String[] args) {

        File file = new File(fileName + ".txt");
        ArrayList<Person> personArr = new ArrayList<Person>();

        try {
            Scanner in = new Scanner(file);
            while (in.hasNext()) {
                String lastName = in.next().trim();
                String firstName = in.next().trim();
                personArr.add(new Person(firstName, lastName));
            }
        } catch (FileNotFoundException e) {
            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("Error Log starts here");
            e.printStackTrace();
        }
        displaySortedNames(personArr);
    }

    /**
     * This method is used to display ordered names.
     * @param personArr person array
     * @return nothing.
     */
    private static void displaySortedNames(ArrayList<Person> personArr) {
        Collections.sort(personArr, new OrderNamesByLastAndFirst());
        File outputFile = new File(fileName + "-sorted.txt");

        try {
            if (outputFile.createNewFile()) {
                LOGGER.setLevel(Level.INFO);
                LOGGER.info("File created: " + outputFile.getName());
            } else {
                LOGGER.setLevel(Level.INFO);
                LOGGER.info("File already exists.");
            }
            writeToOutputFile(personArr, outputFile);
            LOGGER.setLevel(Level.INFO);
            LOGGER.info("Successfully wrote to the file.");
        } catch (IOException e) {
            LOGGER.setLevel(Level.SEVERE);
            LOGGER.severe("Error Log starts here");
            e.printStackTrace();
        }
    }

    /**
     * This method is used to write ordered names to the output file.
     * @param personArr person array
     * @param outputFile output file
     * @return nothing.
     */
    private static void writeToOutputFile(ArrayList<Person> personArr, File outputFile) throws IOException {
        FileOutputStream fos = new FileOutputStream(outputFile);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));

        for (Person person : personArr) {
            bw.write(person.lastName + " " + person.getFirstName());
            bw.newLine();
        }
        bw.close();
    }
}

class OrderNamesByLastAndFirst implements Comparator<Person>{

    @Override
    public int compare(Person o1, Person o2) {

        if(o1.getLastName().compareTo(o2.getLastName()) != 0) {
            return o1.getLastName().compareTo(o2.getLastName());
        } else {
            return o1.getFirstName().compareTo(o2.getFirstName());
        }
    }
}
