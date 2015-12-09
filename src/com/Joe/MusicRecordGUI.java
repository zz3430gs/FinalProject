package com.Joe;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class MusicRecordGUI extends JFrame {
    private JPanel rootPanel;
    private JTextField recordArtist;
    private JTextField recordTitle;
    private JTextField sellingPrice;
    private JButton addNewRecordButton;
    private JButton deleteRecordButton;
    private JButton quitButton;
    private JTextField consignerName;
    private JTable musicRecordTable;

    MusicRecordGUI(final MusicData musicDatamodel) {

        musicRecordTable.setModel(musicDatamodel);

        setContentPane(rootPanel);
        pack();
        setSize(500, 500);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        addNewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get Movie title, make sure it's not blank
                String title = recordTitle.getText();

                if (title == null || title.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a title");
                    return;
                }
                String consignername = consignerName.getText();
                if (consignername == null || consignername.trim().equals("")){
                    JOptionPane.showMessageDialog(rootPane, "Please enter a consigner name");
                    return;
                }
                String name = recordArtist.getText();
                if (name == null || name.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a artist name");
                    return;
                }
                int sellPrice;
                try {
                    sellPrice = Integer.parseInt(sellingPrice.getText());
                    if (sellPrice >= -0) {
                        throw new NumberFormatException("Please enter a positive number");
                    }
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane,
                            "The price number can't be negative");
                    return;
                }

            }

        });

        deleteRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = musicRecordTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPane, "Please choose a record to delete");
                }
                boolean deleted = musicDatamodel.deleteRow(currentRow);
                if (deleted) {
                    Main.loadAllMusics();
                } else {
                    JOptionPane.showMessageDialog(rootPane, "Error deleting music record");
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Main.shutdown();
                System.exit(0);   //Should probably be a call back to Main class so all the System.exit(0) calls are in one place.
            }
        });
    }
}