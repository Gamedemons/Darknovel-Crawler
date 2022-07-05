package com.example.lightnovelcrawler;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;

import java.io.*;
import java.util.regex.Pattern;

public class LNController {
    // FXML Controls
    @FXML
    private Pane pane;

    @FXML
    private Button help;

    @FXML
    private Button settings;

    @FXML
    private TextArea console;

    @FXML
    private ComboBox commandBox;

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
    private Button loginReset;

    @FXML
    private TextField emailField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private CheckBox selectorCheck;

    @FXML
    private Button selectorReset;

    @FXML
    private ComboBox selectorBox;

    @FXML
    private TextField selectorField;

    @FXML
    private CheckBox packCheck;

    @FXML
    private Button packingReset;

    @FXML
    private RadioButton singlefileRadio;

    @FXML
    private RadioButton multiplefileRadio;

    @FXML
    private CheckBox overwriteCheck;

    @FXML
    private Button overwritingReset;

    @FXML
    private RadioButton replacefolderRadio;

    @FXML
    private RadioButton ignorefolderRadio;

    @FXML
    private CheckBox ofilenameCheck;

    @FXML
    private Button ofilenameReset;

    @FXML
    private TextField ofilenameField;

    @FXML
    private RadioButton addsourceRadio;

    @FXML
    private Button runCommand;

    @FXML
    private Label messageBar;


    // Public Variables
    String link = "";
    String outputFolder = "";
    String format = "";
    String email = "";
    String password = "";
    String selector = "";
    String filePacking = "";
    String overwriteFolder = "";
    String outputFilename = "";
    String addSource = "";

    String username = "";
    String query = "";

    String regexPattern = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";



    // Functions
    @FXML
    public void start (){
        messageBar.setText("");
        try{

            // Handle Empty Query Command Error
            int successExec = queryGenerator();
            if(successExec == 1 && commandBox.getValue().toString().equalsIgnoreCase("Query")){
                messageBar.setText("Enter a valid query");
                return;
            }

            //Main Exec
            Runnable task = new Runnable() {
                @Override
                public void run() {
                    try{
                        Process processDuration = new ProcessBuilder("cmd.exe", "/c", "cd \"C:\\Users\\GameDemons\\IdeaProjects\\Lightnovel Crawler\\src\\main\\java\\com\\example\\lightnovelcrawler\"",
                                query.trim()).redirectErrorStream(true).start();
                        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream()));
                        String line = "";
                        String stringBuilder = "";
                        while (true){
                            try {
                                if (!((line = bufferedReader.readLine()) != null)) break;
                            } catch (IOException e) {
                                messageBar.setText(e.getMessage());
                            }
                            stringBuilder += line + "\n";
                            console.setText(stringBuilder);
                        }
                        try {
                            int exitValue = processDuration.waitFor();
                        } catch (Exception e) {
                            messageBar.setText(e.getMessage());
                        }
                    }catch (Exception e){
                        messageBar.setText(e.getMessage());
                    }
                }
            };
            Thread thread1 = new Thread(task);
            thread1.setDaemon(true);
            thread1.start();
        }catch (Exception e){
            messageBar.setText(e.getMessage());
        }
    }

    public int queryGenerator() throws Exception {
        resetVariables();
        if(commandBox.getValue().toString().equalsIgnoreCase("Default - Download") || commandBox.getValue().toString().equalsIgnoreCase("Download")){
            // URL
            if(urlField.getText().isEmpty() || !(urlField.getText().startsWith("https://"))){
                messageBar.setText("Enter a valid URL.");
                return 1;
            }else{
                link = urlField.getText();
            }

            // Output Folder
            if(outputField.getText().isEmpty()){
                messageBar.setText("Enter a valid output folder.");
                return 1;
            }else{
                outputFolder = outputField.getText();
            }

            // Format Selector
            if(formatBox.getValue().toString().equalsIgnoreCase("Select Output Format : Default - TEXT")){
                format = "--format text ";
            }else{
                format = "--format " + formatBox.getValue().toString().toLowerCase() + " ";
            }

            // Login Field
            if(loginfieldCheck.isSelected()){
                if(emailField.getText().isEmpty() || !isValid(emailField.getText())){
                    messageBar.setText("Enter a valid email.");
                    return 1;
                }else{
                    email = emailField.getText();
                }
                if(passwordField.getText().isEmpty()){
                    messageBar.setText("Enter a valid password.");
                    return 1;
                }else{
                    password = passwordField.getText();
                }
            }

            // Selector
            if(selectorCheck.isSelected()){
                if(selectorBox.getValue().toString().equalsIgnoreCase("Default - All")){
                    selector = "--all ";
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("All")){
                    selector = "--all ";
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("First")){
                    try{
                        if(selectorField.getText().isEmpty()){
                            throw new Exception();
                        }else{
                            int n = Integer.parseInt(selectorField.getText());
                            selector = "--first " + n + " ";
                        }
                    }catch (Exception e){
                        messageBar.setText("Enter a valid number.");
                        return 1;
                    }
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("Last")){
                    try{
                        if(selectorField.getText().isEmpty()){
                            throw new Exception();
                        }else{
                            int n = Integer.parseInt(selectorField.getText());
                            selector = "--last " + n + " ";
                        }
                    }catch (Exception e){
                        messageBar.setText("Enter a valid number.");
                        return 1;
                    }
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("Page")){
                    try{
                        if(selectorField.getText().isEmpty()){
                            throw new Exception();
                        }else{
                            String[] parts = selectorField.getText().split(":");
                            if(parts.length > 2){
                                throw new Exception();
                            }
                            selector = "--page " + parts[0].trim() + " " + parts[1].trim() + " ";
                        }
                    }catch (Exception e){
                        messageBar.setText("Enter a valid URL.");
                        return 1;
                    }
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("Range")){
                    try{
                        if(selectorField.getText().isEmpty()){
                            throw new Exception();
                        }else{
                            String[] parts = selectorField.getText().split(":");
                            if(parts.length > 2){
                                throw new Exception();
                            }
                            int num1 = Integer.parseInt(parts[0].trim());
                            int num2 = Integer.parseInt(parts[1].trim());
                            selector = "--range " + num1 + " " + num2 + " ";
                        }
                    }catch (Exception e){
                        messageBar.setText("Enter a valid range.");
                        return 1;
                    }
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("Volumes")){
                    try{
                        if(selectorField.getText().isEmpty()){
                            throw new Exception();
                        }else{
                            messageBar.setText("Volumes selector option will be added in future updates.");
                        }
                    }catch (Exception e){
                        messageBar.setText("Enter a valid range.");
                        return 1;
                    }
                }else if(selectorBox.getValue().toString().equalsIgnoreCase("Chapters")){
                    try{
                        if(selectorField.getText().isEmpty()){
                            throw new Exception();
                        }else{
                            messageBar.setText("Chapters selector option will be added in future updates.");
                        }
                    }catch (Exception e){
                        messageBar.setText("Enter a valid indexes.");
                        return 1;
                    }
                }
            }else{
                selector = "--all ";
            }

            // File Packing
            if(packCheck.isSelected()){
                if (singlefileRadio.isSelected()){
                    filePacking = "--single ";
                }
                if (multiplefileRadio.isSelected()){
                    filePacking = "--multi ";
                }
            }else{
                filePacking = "--single ";
            }

            // Overwriting
            if(overwriteCheck.isSelected()){
                if(replacefolderRadio.isSelected()){
                    overwriteFolder = "-f ";
                }
                if(ignorefolderRadio.isSelected()){
                    overwriteFolder = "-i ";
                }
            }else{
                overwriteFolder = "-f ";
            }

            // Output File Name
            if(ofilenameCheck.isSelected()){
                outputFilename = "--filename " + ofilenameField.getText() + " ";
            }else{
                outputFilename = "--filename-only ";
            }

            // Add Source
            if(addsourceRadio.isSelected()){
                addSource = "--add-source-url ";
            }

            query = "&lncrawl -s " + link + " -o \"" + outputFolder + "\" " + selector + overwriteFolder + filePacking + addSource + outputFilename + format + "--suppress";
        }
        if(commandBox.getValue().toString().equalsIgnoreCase("Query")){
            if(urlField.getText().isEmpty()){
                return 1;
            }
            query = "&lncrawl -q " + urlField.getText() + " ";
        }
        if(commandBox.getValue().toString().equalsIgnoreCase("List Sources")){
            query = "&lncrawl --list-sources ";
            System.out.println(query);
        }
        if(commandBox.getValue().toString().equalsIgnoreCase("Help")){
            query = "&lncrawl -h ";
        }
        if(commandBox.getValue().toString().equalsIgnoreCase("Version")){
            query = "&lncrawl -v ";
        }
        return 0;
    }

    public void resetVariables(){
        link = "";
        outputFolder = "";
        format = "";
        email = "";
        password = "";
        selector = "";
        filePacking = "";
        overwriteFolder = "";
        outputFilename = "";
        addSource = "";
        username = "";
        query = "";
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    
    
    // UI Enable/Disable Functions
    public void setcommandUI(){
        String command = commandBox.getValue().toString();
        Boolean bvalue = false;
        if(command.equalsIgnoreCase("Default - Download") || command.equalsIgnoreCase("Download")){
            urlField.setText("");
            urlField.setPromptText("Enter the link here");
            urlField.setDisable(bvalue);
            outputField.setDisable(bvalue);
            folderSelector.setDisable(bvalue);
            formatBox.setDisable(bvalue);

            loginfieldCheck.setDisable(bvalue);
            loginfieldCheck.setSelected(bvalue);
            loginPaneUI();

            selectorCheck.setDisable(bvalue);
            selectorCheck.setSelected(bvalue);
            selectorPaneUI();

            packCheck.setDisable(bvalue);
            packCheck.setSelected(bvalue);
            packPaneUI();

            overwriteCheck.setDisable(bvalue);
            overwriteCheck.setSelected(bvalue);
            overwritePaneUI();

            ofilenameCheck.setDisable(bvalue);
            ofilenameCheck.setSelected(bvalue);
            ofilePaneUI();

            addsourceRadio.setDisable(bvalue);
            addsourceRadio.setSelected(bvalue);
        }else if(command.equalsIgnoreCase("Query")){
            bvalue = true;
            urlField.setText("");
            urlField.setPromptText("Enter your query here");
            urlField.setDisable(false);
            outputField.setDisable(bvalue);
            folderSelector.setDisable(bvalue);
            formatBox.setDisable(bvalue);

            loginfieldCheck.setDisable(bvalue);
            loginfieldCheck.setSelected(!bvalue);
            loginPaneUI();

            selectorCheck.setDisable(bvalue);
            selectorCheck.setSelected(!bvalue);
            selectorPaneUI();

            packCheck.setDisable(bvalue);
            packCheck.setSelected(!bvalue);
            packPaneUI();

            overwriteCheck.setDisable(bvalue);
            overwriteCheck.setSelected(!bvalue);
            overwritePaneUI();

            ofilenameCheck.setDisable(bvalue);
            ofilenameCheck.setSelected(!bvalue);
            ofilePaneUI();

            addsourceRadio.setDisable(bvalue);
            addsourceRadio.setSelected(!bvalue);
        }else{
            bvalue = true;
            urlField.setText("");
            urlField.setPromptText("Enter your link here");
            urlField.setDisable(bvalue);
            outputField.setDisable(bvalue);
            folderSelector.setDisable(bvalue);
            formatBox.setDisable(bvalue);

            loginfieldCheck.setDisable(bvalue);
            loginfieldCheck.setSelected(!bvalue);
            loginPaneUI();

            selectorCheck.setDisable(bvalue);
            selectorCheck.setSelected(!bvalue);
            selectorPaneUI();

            packCheck.setDisable(bvalue);
            packCheck.setSelected(!bvalue);
            packPaneUI();

            overwriteCheck.setDisable(bvalue);
            overwriteCheck.setSelected(!bvalue);
            overwritePaneUI();

            ofilenameCheck.setDisable(bvalue);
            ofilenameCheck.setSelected(!bvalue);
            ofilePaneUI();

            addsourceRadio.setDisable(bvalue);
            addsourceRadio.setSelected(!bvalue);
        }
    }
    
    public void openFolderChooser() {
        try {
            DirectoryChooser dChooser = new DirectoryChooser();
            File file = dChooser.showDialog(pane.getScene().getWindow());
            if(file != null){
                outputFolder = file.getAbsolutePath();
                outputField.setText(outputFolder);
            }
        }catch (Exception e){
            messageBar.setText("Error : " + e.getMessage());
        }
    }

    public void loginPaneUI(){
        Boolean value = false;
        if(loginfieldCheck.isSelected()){
            emailField.setDisable(value);
            passwordField.setDisable(value);
        }else{
            emailField.setDisable(!value);
            passwordField.setDisable(!value);
        }
    }

    public void selectorPaneUI(){
        Boolean value = false;
        if(selectorCheck.isSelected()){
            selectorBox.setDisable(value);
            selectorField.setDisable(value);
        }else{
            selectorBox.setDisable(!value);
            selectorField.setDisable(!value);
        }
    }

    public void packPaneUI(){
        Boolean value = false;
        if(packCheck.isSelected()){
            singlefileRadio.setDisable(value);
            multiplefileRadio.setDisable(value);
        }else{
            singlefileRadio.setDisable(!value);
            multiplefileRadio.setDisable(!value);
        }
    }

    public void overwritePaneUI(){
        Boolean value = false;
        if(overwriteCheck.isSelected()){
            replacefolderRadio.setDisable(value);
            ignorefolderRadio.setDisable(value);
        }else{
            replacefolderRadio.setDisable(!value);
            ignorefolderRadio.setDisable(!value);
        }
    }

    public void ofilePaneUI(){
        Boolean value = false;
        if(ofilenameCheck.isSelected()){
            ofilenameField.setDisable(value);
        }else{
            ofilenameField.setDisable(!value);
        }
    }

}