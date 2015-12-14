package com.Joe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joe on 12/14/2015.
 */
public class Consigner_Info extends JPanel {
    private JPanel rootPanel;
    private JTable consignerTable;
    private JButton addConsignerButton;
    private JButton deleteConsignerButton;
    private JButton searchButton;
    private JComboBox comboBox1;
    private JTextField textField1;
    private JButton quitButton;
    private JTextField consignerName;
    private JTextField consignerEmail;
    private JTextField consignerAddress;

    public Consigner_Info(final MusicData consigner_info_model){

        consignerTable.setGridColor(Color.black);
        consignerTable.setModel(consigner_info_model);


        addConsignerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cosignerName = consignerName.getText();
                String cosignerEmail = consignerEmail.getText();
                String cosignerAddress = consignerAddress.getText();
                if (cosignerAddress ==null || cosignerAddress.trim().equals("")&& cosignerEmail == null || cosignerEmail.trim().equals("")&& cosignerName ==null || cosignerName.trim().equals("")){
                    System.out.println("PLease enter in data and don't leave empty space");
                }
                System.out.println("Adding "+ cosignerName + " "+cosignerAddress+ " "+cosignerEmail);
                boolean cosignerInsertRow = consigner_info_model.insertCosignerRow(cosignerName, cosignerEmail , cosignerAddress);

                if (!cosignerInsertRow) {
                    JOptionPane.showMessageDialog(rootPanel, "Error adding new cosigner info");
                }

            }
        });
        deleteConsignerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = consignerTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPanel, "Please choose a consigner to delete");
                }
                boolean deleted = consigner_info_model.deleteRow(currentRow);
                if (deleted) {
                    Main.loadAllMusics();
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "Error deleting consigner");
                }

            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.shutdown();
                System.exit(0);
            }
        });
    }
}
