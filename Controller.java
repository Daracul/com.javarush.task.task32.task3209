package com.javarush.task.task32.task3209;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

/**
 * Created by amalakhov on 05.06.2017.
 */
public class Controller {
    private View view;
    private HTMLDocument document;
    private File currentFile;

    public Controller(View view) {
        this.view = view;
    }

    public void init(){
        createNewDocument();
    }

    public void exit(){
        System.exit(0);
    }

    public HTMLDocument getDocument() {
        return document;
    }
    public void resetDocument(){
        if (document!=null){
            document.removeUndoableEditListener(view.getUndoListener());
            document=(HTMLDocument) new HTMLEditorKit().createDefaultDocument();
            document.addUndoableEditListener(view.getUndoListener());
            view.update();
        }

    }

    public void setPlainText(String text){
        resetDocument();
        StringReader stringReader = new StringReader(text);
        try {
            new HTMLEditorKit().read(stringReader, document, document.getLength());
        } catch (IOException e) {
            ExceptionHandler.log(e);
        } catch (BadLocationException e) {
            ExceptionHandler.log(e);
        }

    }
    public String getPlainText(){
        StringWriter stringWriter = new StringWriter();
        try {
            new HTMLEditorKit().write(stringWriter,document,0,document.getLength());
        } catch (IOException e) {
            ExceptionHandler.log(e);
        } catch (BadLocationException e) {
            ExceptionHandler.log(e);
        }
        return stringWriter.toString();
    }

    public static void main(String[] args) {
        View view = new View();
        Controller controller = new Controller(view);
        view.setController(controller);
        view.init();
        controller.init();
    }
    public void createNewDocument(){
        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile=null;
    }
    public void openDocument(){
        view.selectHtmlTab();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());
        fileChooser.setDialogTitle("Open File");
        int option = fileChooser.showOpenDialog(view);
        if (option==JFileChooser.APPROVE_OPTION){
            currentFile=fileChooser.getSelectedFile();
            resetDocument();
            view.setTitle(currentFile.getName());
            try(FileReader fileReader = new FileReader(currentFile)){
                new HTMLEditorKit().read(fileReader,document,document.getLength());

            }
            catch (IOException e){ExceptionHandler.log(e);}
            catch (BadLocationException e) {
                ExceptionHandler.log(e);
            }
            view.resetUndo();
        }

    }
    public void saveDocument(){
        view.selectHtmlTab();
        if (currentFile==null){
            saveDocumentAs();
        }
        else {
            try(FileWriter fileWriter = new FileWriter(currentFile)){
                new HTMLEditorKit().write(fileWriter,document,0,document.getLength());

            }
            catch (IOException e){ExceptionHandler.log(e);}
            catch (BadLocationException e) {
                ExceptionHandler.log(e);
            }
        }
    }
    public void saveDocumentAs(){
        view.selectHtmlTab();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new HTMLFileFilter());
        fileChooser.setDialogTitle("Save File");
        int option = fileChooser.showSaveDialog(view);
        if (option==JFileChooser.APPROVE_OPTION){
            currentFile=fileChooser.getSelectedFile();
            view.setTitle(currentFile.getName());
            try(FileWriter fileWriter = new FileWriter(currentFile)){
                new HTMLEditorKit().write(fileWriter,document,0,document.getLength());

            }
            catch (IOException e){ExceptionHandler.log(e);}
            catch (BadLocationException e) {
                ExceptionHandler.log(e);
            }
        }


    }
}
