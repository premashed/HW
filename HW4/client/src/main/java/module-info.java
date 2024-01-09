module org.example.client {
    requires javafx.controls;
    requires java.naming;
    requires spring.web;
    requires spring.core;
    opens org.example.client;
    exports org.example.client;
}
