package com.Joe;
//This is used because couldn't figure out how to use tabbedpane
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;

public class MainGui extends JFrame implements WindowListener {
    private JPanel rootPanel;
    private JTabbedPane TabbedPane;

    public MainGui(final MusicData musicRecord, final MusicData cosigner_info_display, final MusicData sales_records) {
        setContentPane(rootPanel);

        TabbedPane=new JTabbedPane();
        ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            System.out.println("Tab Changed To: " + sourceTabbedPane.getTitleAt(index));
            System.out.println("Tab Index Number: " + index);
            if (index == 0) {
                musicRecord.fireTableDataChanged();
                //musicDatamodel.search("Default","",index);
                System.out.println("This is the records tab");
            } else if (index == 1) {
                cosigner_info_display.fireTableDataChanged();
                // cosigner_info_display.search("Default","",index);
                System.out.println("This is the consigner tab");
            } else if (index == 2) {
                sales_records.fireTableDataChanged();
                //sales_records.search("Default","",index+1);
                System.out.println("This is the sales tab!");
            }

        }
    };
        TabbedPane.addChangeListener(changeListener);
        rootPanel.add(TabbedPane);
        TabbedPane.add("Music Records", new MusicRecordGUI(musicRecord));
        TabbedPane.add("Consigner Information",new Consigner_Info(cosigner_info_display));
        TabbedPane.add("Sales Record",new SalesRecords(sales_records));
        //setSize(600, 600);
        setVisible(true);
        pack();
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

}
    public void windowClosing(WindowEvent e) {
        try{
            System.out.println("closing");
            ConnectDB.shutdownResources();
    }catch (SQLException se){
            System.out.println(se);
            System.out.println("Error closing");
        }

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
