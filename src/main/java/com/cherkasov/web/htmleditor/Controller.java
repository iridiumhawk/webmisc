package com.cherkasov.web.htmleditor;

import javax.swing.*;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import java.io.*;

/**
 * Created by hawk on 26.08.2016.
 */
public class Controller {
    private View view;

    private HTMLDocument document;
    private File currentFile;

    public HTMLDocument getDocument() {

        return document;
    }

    public Controller(View view) {

        this.view = view;
    }

    public void init() {

        createNewDocument();
    }

    public void exit() {

        System.exit(0);
    }

    public static void main(String[] args) {

        View view = new View();                          //2.2.1.	Создавать объект представления.
        Controller controller = new Controller(view);    //2.2.2.	Создавать контроллер, используя представление.
        view.setController(controller);                  //2.2.3.	Устанавливать у представления контроллер.
        view.init();                                      //2.2.4.	Инициализировать представление.
        controller.init();                                //2.2.5.	Инициализировать контроллер. Контроллер должен инициализироваться после представления
    }

    public void resetDocument() {

        if (document != null) {
            document.removeUndoableEditListener(view.getUndoListener());
        }
        HTMLDocument doc = (HTMLDocument) new HTMLEditorKit().createDefaultDocument();
        document = doc;
        document.addUndoableEditListener(view.getUndoListener());
        view.update();
    }

    public void setPlainText(String text) {

        resetDocument();
        StringReader stringReader = new StringReader(text);
        try {
            new HTMLEditorKit().read(stringReader, document, 0);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public String getPlainText() {

        StringWriter stringWriter = new StringWriter();
        try {
            new HTMLEditorKit().write(stringWriter, document, 0, document.getLength());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return stringWriter.toString();
    }

    public void createNewDocument() {

        view.selectHtmlTab();
        resetDocument();
        view.setTitle("HTML редактор");
        view.resetUndo();
        currentFile = null;
    }

    public void openDocument() {

        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());


        if (chooser.showOpenDialog(view) == JFileChooser.APPROVE_OPTION) {

            currentFile = chooser.getSelectedFile();

            resetDocument();

            view.setTitle(currentFile.getName());

            try (FileReader fileReader = new FileReader(currentFile)) {

                new HTMLEditorKit().read(fileReader, document, 0);
                view.resetUndo();

            } catch (Exception e) {
                ExceptionHandler.log(e);
            }

        }
    }


    public void saveDocument() {

        if (currentFile != null) {

            view.selectHtmlTab();
//            view.setTitle(currentFile.getName());

            try (FileWriter fileWriter = new FileWriter(currentFile)) {

                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());

            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        } else {
            saveDocumentAs();
        }
    }


    public void saveDocumentAs() {

        view.selectHtmlTab();
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(new HTMLFileFilter());
        if (chooser.showSaveDialog(view) == JFileChooser.APPROVE_OPTION) {

            currentFile = chooser.getSelectedFile();

            view.setTitle(currentFile.getName());

            try (FileWriter fileWriter = new FileWriter(currentFile)) {

                new HTMLEditorKit().write(fileWriter, document, 0, document.getLength());

            } catch (Exception e) {
                ExceptionHandler.log(e);
            }
        }
    }
}