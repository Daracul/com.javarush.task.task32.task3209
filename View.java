package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by amalakhov on 05.06.2017.
 */
public class View extends JFrame implements ActionListener {
    private Controller controller;
    private JTabbedPane tabbedPane=new JTabbedPane();
    private JTextPane htmlTextPane = new JTextPane();
    private JEditorPane plainTextPane = new JEditorPane();
    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void init(){
        initGui();
        FrameListener listener = new FrameListener(this);
        this.addWindowListener(listener);
        this.setVisible(true);

    }
    public void initMenuBar(){

    }
    public void initEditor(){
        htmlTextPane.setContentType("text/html");
        tabbedPane.addTab("HTML",new JScrollPane(htmlTextPane));
        tabbedPane.addTab("Текст",new JScrollPane(plainTextPane));
        tabbedPane.setPreferredSize(new Dimension(410,50));
        tabbedPane.addChangeListener(new TabbedPaneChangeListener(this));
        this.getContentPane().add(tabbedPane,BorderLayout.CENTER);


    }
    public void initGui(){
        initMenuBar();
        initEditor();
        pack();
    }

    public void exit (){
        controller.exit();
    }

    public void selectedTabChanged(){}


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
