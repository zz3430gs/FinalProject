package com.Joe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MusicRecordGUI extends JFrame {
    private JPanel rootPanel;
    //private JTabbedPane tabbedPane;
    private JTextField recordArtist;
    private JTextField recordTitle;
    private JTextField sellingPrice;
    private JButton addNewRecordButton;
    private JButton deleteRecordButton;
    private JButton quitButton;
    private JTextField consignerName;
    private JTable musicRecordTable;
    //private JComboBox searchBycomboBox;
    private JButton sellRecordButton;


    MusicRecordGUI(final MusicData musicRecord) {//, final MusicData cosigner_info_display, final MusicData sales_records) {

        //tabbedPane = new JTabbedPane();

       /* ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
                int index = sourceTabbedPane.getSelectedIndex();
                System.out.println("Tab Changed To: "+sourceTabbedPane.getTitleAt(index));
                System.out.println("Tab Index Number: "+index);
                if(index==0){
                    musicDatamodel.fireTableDataChanged();
                    //musicDatamodel.search("Default","",index);
                }
                else if(index==1){
                    cosigner_info_display.fireTableDataChanged();
                   // cosigner_info_display.search("Default","",index);
                }
                else if(index==2){
                    sales_records.fireTableDataChanged();
                    //sales_records.search("Default","",index+1);
                    System.out.println("This is the sales tab!");
                }
            }
        };
        tabbedPane.addChangeListener(changeListener);
        rootPanel.add(tabbedPane);
        //tabbedPane.add("Music Record", new MusicRecordGUI(musicDatamodel, cosigner_info_display, sales_records));
        tabbedPane.add("Consigner Information",new Consigner_Info(cosigner_info_display));
        tabbedPane.add("Sales Record",new SalesRecords(sales_records));*/

        musicRecordTable.setGridColor(Color.BLACK);
        musicRecordTable.setModel(musicRecord);

        setContentPane(rootPanel);
        pack();
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        addNewRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                //Get record title, make sure it's not blank
                String title = recordTitle.getText();

                if (title == null || title.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPanel, "Please enter a title");
                    return;
                }
                String consignername = consignerName.getText();
                if (consignername == null || consignername.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPanel, "Please enter a consigner name");
                    return;
                }
                String name = recordArtist.getText();
                if (name == null || name.trim().equals("")) {
                    JOptionPane.showMessageDialog(rootPanel, "Please enter a artist name");
                    return;
                }
                int sellPrice;
                try {
                    sellPrice = Integer.parseInt(sellingPrice.getText());
                    if (sellPrice <= -0) {
                        throw new NumberFormatException("Please enter a positive number");
                    }//TODO What if it was a string
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPanel, "The price number can't be negative");
                    return;
                }
                //Print out what has been added
                System.out.println("Adding " + name + " " + title + " " + " " + sellPrice + " " + consignername);
                boolean insertRecordRow = musicRecord.insert_Record_To_Table(name, title, sellPrice, consignername);

                if (!insertRecordRow) {
                    JOptionPane.showMessageDialog(rootPanel, "Error adding new Music Record");
                }
                musicRecord.fireTableDataChanged();
            }
        });

        deleteRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int currentRow = musicRecordTable.getSelectedRow();

                if (currentRow == -1) {      // -1 means no row is selected. Display error message.
                    JOptionPane.showMessageDialog(rootPanel, "Please choose a record to delete");
                }
                boolean deleted = musicRecord.deleteRow(currentRow);
                if (deleted) {
                    ConnectDB.loadAllRecordData();
                } else {
                    JOptionPane.showMessageDialog(rootPanel, "Error deleting music record");
                }
            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ConnectDB.shutdown();
                System.exit(0);
            }
        });
        //got help and code from Malcolm
        sellRecordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int n = JOptionPane.showConfirmDialog(null, "Are you certain you want to sell that record?", "WARNING!", JOptionPane.YES_NO_OPTION);
                if (n == 0) {
                    int rowIndex;
                    int colIndex;
                    rowIndex = musicRecordTable.getSelectedRow();
                    System.out.println("The row I grabbed :" + rowIndex);
                    colIndex = musicRecord.getColumnCount();
                    System.out.println("The column I just grabbed :" + colIndex);
                    //this grabs the ID number of the record so that I can execute the Data Models 'Sell Record' method.
                    String i = (String) musicRecord.getValueAt(rowIndex, 0);
                    if (musicRecord.sellRecord(i)) {
                        musicRecord.fireTableDataChanged();
                        JOptionPane.showMessageDialog(rootPanel, "Record has been sold and moved to sales tab.");
                    }
                } else if (n == 1) {
                    JOptionPane.showMessageDialog(rootPanel, "  Sales Transaction Aborted.  ");
                }
            }
        });
    }

    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        ConnectDB.shutdown();
    }
}
