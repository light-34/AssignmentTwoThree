package Data;

import Business.Student;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentIO {
    private static File file = new File("ProgramList.txt");
    private static File bfile = new File("students.dat");   
    private static final int REC_SIZE = 42;  //42
    private static final int COURSE_SIZE = 14; //Size of characters in the course NOT IN THE FILE
    public static ArrayList<Student> arrayList = new ArrayList<>();
    private static int pointer = 0; //This will hold location of pointer

    //This method is designed to save data to the file
    public static void saveData (Student stdReg) throws IOException{
        try(DataOutputStream rdAOut = new DataOutputStream((new FileOutputStream(bfile,true)))){
            //Write data from Student class into the binary file
            rdAOut.writeInt(stdReg.getStudentId());
            rdAOut.writeChars((String)stdReg.getProgram());
            rdAOut.writeInt(stdReg.getSemester());

            if (stdReg.getCourses().length() < COURSE_SIZE) {
                StringBuilder crsStrBuild = new StringBuilder(stdReg.getCourses());
                int numChars = COURSE_SIZE - stdReg.getCourses().length();
                crsStrBuild.append("\0".repeat(Math.max(0, numChars))); // This line will append space to get course size
                stdReg.setCourses(crsStrBuild.toString());
            }
            rdAOut.writeChars(stdReg.getCourses());

            //This will renew data in the array list
            arrayList.clear();
        }
    }
    //This method is designed to show data of desired program in to the text area
    public static void displayData () throws IOException {
        int stdId = 0;
        int semester = 0;

        StringBuilder strBuilProg = new StringBuilder(); // this is for getting Program
        StringBuilder strBuilCours = new StringBuilder(); // this is for getting Courses

        try (DataInputStream rdAIn = new DataInputStream(new FileInputStream(bfile)))
        {
            while (bfile.length() != -1) { // Loop for reading file to extract data till the end
                stdId = rdAIn.readInt();

                for (int j = 0; j < 3; j++) {
                    char progChar = rdAIn.readChar();
                    strBuilProg.append(progChar);
                }
                semester = rdAIn.readInt();
                for (int k = 0; k < COURSE_SIZE; k++) {
                    char courseChar = rdAIn.readChar();
                    strBuilCours.append(courseChar);
                }
                // Add data to array list
                arrayList.add(new Student(stdId,strBuilProg.toString(),semester,strBuilCours.toString()));

                //set length of StringBuilder
                strBuilCours.setLength(0);
                strBuilProg.setLength(0);
            }
        }
    }
    //This method will show the first record in the file
    public static String firstRecord () throws IOException{
        try (RandomAccessFile raf = new RandomAccessFile(bfile, "r"))// random access object
        {
        	// begin of file / records
            raf.seek(0); 
            
            // initialize variables 
            int stdId = 0;
            int semester = 0;
            StringBuilder strBuilProg = new StringBuilder();
            StringBuilder strBuilCours = new StringBuilder();
            
            // get student id - simple read int (4 bytes)
            stdId = raf.readInt();
            
            // get program - for loop up to 3 (size of program ie WEB - 6 bytes)  
            // using read char add to string 
            for (int j = 0; j < 3; j++) 
            {
                char progChar = raf.readChar();
                strBuilProg.append(progChar);
            }
            
            // get semester - simple read int (4 bytes)
            semester = raf.readInt();
            
            // get courses - for loop up to Course size (28 bytes)  
            // using read char add to string 
            for (int k = 0; k < COURSE_SIZE; k++) 
            {
                char courseChar = raf.readChar();
                strBuilCours.append(courseChar);
            }
            
            // sets pointer???
            pointer = (int)raf.getFilePointer();
            
            // return string 
            return stdId + "\t" + strBuilProg.toString() + "\t"
                    + semester + "\t" + strBuilCours.toString();
        }
    }
    //This method will show the previous record in the file
    public static String previousRecord () throws IOException{
        try (RandomAccessFile raf = new RandomAccessFile(bfile, "r"))// random access object
        {
            // initialize variables 
        	int stdId = 0; 
            int semester = 0;
            StringBuilder strBuilProg = new StringBuilder();
            StringBuilder strBuilCours = new StringBuilder();

            // if statement to handle no more previous records - pointer cant be less or equl to 0
            if (pointer <= 0) 
            {
                JOptionPane.showMessageDialog(null, "There is no previous data");
                return "NO DATA IS AVAILABLE";
            }           
            
            
            // else statement to handle work with prev records 
            else 
            {
            	// begin of record -> previous 
                raf.seek(pointer - REC_SIZE*2);
                
                // get student id - simple read int (4 bytes)
                stdId = raf.readInt();
                
                // get program - for loop up to program size (3 char - 6 bytes)  
                // using read char add to string 
                for (int j = 0; j < 3; j++) 
                {
                    char progChar = raf.readChar();
                    strBuilProg.append(progChar);
                }
                
                // get semester - simple read int (4 bytes)
                semester = raf.readInt();
                
                // get courses - for loop up to Course size (28 bytes)  
                // using read char add to string 
                for (int k = 0; k < COURSE_SIZE; k++) 
                {
                    char courseChar = raf.readChar();
                    strBuilCours.append(courseChar);
                }
            }
            // sets pointer???
            pointer = (int)raf.getFilePointer();
            
            // returns string 
            return stdId + "\t" + strBuilProg.toString() + "\t"
                    + semester + "\t" + strBuilCours.toString();
        }
    }
    //This method will show the next record in the file
    public static String nextRecord () throws IOException{
        try (RandomAccessFile raf = new RandomAccessFile(bfile, "r"))// random access object
        {
        	// initialize variables 
            int stdId = 0;
            int semester = 0;
            StringBuilder strBuilProg = new StringBuilder();
            StringBuilder strBuilCours = new StringBuilder();
            
            // if statement to handle reaching end of records - no more records
            if (pointer == bfile.length()) 
            {
                JOptionPane.showMessageDialog(null, "This is the end of file");
                return "NO DATA IS AVAILABLE";
            }
            
            // else statement to do work with next record 
            else 
            {
            	// begin of record - next (after we have read on record - begins on next) 
                raf.seek(pointer); 
                
                // gets stud id - simple read int - 4 bytes 
                stdId = raf.readInt();
                
                // get program - for loop up to program size  
                // using read char add to string 
                for (int j = 0; j < 3; j++) 
                {
                    char progChar = raf.readChar();
                    strBuilProg.append(progChar);
                }
                
                // gets semester - simple read int 
                semester = raf.readInt();
                
                // get courses - for loop up to Course size (28 bytes)  
                // using read char add to string 
                for (int k = 0; k < COURSE_SIZE; k++) 
                {
                    char courseChar = raf.readChar();
                    strBuilCours.append(courseChar);
                }
            }
            
            // sets pointer ???
            pointer = (int)raf.getFilePointer();

            // return string 
            return stdId + "\t" + strBuilProg.toString() + "\t"
                    + semester + "\t" + strBuilCours.toString();
        }
    }
    //This method will show last record of the file
    public static String lastRecord () throws IOException{
        try (RandomAccessFile raf = new RandomAccessFile(bfile, "r"))// random access object
        {
        	// begin of last record
            raf.seek(bfile.length() - REC_SIZE); 
            
            // initialize variables 
            int stdId = 0;
            int semester = 0;
            StringBuilder strBuilProg = new StringBuilder();
            StringBuilder strBuilCours = new StringBuilder();
            
            // get student id - simple read int 
            stdId = raf.readInt();
            
            // get program - for loop up to program size 
            // using read char add to string 
            for (int j = 0; j < 3; j++) 
            {
                char progChar = raf.readChar();
                strBuilProg.append(progChar);
            }
            
            // get semester - read int 
            semester = raf.readInt();
            
            // get courses - for loop up to Course size (28 bytes)  
            // using read char add to string 
            for (int k = 0; k < COURSE_SIZE; k++) 
            {
                char courseChar = raf.readChar();
                strBuilCours.append(courseChar);
            }
            
            // set pointer???
            pointer = (int)raf.getFilePointer();
            
            // return string 
            return stdId + "\t" + strBuilProg.toString() + "\t"
                    + semester + "\t" + strBuilCours.toString();
        }
    }
    // This method is designed to update the record of desired
    public static void updateRecord (Student stdReg, int record) throws IOException {
        try(RandomAccessFile rdAOut = new RandomAccessFile(bfile,"rw"))
        {
            //set pointer at the end of the file
            rdAOut.seek((long)record * REC_SIZE - REC_SIZE );

            //Write data from Student class into the binary file
            rdAOut.writeInt(stdReg.getStudentId());
            rdAOut.writeChars((String)stdReg.getProgram());
            rdAOut.writeInt(stdReg.getSemester());

            if (stdReg.getCourses().length() < COURSE_SIZE) {
                StringBuilder crsStrBuild = new StringBuilder(stdReg.getCourses());
                int numChars = COURSE_SIZE - stdReg.getCourses().length();
                crsStrBuild.append("\0".repeat(Math.max(0, numChars)));
                stdReg.setCourses(crsStrBuild.toString());
            }
            rdAOut.writeChars(stdReg.getCourses());

            //This will renew data in the array list
            arrayList.clear();
        }

    }
    // This method id designed to load comboBox from a text file
    public static String [] comboBoxLoader() {
        String [] program = new String[5];
        try(Scanner input = new Scanner(file)) {
            int i = 0;
            while (input.hasNext()) {
                program[i] = input.nextLine();
                i++;
            }

        } catch (IOException ex) {
            System.out.println("Error! File not found");
        }
        return program;
    }

    // This method is designed to add ID automatically when it is called
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
