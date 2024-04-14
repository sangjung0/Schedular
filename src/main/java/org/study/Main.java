package org.study;

import javax.swing.*;

public class Main extends JFrame {

    Main(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("test");
        setLocation(100,100);
        setSize(300,300);
        setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("current path: " + System.getProperty("user.dir"));
        new Main();
    }
}