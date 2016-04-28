package com.Momo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.geom.Arc2D;

/**
 * Created by Momo Johnson on 4/27/2016.
 */
public class DataForm extends JFrame implements WindowListener {
    private JTextField textFieldArea;
    private JPanel rootPane;
    private JButton quitButton;
    private JButton addButton;
    private JButton deleteButton;
    private JTable rubicTableData;
    private JTextField timeField;

    public DataForm(final RubikCubeDataModel rubikCubeDataModel){
        //Definition of various Gui variables
        setContentPane(rootPane);
        setTitle("Rubic Cube Solver");
        pack();
        addWindowListener(this);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);

        //setting up the Jtable
        rubicTableData.setGridColor(Color.blue);
        rubicTableData.setModel(rubikCubeDataModel);

        //Action listener for the add button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String solver = textFieldArea.getText();
                if(solver ==null || solver.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "Please enter valid text", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double timeEnter =0;
                try{
                    timeEnter = Double.parseDouble(timeField.getText());
                }catch (NumberFormatException se){
                    JOptionPane.showMessageDialog(rootPane, "Please enter a valid number", "Error", JOptionPane.ERROR_MESSAGE);
                }
                boolean insertData = rubikCubeDataModel.insertRows(solver, timeEnter);
                if(!insertData){
                    JOptionPane.showMessageDialog(rootPane,"Please enter a new cube solver", "Error", JOptionPane.ERROR_MESSAGE );
                }

            }
        });
        //An action listner for the quit button
        quitButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RubikCubeDatabase.shutDown();
                System.exit(0);
            }
        });
        //An action listener for the delete button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = rubicTableData.getSelectedRow();
                if(row == -1){
                    JOptionPane.showMessageDialog(rootPane, "Please choose a cube solver to be deleted");
                }
                boolean todelete = rubikCubeDataModel.deleteRow(row);
                if(todelete){
                    RubikCubeDatabase.loadRubikCubeData();
                }else {
                    JOptionPane.showMessageDialog(rootPane, "Warning, Error deleting cube_solver");
                }
            }
        });
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    //A method that shuts the database securely
    public void windowClosing(WindowEvent e) {
        System.out.println("Database Closing");
        RubikCubeDatabase.shutDown();

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
