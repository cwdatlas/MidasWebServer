module midasweb.main {
    requires alphavantage.java;
    requires jakarta.persistence;
    requires jakarta.validation;
    requires org.apache.tomcat.embed.core;
    requires org.hibernate.orm.core;
    requires org.slf4j;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    //requires spring.boot.test.autoconfigure; //This breaks
    requires spring.context;
    requires spring.data.jpa;
    requires spring.tx;
    requires spring.web;
    requires spring.webmvc;
}