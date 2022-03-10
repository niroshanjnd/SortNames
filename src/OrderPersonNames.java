import java.io.*;
import java.util.*;

public class OrderPersonNames {

    private static String fileName  = "inputNames";

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
            e.printStackTrace();
        }
        displaySortedNames(personArr);
    }

    private static void displaySortedNames(ArrayList<Person> personArr) {
        Collections.sort(personArr, new OrderNamesByLastAndFirst());

        File outputFile = new File(fileName + "-sorted.txt");

        try {
            if (outputFile.createNewFile()) {
                System.out.println("File created: " + outputFile.getName());
            } else {
                System.out.println("File already exists.");
            }
            writeToOutputFile(personArr, outputFile);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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
