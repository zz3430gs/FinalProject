package com.Joe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainGui extends JFrame implements WindowListener {
    private JPanel rootPanel;
    private JTabbedPane jTabbedPane;

    public MainGui(final MusicData musicDatamodel, final MusicData cosigner_info_display, final MusicData sales_records) {
        setContentPane(rootPanel);

        jTabbedPane=new JTabbedPane();
        ChangeListener changeListener = new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
            JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
            int index = sourceTabbedPane.getSelectedIndex();
            System.out.println("Tab Changed To: " + sourceTabbedPane.getTitleAt(index));
            System.out.println("Tab Index Number: " + index);
            if (index == 0) {
                musicDatamodel.fireTableDataChanged();
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
        jTabbedPane.addChangeListener(changeListener);
        rootPanel.add(jTabbedPane);
        jTabbedPane.add("Music Record", new MusicRecordGUI(musicDatamodel));
        jTabbedPane.add("Consigner Information",new Consigner_Info(cosigner_info_display));
        jTabbedPane.add("Sales Record",new SalesRecords(sales_records));
        pack();
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

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
