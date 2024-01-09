module org.example.server {
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;
    requires spring.web;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.core;
    requires spring.context;
    requires spring.beans;

    opens org.example.server to org.hibernate.orm.core, spring.boot, spring.core, spring.web, org.springframework;
    exports org.example.server;
}
