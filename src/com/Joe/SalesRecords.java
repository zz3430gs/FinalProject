package com.Joe;

import javax.swing.*;
import java.awt.*;

public class SalesRecords extends JFrame{
    private JPanel rootPanel;
    private JTable salesTable;

    public SalesRecords(final MusicData salesRecords){

        setContentPane(rootPanel);
        pack();
        setSize(600, 600);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


        salesTable.setModel(salesRecords);
        salesTable.setGridColor(Color.black);
        salesRecords.fireTableDataChanged();


    }


}
