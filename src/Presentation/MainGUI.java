package Presentation;


import Business.Student;

import Data.StudentIO;
import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;

public class MainGUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtStudentId;
    private JTextArea textArea;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private Business.Student student;
    // tim vars below
    private int counter = 0;
    private int addrecs = 0;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainGUI frame = new MainGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainGUI() {
        setTitle("Student Course Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 490, 589);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel lblStudentId = new JLabel("Student ID");
        lblStudentId.setForeground(Color.BLUE);
        lblStudentId.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblStudentId.setBounds(10, 14, 93, 24);
        contentPane.add(lblStudentId);

        JLabel lblProgram = new JLabel("Program");
        lblProgram.setForeground(Color.BLUE);
        lblProgram.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblProgram.setBounds(10, 55, 93, 24);
        contentPane.add(lblProgram);

        JLabel lblSemester = new JLabel("Semester");
        lblSemester.setForeground(Color.BLUE);
        lblSemester.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblSemester.setBounds(10, 97, 93, 24);
        contentPane.add(lblSemester);

        JLabel lblCourses = new JLabel("Courses");
        lblCourses.setForeground(Color.BLUE);
        lblCourses.setFont(new Font("Tahoma", Font.BOLD, 13));
        lblCourses.setBounds(10, 139, 93, 24);
        contentPane.add(lblCourses);

        txtStudentId = new JTextField();
        txtStudentId.setFont(new Font("Tahoma", Font.BOLD, 12));
        txtStudentId.setBounds(125, 14, 228, 24);
        contentPane.add(txtStudentId);
        txtStudentId.setColumns(10);

        JComboBox comboBox = new JComboBox(StudentIO.comboBoxLoader());
        comboBox.setBounds(125, 53, 228, 24);
        contentPane.add(comboBox);

        JRadioButton rdOne = new JRadioButton("1");
        rdOne.setSelected(true);
        buttonGroup.add(rdOne);
        rdOne.setBounds(137, 96, 36, 24);
        contentPane.add(rdOne);

        JRadioButton rdTwo = new JRadioButton("2");
        buttonGroup.add(rdTwo);
        rdTwo.setBounds(195, 95, 36, 24);
        contentPane.add(rdTwo);

        JRadioButton rdThree = new JRadioButton("3");
        buttonGroup.add(rdThree);
        rdThree.setBounds(253, 95, 36, 24);
        contentPane.add(rdThree);

        JRadioButton rdFour = new JRadioButton("4");
        buttonGroup.add(rdFour);
        rdFour.setBounds(311, 95, 36, 24);
        contentPane.add(rdFour);

        JCheckBox chckbxC1 = new JCheckBox("C1");
        chckbxC1.setBounds(125, 138, 44, 24);
        contentPane.add(chckbxC1);

        JCheckBox chckbxC2 = new JCheckBox("C2");
        chckbxC2.setBounds(171, 138, 44, 24);
        contentPane.add(chckbxC2);

        JCheckBox chckbxC3 = new JCheckBox("C3");
        chckbxC3.setBounds(217, 138, 44, 24);
        contentPane.add(chckbxC3);

        JCheckBox chckbxC4 = new JCheckBox("C4");
        chckbxC4.setBounds(263, 138, 44, 24);
        contentPane.add(chckbxC4);

        JCheckBox chckbxC5 = new JCheckBox("C5");
        chckbxC5.setBounds(309, 138, 44, 24);
        contentPane.add(chckbxC5);

        JButton btnSave = new JButton("Save");
        btnSave.addActionListener(e -> {
        	
            StringBuilder stringBuilder = new StringBuilder();
            int selSemester = 0;
            
            //To add semester
            if (rdOne.isSelected()) selSemester = 1;
            if (rdTwo.isSelected()) selSemester = 2;
            if (rdThree.isSelected()) selSemester = 3;
            if (rdFour.isSelected()) selSemester = 4;

            System.out.println(selSemester); //Checker in Console

            //To add Classes to StringBuilder
            if(chckbxC1.isSelected()) stringBuilder.append("C1 ");
            if(chckbxC2.isSelected()) stringBuilder.append("C2 ");
            if(chckbxC3.isSelected()) stringBuilder.append("C3 ");
            if(chckbxC4.isSelected()) stringBuilder.append("C4 ");
            if(chckbxC5.isSelected()) stringBuilder.append("C5");
            
            System.out.println(stringBuilder.toString()); //Checker in Console

            //This line will load data to Student class
            student = new Student(StudentIO.idFinder(), comboBox.getSelectedItem(),selSemester, stringBuilder.toString());
            
           
            JOptionPane.showMessageDialog(null,"Student ID: " + StudentIO.idFinder() + "\n" + 
            "Program: " + comboBox.getSelectedItem() + "\n" +
            "Semester: " + selSemester + "\n"+ 
            "Courses: " + stringBuilder.toString());
            
            StudentIO.saveData(student);
            
            // Cezmi - I did not understand purpose of this code????
            
            // Tim  - keeps track of records added for quick reference           
			/*
			 * addrecs = 1; try { StudentIO.saveRecord(addrecs); } catch (IOException e1) {
			 * // TODO Auto-generated catch block e1.printStackTrace(); }
			 */
            // Tim 

        });
        btnSave.setForeground(Color.RED);
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnSave.setBounds(384, 52, 80, 24);
        contentPane.add(btnSave);

        JButton btnDisplay = new JButton("Display");
        btnDisplay.addActionListener(e ->  { //This button is designed to DISPLAY selected Program from combobox
        	textArea.setText("");
        	
            System.out.println("Button is working"); //Checker in Console
            
            StringBuilder strBuild = new StringBuilder();
            if (StudentIO.arrayList.size() == 0) { //Checks ArrayList size
                StudentIO.displayData(); //If array list size is 0 it will call this method to add elements.
                
                for (Student k: StudentIO.arrayList // ForEach to check each elements in array list
                ) {
                    System.out.println("It is in the foreach loop"); //Checker in Console
                    
                    if (k.getProgram().equals(comboBox.getSelectedItem())) // Checks equality of selected item in ComboBox 
                    	strBuild.append(k.getStudentId()).append(" ").append(k.getProgram()).append(" ").append(k.getSemester()).append(" ").append(k.getCourses()).append("\n");
                }
                textArea.setText(strBuild.toString());
                
                strBuild.setLength(0);
                
            } else { //if array list contains element this part will be executed
                for (Student k: StudentIO.arrayList
                ) {
                    System.out.println("It is in the foreach loop");//Checker in Console
                    
                    if (k.getProgram().equals(comboBox.getSelectedItem())) // Checks equality of selected item in ComboBox 
                    	strBuild.append(k.getStudentId()).append(" ").append(k.getProgram()).append(" ").append(k.getSemester()).append(" ").append(k.getCourses()).append("\n");
                }
                textArea.setText(strBuild.toString()); // adds content of StringBuilder in to the text area.
                
                strBuild.setLength(0); // Resets the StringBuilder
            }
        
        });
        btnDisplay.setForeground(Color.RED);
        btnDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnDisplay.setBounds(384, 94, 80, 24);
        contentPane.add(btnDisplay);

        
        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> { //Button designed to EXIT from app
            System.exit(0);
        });
        btnExit.setForeground(Color.RED);
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnExit.setBounds(384, 136, 80, 24);
        contentPane.add(btnExit);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 171, 454, 2);
        contentPane.add(separator);

        JButton btnFirst = new JButton("First");
        btnFirst.setToolTipText("Returns Oldest Record");
        btnFirst.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) 
        	{
        		setCounter(1);
        		int record=1;        		
				
				try {
					Student S = StudentIO.firstRecord(record);
					textArea.setText("The First Record" + "\n" +
					"Student ID: " + String.valueOf(S.getStudentId()) + "\n" + 
					"Program Name: " + String.valueOf(S.getProgram()) + "\n" + 
					"Semester: " + String.valueOf(S.getSemester()) + "\n"  +
					"Course List: " + S.getCourses());				
					
				}
				catch(IOException e1){					
					JOptionPane.showMessageDialog(null, "Error ! " + e1.getMessage());
				}		
        	}
        });
        btnFirst.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnFirst.setForeground(new Color(0, 153, 0));
        btnFirst.setBounds(10, 186, 80, 24);
        contentPane.add(btnFirst);

        JButton btnPrevious = new JButton("Previous");
        btnPrevious.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) 
        	{
        		int record = getCounter();  
        		record -= 1;
        		setCounter(record);
        		
        		//textArea.setText(String.valueOf(record));
        		if (record >= 1) {
        			textArea.setText(String.valueOf(record));        			
        			try {
        			
        			Student S = StudentIO.previousRecord(record);
					textArea.setText("The Previous Record" + "\n" +
					"Student ID: " + String.valueOf(S.getStudentId()) + "\n" + 
					"Program Name: " + String.valueOf(S.getProgram()) + "\n" + 
					"Semester: " + String.valueOf(S.getSemester()) + "\n"  +
					"Course List: " + S.getCourses());
									
					
        			}
					catch(IOException e1){					
						JOptionPane.showMessageDialog(null, "Error! " + e1.getMessage());						
					}
					
        		}
        		else
        		{
        			JOptionPane.showMessageDialog(null, "Error! There are no prev records");
        			record += 1;
        			setCounter(record);
        		}
        		
				
        	}
        });
        btnPrevious.setForeground(new Color(0, 153, 0));
        btnPrevious.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPrevious.setBounds(100, 187, 90, 24);
        contentPane.add(btnPrevious);

        JButton btnNext = new JButton("Next");
        btnNext.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) 
        	{
        		int record = getCounter();        		
        		record += 1;
        		setCounter(record);        		
        		
        		if (record >= 1) {        			        			
        			try 
        			{
	        			Student S = StudentIO.nextRecord(record);
						textArea.setText("The Next Record" + "\n" +
						"Student ID: " + String.valueOf(S.getStudentId()) + "\n" + 
						"Program Name: " + String.valueOf(S.getProgram()) + "\n" + 
						"Semester: " + String.valueOf(S.getSemester()) + "\n"  +
						"Course List: " + S.getCourses());
        			}
					catch(IOException e1){					
						JOptionPane.showMessageDialog(null, "Error! There are no more records \n" 
															+ e1.getMessage());
						record -= 1;
		        		setCounter(record);						
					}
					
        		}        		
        	}
        });
        btnNext.setForeground(new Color(0, 153, 0));
        btnNext.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNext.setBounds(200, 187, 80, 24);
        contentPane.add(btnNext);

        JButton btnLast = new JButton("Last");
        btnLast.setToolTipText("Returns Most Rececnt Record");
        btnLast.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) 
        	{
        		//textArea.setText(String.valueOf(getAddrecs())); // checker
        		Object[] records = null;
        		int record = 0;
				try {
					 records = StudentIO.readRec();	
					 for(int i = 0; i < records.length; i++)
					 {
						 record += 1;
					 }
					 textArea.setText(String.valueOf(record)); // checker 
					 
				} catch (IOException e1) {					
					e1.printStackTrace();
				} 		
        		setCounter(record);
        	
        		try {
					Student S = StudentIO.lastRecord(record);
					textArea.setText("The Last Record" + "\n" +
					"Student ID: " + String.valueOf(S.getStudentId()) + "\n" + 
					"Program Name: " + String.valueOf(S.getProgram()) + "\n" + 
					"Semester: " + String.valueOf(S.getSemester()) + "\n"  +
					"Course List: " + S.getCourses());				
					
				}
				catch(IOException e1){					
					JOptionPane.showMessageDialog(null, "Error ! " + e1.getMessage());
				}
				
						
        	}
        });
        btnLast.setForeground(new Color(0, 153, 0));
        btnLast.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLast.setBounds(290, 187, 80, 24);
        contentPane.add(btnLast);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.addActionListener(e -> {
            if(txtStudentId.getText().equals(""))
                JOptionPane.showMessageDialog(null,"Student ID can not be empty");
            else {
                StringBuilder stringBuilder = new StringBuilder();
                int selSemester = 0;

                if (rdOne.isSelected()) selSemester = 1;
                if (rdTwo.isSelected()) selSemester = 2;
                if (rdThree.isSelected()) selSemester = 3;
                if (rdFour.isSelected()) selSemester = 4;

                System.out.println(selSemester);

                if(chckbxC1.isSelected()) stringBuilder.append("C1 ");
                if(chckbxC2.isSelected()) stringBuilder.append("C2 ");
                if(chckbxC3.isSelected()) stringBuilder.append("C3 ");
                if(chckbxC4.isSelected()) stringBuilder.append("C4 ");
                if(chckbxC5.isSelected()) stringBuilder.append("C5");
                System.out.println(stringBuilder.toString());

                student = new Student(StudentIO.idFinder(), comboBox.getSelectedItem(),selSemester, stringBuilder.toString());

                StudentIO.updateRecord(student, Integer.parseInt(txtStudentId.getText()));
            }
        });
        btnUpdate.setToolTipText("Update Current Record");
        btnUpdate.setForeground(new Color(0, 153, 0));
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnUpdate.setBounds(380, 187, 80, 24);
        contentPane.add(btnUpdate);

        textArea = new JTextArea();
        textArea.setBounds(10, 220, 454, 319);
        contentPane.add(textArea);
    }

	private void setaddrec() {
		// TODO Auto-generated method stub
		
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getAddrecs() {
		return addrecs;
	}

	public void setAddrecs(int addrecs) {
		this.addrecs = addrecs;
	}
}