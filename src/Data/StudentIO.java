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
    private static final int REC_SIZE = 32; // was 42 before *Tim 32
    private static final int COURSE_SIZE = 14;  // was 28 before   *Tim 14
    //private static final int STUD_ID_SIZE = 4;
    
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

    public static Student firstRecord (int recNum) throws IOException
    {        	 
    	Student S = new Student();
    	try(RandomAccessFile dIn = new RandomAccessFile(bfile,"r")) {
    		//Find number of records in a file
			int numRecs = (int)dIn.length()/REC_SIZE;

			if (numRecs >= recNum){
				//Move the file pointer in the beginning of rec to read
				dIn.seek((recNum-1) * REC_SIZE);
				
				
				int Stud_ID = dIn.readInt(); // not reading all numbers 
				/*
				int Stud_ID = 0;
				for (int i=0; i< STUD_ID_SIZE; i++) {
					Stud_ID = dIn.readInt();
				}
				*/
					
				
				
				StringBuilder Prog = new StringBuilder();
				for (int i=0; i<3; i++) {
					char nameChar = dIn.readChar();
					Prog.append(nameChar);				
				}
				
				int Sem = dIn.readInt();
				
				StringBuilder Courses = new StringBuilder();
				for (int i=0; i<COURSE_SIZE; i++) {
					char nameChar = dIn.readChar();
					Courses.append(nameChar);				
				}
				
				S.setStudentId(Stud_ID);
				S.setProgram(Prog.toString());
				S.setSemester(Sem);
				S.setCourses(Courses.toString());	
				return S;
			}
			else
				throw new IOException("Invalid - No records found");				
		}
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