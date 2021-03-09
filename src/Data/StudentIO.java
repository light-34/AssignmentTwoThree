package Data;

import Business.Student;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class StudentIO {
    private static File file = new File("ProgramList.txt");
    private static File bfile = new File("students.dat");
    private static final int REC_SIZE = 42;
    private static final int COURSE_SIZE = 28;

    public static void saveData (Student stdReg) {
        try(RandomAccessFile rdAOut = new RandomAccessFile(bfile,"rw")) {
            //set pointer at the end of the file
            rdAOut.seek(rdAOut.length());

            //Write data from Student class into the binary file
            rdAOut.writeInt(stdReg.getStudentId());
            rdAOut.writeChars((String)stdReg.getProgram());
            rdAOut.writeInt(stdReg.getSemester());
            rdAOut.writeChars(stdReg.getCourses());

        } catch (IOException ex) {
            System.out.println("Error! File is not found");
        }

    }

    public static String displayData () { // I need to add an argument to call recorda according to program
        int stdId = 0;
        int semester = 0;
        StringBuilder strBuilProg = new StringBuilder();
        StringBuilder strBuilCours = new StringBuilder();

        try(RandomAccessFile rdAIn = new RandomAccessFile(bfile,"r")) {
            rdAIn.seek(0);
            while (bfile.length() != -1) {
                stdId = rdAIn.readInt();

                for (int i = 0; i < 3; i++) {
                    char progChar = rdAIn.readChar();
                    strBuilProg.append(progChar);
                }

                semester = rdAIn.readInt();

                for (int i = 0; i < COURSE_SIZE; i++) {
                    char courseChar = rdAIn.readChar();
                    strBuilCours.append(courseChar);
                }
            }

        } catch (EOFException ex) {
            System.out.println("End of the file");
        } catch (IOException ex) {
            System.out.println("Error! File is not found");
        }

        return stdId + " " + strBuilProg.toString() + " " + semester + " " + strBuilCours.toString() + "\n";

    }

    public static void firstRecord (int recNum) throws IOException
    {        	 
    	/*	
    	Student S = new Student();
    		try(RandomAccessFile dIn = new RandomAccessFile(myFile2,"r")) {

    			//Find number of records in a file
    			int numRecs = (int)dIn.length()/REC_SIZE;

    			if (numRecs >= recNum){
    				//Move the file pointer in the begining of rec to read
    				dIn.seek((recNum-1) * REC_SIZE);
    								
    				StringBuilder fname = new StringBuilder();
    				for (int i=0; i<FNAME_SIZE; i++) {
    					char nameChar = dIn.readChar();
    					fname.append(nameChar);				
    				}
    				*/
    }

    public static void previousRecord () {    }

    public static void nextRecord () {    }

    public static void lastRecord () {    }

    public static void updateRecord () {    }





    public static String [] comboBoxLoader() {
        String [] program = new String[5];
        try(Scanner input = new Scanner(file)) {
            int i = 0;
            while (input.hasNext()) {
                program[i] = input.nextLine();
                i++;
            }

        }catch (IOException ex) {
            System.out.println("Error! File not found");
        }
        return program;
    }

    // This method is designed to add ID
    public static int idFinder () {
        int id = 0;
        try (RandomAccessFile ranAccFile = new RandomAccessFile(bfile, "r")) {
            if(bfile.length() == 0) {
                id = 1;
            }
            else {
                id = (int) ranAccFile.length() / REC_SIZE;
            }

        } catch (IOException ex) {
            System.out.println("Error! File not found");
        }

        return id + 1;
    }

}