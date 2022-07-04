package com.example.lightnovelcrawler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LNController {
    @FXML
    private Button help;

    @FXML
    private Button settings;

    @FXML
    private TextArea console;

    @FXML
    private ChoiceBox commandBox;

    @FXML
    private TextField urlField;

    @FXML
    private TextField outputField;

    @FXML
    private Button folderSelector;

    @FXML
    private ChoiceBox formatBox;

    @FXML
    private CheckBox loginfieldCheck;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox selectorCheck;

    @FXML
    private ComboBox selectorBox;

    @FXML
    private TextField selectorField;

    @FXML
    private CheckBox packCheck;

    @FXML
    private RadioButton singlefileRadio;

    @FXML
    private RadioButton multiplefileRadio;

    @FXML
    private CheckBox overwriteCheck;

    @FXML
    private RadioButton replacefolderRadio;

    @FXML
    private RadioButton ignorefolderRadio;

    @FXML
    private CheckBox ofilenameCheck;

    @FXML
    private TextField ofilenameField;

    @FXML
    private RadioButton addsourceRadio;

    @FXML
    private Button runCommand;

    @FXML
    private Label messageBar;



    @FXML
    public void start() {
        String url = urlField.getText();
        String loc = outputField.getText();

        Runnable task = new Runnable() {
            @Override
            public void run() {
                try{
                    Process processDuration = new ProcessBuilder("cmd.exe", "/c", "cd \"C:\\Users\\GameDemons\\IdeaProjects\\Lightnovel Crawler\\src\\main\\java\\com\\example\\lightnovelcrawler\"","&lncrawl -s " + url + " -o \"" + loc + "\" --all -i --format text --suppress").redirectErrorStream(true).start();


                    Runnable re = new Runnable() {
                        @Override
                        public void run() {
                            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream()));
                            String line = "";
                            String b = "";
                            while (true){
                                try {
                                    if (!((line = bufferedReader.readLine()) != null)) break;
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                b+=line+"\n";
                                console.setText(b);
                            }
                            try {
                                int exitValue = processDuration.waitFor();
                            } catch (Exception e) {
                                messageBar.setText(e.getMessage());
                            }
                        }
                    };
                    new Thread(re).start();

                }catch (Exception e){
                    messageBar.setText(e.getMessage());
                }
            }
        };
        new Thread(task).start();





    }
}