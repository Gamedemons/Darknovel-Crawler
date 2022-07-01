module com.example.lightnovelcrawler {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.lightnovelcrawler to javafx.fxml;
    exports com.example.lightnovelcrawler;
}