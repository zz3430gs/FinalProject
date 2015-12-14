package com.Joe;

import javax.swing.*;
import java.awt.*;

public class SalesRecords extends JPanel{
    private JPanel rootPanel;
    private JTable salesTable;

    public SalesRecords(final MusicData salesRecords){

        salesTable.setModel(salesRecords);
        salesTable.setGridColor(Color.black);
        salesRecords.fireTableDataChanged();


    }


}
