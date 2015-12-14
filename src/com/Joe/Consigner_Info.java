package com.Joe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Joe on 12/14/2015.
 */
public class Consigner_Info {
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

            }
        });
        deleteConsignerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
}
