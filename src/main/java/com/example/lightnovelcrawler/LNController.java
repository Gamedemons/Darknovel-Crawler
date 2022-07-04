package com.example.lightnovelcrawler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class LNController {
    @FXML
    private TextArea console;

    @FXML
    private TextField outputLoc;

    @FXML
    private TextField urlBox;

    @FXML
    private Label messageBar;

    @FXML
    public void a() {
        String url = urlBox.getText();
        String loc = outputLoc.getText();

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



//                    StringBuilder strBuild = new StringBuilder();
//                    try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream(), Charset.defaultCharset()))) {
//                        String line;
//                        while ((line = processOutputReader.readLine()) != null) {
//                            strBuild.append(line).append(System.lineSeparator());
//                        }
//                        processDuration.waitFor();
//                    }
//                    String outputJson = strBuild.toString().trim();
//                    ta.setText(outputJson);

                }catch (Exception e){
                    messageBar.setText(e.getMessage());
                }
            }
        };
        new Thread(task).start();





    }
}