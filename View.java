package com.javarush.task.task32.task3209;

import com.javarush.task.task32.task3209.listeners.FrameListener;
import com.javarush.task.task32.task3209.listeners.TabbedPaneChangeListener;
import com.javarush.task.task32.task3209.listeners.UndoListener;

import javax.swing.*;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;
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
    private UndoManager undoManager = new UndoManager();
    private UndoListener undoListener = new UndoListener(undoManager);

    public UndoListener getUndoListener() {
        return undoListener;
    }

    public View() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            ExceptionHandler.log(e);
        } catch (InstantiationException e) {
            ExceptionHandler.log(e);
        } catch (IllegalAccessException e) {
            ExceptionHandler.log(e);
        } catch (UnsupportedLookAndFeelException e) {
            ExceptionHandler.log(e);
        }
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
        JMenuBar menuBar = new JMenuBar();
        MenuHelper.initFileMenu(this,menuBar);
        MenuHelper.initEditMenu(this,menuBar);
        MenuHelper.initStyleMenu(this,menuBar);
        MenuHelper.initAlignMenu(this,menuBar);
        MenuHelper.initColorMenu(this,menuBar);
        MenuHelper.initFontMenu(this,menuBar);
        MenuHelper.initHelpMenu(this,menuBar);
        this.getContentPane().add(menuBar,BorderLayout.NORTH);

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

    @Override
    public void actionPerformed(ActionEvent e) {
       String event = e.getActionCommand();
        if (event.equals("Новый")){
            controller.createNewDocument();

        }
        else if (event.equals("Открыть")){
            controller.openDocument();
        }
        else if (event.equals("Сохранить")){
            controller.saveDocument();
        }
        else if (event.equals("Сохранить как...")){
            controller.saveDocumentAs();
        }
        else if (event.equals("Выход")){
            controller.exit();
        }
        else if (event.equals("О программе")){
            showAbout();
        }

    }

    public boolean canUndo(){
        return undoManager.canUndo();
    }
    public boolean canRedo(){
        return undoManager.canRedo();
    }
    public void undo(){
        try{
        undoManager.undo();}
        catch (CannotUndoException e){
            ExceptionHandler.log(e);
        }
    }
    public void redo(){
        try {
            undoManager.redo();
        }
        catch (CannotRedoException e){
            ExceptionHandler.log(e);
        }
    }
    public void resetUndo(){
        undoManager.discardAllEdits();
    }

    public boolean isHtmlTabSelected(){
        if (tabbedPane.getSelectedIndex()==0){
            return true;
        }
        return false;
    }
    public void selectHtmlTab(){
        tabbedPane.setSelectedIndex(0);
        this.resetUndo();
    }

    public void update(){
        htmlTextPane.setDocument(controller.getDocument());
    }
    public void showAbout(){
        JOptionPane.showMessageDialog(this.getContentPane(),"ShitSoft Corp. beta 0.1","HTML Editor",JOptionPane.INFORMATION_MESSAGE);
    }

    public void selectedTabChanged(){
        if (tabbedPane.getSelectedIndex()==0){
            controller.setPlainText(plainTextPane.getText());
        }
        else if (tabbedPane.getSelectedIndex()==1){
            plainTextPane.setText(controller.getPlainText());
        }
        this.resetUndo();
    }
}
