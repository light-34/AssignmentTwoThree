package Data;


import Business.Student;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentIO {
    private static File file = new File("ProgramList.txt");
    private static File bfile = new File("students.dat");
    private static File cfile = new File("numRecs.txt");
    private static final int REC_SIZE = 42;  
    private static final int COURSE_SIZE = 14; 
    public static ArrayList<Student> arrayList = new ArrayList<>(); //displayData and updateData will use this list
    
    //This method is designed by *****CEZMI******
    //This method is designed to save data in the file
    public static void saveData (Student stdReg) {
    	try(DataOutputStream rdAOut = new DataOutputStream((new FileOutputStream(bfile,true)))) {

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

        } catch (EOFException ex) {
            System.out.println("End of the file");
        } catch (IOException ex) {
            System.out.println("Error! File is not found");
        }

    }
    
    //This method is used to see all data in text area 
    //This method is designed by *****CEZMI******
    public static void displayData () { 
        int stdId = 0;
        int semester = 0;
        StringBuilder strBuilProg = new StringBuilder();
        StringBuilder strBuilCours = new StringBuilder();

        try (DataInputStream rdAIn = new DataInputStream(new FileInputStream(bfile))) {
            
            //This loop is used to real data till the end of the file
            while (bfile.length() != -1) {
                stdId = rdAIn.readInt(); //Reads Student ID
             
                for (int j = 0; j < 3; j++) { // Reads Program
                    char progChar = rdAIn.readChar();
                    strBuilProg.append(progChar);
                }
                
                semester = rdAIn.readInt();//Reads Semester

                for (int k = 0; k < COURSE_SIZE; k++) { //Reads Courses
                    char courseChar = rdAIn.readChar();
                    strBuilCours.append(courseChar);
                }
               
                //Adds Data in arrayList
                arrayList.add(new Student(stdId,strBuilProg.toString(),semester,strBuilCours.toString()));

                //String builder are reset
                strBuilCours.setLength(0);
                strBuilProg.setLength(0);

            }

        } catch (EOFException ex) {
            System.out.println("End of the file");
        } catch (IOException ex) {
            System.out.println("Error! File is not found");
        }
    }
    
    //This method is used to update the data 
    //This method is designed by *****CEZMI******
    public static void updateRecord (Student stdReg, int record) { 
        try(RandomAccessFile rdAOut = new RandomAccessFile(bfile,"rw"))
        {
            //set pointer at the beginning of the updated record
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
            
            //Clears Array List
            arrayList.clear();
            
        } catch (EOFException ex) {
            System.out.println("End of the file");
        } catch (IOException ex) {
            System.out.println("Error! File is not found");
        }

    }
    
    
    //This method is designed by  **** CEZMI ****
    //This method is designed to load data into combobox from a text file
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

    //This method is designed by  **** CEZMI ****
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

    
    //Tim Below 
    public static void saveRecord (int recInfo) throws IOException 
    {
    	try(PrintWriter writer = new PrintWriter(new FileWriter(cfile, true))) 
    	{						
			writer.println(recInfo);	
    	}
    }
    
    /*
    public static String readRec() throws IOException { 
    	//some issues here 
		Scanner scanner = new Scanner(cfile);		
		String recsAdded = null; 
		while(scanner.hasNext())
		{
			recsAdded = scanner.nextLine();			
		}
		scanner.close();		
		return recsAdded;
	}
	*/
    public static Object[] readRec() throws IOException { 
		Scanner scanner = new Scanner(cfile);	
		
		ArrayList<String> recsAdded = new ArrayList<String>(); // create an array list to add to using scanner	
		while (scanner.hasNext())
		{
			String info = scanner.nextLine();
			recsAdded.add(info);
		}
		scanner.close();			
		return recsAdded.toArray();		
	}
    //Tim above

    
    
    //This method is designed by  **** TIMOTHY ****
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
				
					
				StringBuilder Stud = new StringBuilder();
				for (int i=0; i<STUD_ID_SIZE; i++) {
					char nameChar = dIn.readChar();
					Stud.append(nameChar);				
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
				//S.setStudentId(Stud.toString());
				S.setProgram(Prog.toString());
				S.setSemester(Sem);
				S.setCourses(Courses.toString());	
				return S;
			}
			else
				throw new IOException("Invalid - No records found");				
		}
    }

    //This method is designed by  **** TIMOTHY ****
    public static Student previousRecord (int recNum)  throws IOException
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
					
				StringBuilder Stud = new StringBuilder();
				for (int i=0; i<STUD_ID_SIZE; i++) {
					char nameChar = dIn.readChar();
					Stud.append(nameChar);				
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
				//S.setStudentId(Stud.toString());
				S.setProgram(Prog.toString());
				S.setSemester(Sem);
				S.setCourses(Courses.toString());	
				return S;
			}
			else
				throw new IOException("Invalid - No records found");				
		}
    }

    //This method is designed by  **** TIMOTHY ****
    public static Student nextRecord (int recNum) throws IOException
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
					
				StringBuilder Stud = new StringBuilder();
				for (int i=0; i<STUD_ID_SIZE; i++) {
					char nameChar = dIn.readChar();
					Stud.append(nameChar);				
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
				//S.setStudentId(Stud.toString());
				S.setProgram(Prog.toString());
				S.setSemester(Sem);
				S.setCourses(Courses.toString());	
				return S;
			}
			else
				throw new IOException("Invalid - No records found");				
		}
    }

    //This method is designed by  **** TIMOTHY ****
    public static Student lastRecord (int recNum) throws IOException
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
				
				
				StringBuilder Stud = new StringBuilder();
				for (int i=0; i<STUD_ID_SIZE; i++) {
					char nameChar = dIn.readChar();
					Stud.append(nameChar);				
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
				//S.setStudentId(Stud.toString());
				S.setProgram(Prog.toString());
				S.setSemester(Sem);
				S.setCourses(Courses.toString());	
				return S;
			}
			else
				throw new IOException("Invalid - No records found");				
		}
    }

}