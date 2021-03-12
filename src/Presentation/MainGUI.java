package Presentation;

import Business.Student;
import Data.StudentIO;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Font;
import java.awt.Color;
import java.io.EOFException;
import java.io.IOException;

public class MainGUI extends JFrame {

    private JPanel contentPane;
    private JTextField txtStudentId;
    private JTextArea textArea;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private Business.Student student;

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

        JButton btnSave = new JButton("Save"); // This button is designed to save data
        btnSave.addActionListener(e -> {
            try {
                StringBuilder stringBuilder = new StringBuilder();
                int selSemester = 0;

                if (rdOne.isSelected()) selSemester = 1;
                if (rdTwo.isSelected()) selSemester = 2;
                if (rdThree.isSelected()) selSemester = 3;
                if (rdFour.isSelected()) selSemester = 4;

                if(chckbxC1.isSelected()) stringBuilder.append("C1 ");
                if(chckbxC2.isSelected()) stringBuilder.append("C2 ");
                if(chckbxC3.isSelected()) stringBuilder.append("C3 ");
                if(chckbxC4.isSelected()) stringBuilder.append("C4 ");
                if(chckbxC5.isSelected()) stringBuilder.append("C5");
                // Add data to a Student object
                student = new Student(StudentIO.idFinder(), comboBox.getSelectedItem(),selSemester, stringBuilder.toString());
                // call save data method
                StudentIO.saveData(student);

            } catch (IOException ex) {
                System.out.println("Error! File is not found");
            }
        });
        btnSave.setForeground(Color.RED);
        btnSave.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnSave.setBounds(384, 52, 80, 24);
        contentPane.add(btnSave);

        JButton btnDisplay = new JButton("Display"); //This button is designed to show data
        btnDisplay.addActionListener(e ->  {
            try {
                textArea.setText(""); //Clears the text Area
                String str = "Student ID" + "\t" + "Program" + "\t" + "Semester" + "\t" + "Courses" + "\n";

                if (StudentIO.arrayList.size() == 0) {
                    StudentIO.displayData(); //Calls displayData method
                    for (Student k: StudentIO.arrayList) { //This loop will get all elements of array list
                        System.out.println("It is in the foreach loop");
                        if (k.getProgram().equals(comboBox.getSelectedItem()))
                            str += k.getStudentId() + "\t" + k.getProgram() + "\t" + k.getSemester() + "\t" + k.getCourses() + "\n";
                    }
                    textArea.setText(str);
                }
                else {
                    for (Student k: StudentIO.arrayList) {
                        System.out.println("It is in the foreach loop");
                        if (k.getProgram().equals(comboBox.getSelectedItem()))
                            str += k.getStudentId() + "\t" + k.getProgram() + "\t" + k.getSemester() + "\t" + k.getCourses() + "\n";
                    }
                    textArea.setText(str);
                }
            } catch (IOException ex) {
                System.out.println("Error! File is not found");
            }
        });
        btnDisplay.setForeground(Color.RED);
        btnDisplay.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnDisplay.setBounds(384, 94, 80, 24);
        contentPane.add(btnDisplay);

        JButton btnExit = new JButton("Exit"); //This button is to exit from app
        btnExit.addActionListener(e -> {
            System.exit(0);
        });
        btnExit.setForeground(Color.RED);
        btnExit.setFont(new Font("Tahoma", Font.BOLD, 11));
        btnExit.setBounds(384, 136, 80, 24);
        contentPane.add(btnExit);

        JSeparator separator = new JSeparator();
        separator.setBounds(10, 171, 454, 2);
        contentPane.add(separator);

        JButton btnFirst = new JButton("First"); // This button is to show first record in the file
        btnFirst.addActionListener(e ->  {
            String str = "Student ID" + "\t" + "Program" + "\t" + "Semester" + "\t" + "Courses" + "\n";
            try {
                str += StudentIO.firstRecord();
                textArea.setText(str);
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }

        });
        btnFirst.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnFirst.setForeground(new Color(0, 153, 0));
        btnFirst.setBounds(10, 186, 80, 24);
        contentPane.add(btnFirst);

        JButton btnPrevious = new JButton("Previous"); // This button is to show previous record
        btnPrevious.addActionListener(e -> {
            String str = "Student ID" + "\t" + "Program" + "\t" + "Semester" + "\t" + "Courses" + "\n";
            try {
                str += StudentIO.previousRecord();
                textArea.setText(str);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,"There is no previous Data");
                System.out.println("Error in reading");
            }
        });
        btnPrevious.setForeground(new Color(0, 153, 0));
        btnPrevious.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnPrevious.setBounds(100, 187, 90, 24);
        contentPane.add(btnPrevious);

        JButton btnNext = new JButton("Next"); // This button is to show next record
        btnNext.addActionListener(e -> {
            String str = "Student ID" + "\t" + "Program" + "\t" + "Semester" + "\t" + "Courses" + "\n";
            try {
                str += StudentIO.nextRecord();
                textArea.setText(str);
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
        });
        btnNext.setForeground(new Color(0, 153, 0));
        btnNext.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnNext.setBounds(200, 187, 80, 24);
        contentPane.add(btnNext);

        JButton btnLast = new JButton("Last"); // This button is to show last record
        btnLast.addActionListener(e -> {
            String str = "Student ID" + "\t" + "Program" + "\t" + "Semester" + "\t" + "Courses" + "\n";
            try {
                str += StudentIO.lastRecord();
                textArea.setText(str);
            } catch (IOException ex) {
                System.out.println("Error in reading");
            }
        });
        btnLast.setForeground(new Color(0, 153, 0));
        btnLast.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnLast.setBounds(290, 187, 80, 24);
        contentPane.add(btnLast);

        JButton btnUpdate = new JButton("Update"); // This button is to update desired record
        btnUpdate.addActionListener(e -> {
            try {
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

                    if (chckbxC1.isSelected()) stringBuilder.append("C1 ");
                    if (chckbxC2.isSelected()) stringBuilder.append("C2 ");
                    if (chckbxC3.isSelected()) stringBuilder.append("C3 ");
                    if (chckbxC4.isSelected()) stringBuilder.append("C4 ");
                    if (chckbxC5.isSelected()) stringBuilder.append("C5");
                    System.out.println(stringBuilder.toString());

                    student = new Student(Integer.parseInt(txtStudentId.getText()), comboBox.getSelectedItem(), selSemester, stringBuilder.toString());

                    StudentIO.updateRecord(student, Integer.parseInt(txtStudentId.getText()));
                    JOptionPane.showMessageDialog(null, "Student ID : " + txtStudentId.getText() + "\nhas been updated successfully");
                }
            } catch (IOException ex) {
                System.out.println("Error! File is not found");
            }
        });
        btnUpdate.setForeground(new Color(0, 153, 0));
        btnUpdate.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnUpdate.setBounds(380, 187, 80, 24);
        contentPane.add(btnUpdate);

        textArea = new JTextArea();
        textArea.setBounds(10, 220, 454, 319);
        contentPane.add(textArea);
    }
}