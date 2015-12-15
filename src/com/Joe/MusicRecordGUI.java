package com.Joe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MusicRecordGUI extends JFrame{
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


    MusicRecordGUI(final MusicData musicRecord){//, final MusicData cosigner_info_display, final MusicData sales_records) {

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

       // setContentPane(rootPanel);
        //pack();
        //setSize(600, 600);
        //setVisible(true);
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


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
                    }
                } catch (NumberFormatException ne) {
                    JOptionPane.showMessageDialog(rootPanel, "The price number can't be negative");
                    return;
                }
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
       /* searchButton.addActionListener(new ActionListener() {
            String searchBy="";
            @Override
            public void actionPerformed(ActionEvent e) {
                searchBy = searchField.getText();
               // if (searchBycomboBox.getSelectedItem().equals(opt0)) {
                   // musicDatamodel.search(CreateTables.C_NAME,searchBy,1);
                }
            }
        });*/
    }


    public void windowClosing(WindowEvent e) {
        System.out.println("closing");
        ConnectDB.shutdown();
    }

/*    @Override
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
    }*/
}