module bd.northwind {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires kernel; 
    requires layout;
    requires io;
    requires java.desktop;
    requires javafx.swing;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    
    opens bd.northwind to javafx.fxml;
    exports bd.northwind;
}
