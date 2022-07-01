package com.example.lightnovelcrawler;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.io.*;
import java.nio.charset.Charset;

public class LNController {
    @FXML
    private Label welcomeText;

    @FXML
    private TextArea ta;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    public void a() throws IOException {
//        try{
//            ProcessBuilder builder = new ProcessBuilder(
//                    "cmd.exe", "/c", "cd \"C:\\Program Files\\");
//            builder.redirectErrorStream(true);
//            Process p = builder.start();
//            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line;
//            while (true) {
//                line = r.readLine();
//                if (line == null) { break; }
//                System.out.println(line);
//            }
//        }catch (Exception e){
//            System.out.println(e.getMessage());
//        }
        try{
//            ProcessBuilder builder = new ProcessBuilder(
//                    "cmd.exe", "/c", "cd \"C:\\Users\\GameDemons\\IdeaProjects\\Lightnovel Crawler\\src\\main\\java\\com\\example\\lightnovelcrawler\" && dir");
//            builder.redirectErrorStream(true);
//            ProcessBuilder builder2 = new ProcessBuilder(
//                    "cmd.exe","lncrawl","-h");
//            Process p = builder.start();
//            builder2.redirectErrorStream(true);
//            Process p2 = builder2.start();
//            BufferedReader r = new BufferedReader(new InputStreamReader(p2.getInputStream()));
//            String line;
//            String a = "";
//            while (true) {
//                line = r.readLine();
//                if (line == null) { break; }
//                a = a + line + "\n";
//                System.out.println(line);
//            }






//            String[] cmd = {"cmd.exe", "/c", "cd \"C:\\Users\\GameDemons\\IdeaProjects\\Lightnovel Crawler\\src\\main\\java\\com\\example\\lightnovelcrawler\"","lncrawl","-h"};
//            ProcessBuilder p1 = new ProcessBuilder(cmd);
//            p1.redirectErrorStream(true);
//            Process p = p1.start();
//            InputStreamReader r = new InputStreamReader(p.getInputStream());
//            BufferedReader br = new BufferedReader(r);
//
//            String line;
//            while ((line = br.readLine()) != null){
//                System.out.println(line);
//            }
//
//            p.waitFor();






            Process processDuration = new ProcessBuilder("cmd.exe", "/c", "cd \"C:\\Users\\GameDemons\\IdeaProjects\\Lightnovel Crawler\\src\\main\\java\\com\\example\\lightnovelcrawler\"","&lncrawl -h").redirectErrorStream(true).start();
            StringBuilder strBuild = new StringBuilder();
            try (BufferedReader processOutputReader = new BufferedReader(new InputStreamReader(processDuration.getInputStream(), Charset.defaultCharset()));) {
                String line = "";
                while ((line = processOutputReader.readLine()) != null) {
                    strBuild.append(line + System.lineSeparator());
                }
                processDuration.waitFor();
            }
            String outputJson = strBuild.toString().trim();
            ta.setText(outputJson);
//            final InputStream stream = p.getInputStream();
//            new Thread(new Runnable() {
//                public void run() {
//                    BufferedReader reader = null;
//                    try {
//                        reader = new BufferedReader(new InputStreamReader(stream));
//                        String line = null;
//                        while ((line = reader.readLine()) != null) {
//                            System.out.println(line);
//                        }
//                    } catch (Exception e) {
//                        System.out.println(e.getMessage());
//                    } finally {
//                        if (reader != null) {
//                            try {
//                                reader.close();
//                            } catch (IOException e) {
//                                // ignore
//                            }
//                        }
//                    }
//                }
//            }).start();
//            ta.setText();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }


    }
}