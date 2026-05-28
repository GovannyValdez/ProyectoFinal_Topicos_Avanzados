package org.example;

import org.example.vista.Login;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {

                new Login();
            }
        });
    }
}