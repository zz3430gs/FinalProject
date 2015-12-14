package com.Joe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MusicRecordGUI extends JFrame implements WindowListener{
    private JPanel rootPanel;
    private JTextField recordArtist;
    private JTextField recordTitle;
    private JTextField sellingPrice;
    private JButton addNewRecordButton;
    private JButton deleteRecordButton;
    private JButton quitButton;
    private JTextField consignerName;
    private JTable musicRecordTable;
    private JComboBox searchBycomboBox;
    private JTextField searchField;
    private JLabel searchLabel;
    private JButton searchButton;
    private JButton sellRecordButton;

    //Options for the combobox
    final private String opt0 = "Default";
    final private String opt1 = "Artist Name";
    final private String opt2 = "Record title";
    final private String opt3 = "Selling Price";

    MusicRecordGUI(final MusicData musicDatamodel) {

        musicRecordTable.setGridColor(Color.BLACK);
        musicRecordTable.setModel(musicDatamodel);

        setContentPane(rootPanel);
        pack();
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        searchBycomboBox.addItem(opt0);
        searchBycomboBox.addItem(opt1);
        searchBycomboBox.addItem(opt2);
        searchBycomboBox.addItem(opt3);


        addNewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get record title, make sure it's not blank
                String title = recordTitle.getText();

                if (title == null || title.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPane, "Please enter a title");
                    return;
                }
                String consignername = consignerName.getText();
                if (consignername == null || consignername.trim().equals("")) {
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
                    if (sellPrice <= -0) {
                        throw new NumberFormatException("Please enter a positive number");
                    }
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPane,
                            "The price number can't be negative");
                    return;
                }
                System.out.println("Adding " + title + " " + consignername + " " + name + " " + sellPrice);
                boolean insertedRow = musicDatamodel.insertRecordRow(consignername, title, name, sellPrice);

                if (!insertedRow) {
                    JOptionPane.showMessageDialog(rootPane, "Error adding new Music Record");
                }
                musicDatamodel.fireTableDataChanged();
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
                System.exit(0);
            }
        });
        /*searchButton.addActionListener(new ActionListener() {
            String searchBy="";
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBy = searchField.getText();
                if (searchBycomboBox.getSelectedItem().equals(opt0)) {
                    ("Default","",1);
                }else if (searchBycomboBox.getSelectedItem().equals(opt1)){
                    musicDatamodel.search(CreateTables.C_NAME,searchBy,1);
                }
            }
        });*/
    }


    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        Main.shutdown();
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
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