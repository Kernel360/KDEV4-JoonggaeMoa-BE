module JoonggaeMoa.main {
    requires spring.context;
    requires spring.beans;
    requires spring.boot.autoconfigure;
    requires spring.webflux;
    requires spring.web;
    requires static lombok;
    requires spring.core;
    requires spring.data.jpa;
    requires org.slf4j;
    requires com.fasterxml.jackson.databind;
    requires org.apache.poi.poi;
    requires lettuce.core;
    requires org.apache.tomcat.embed.core;
    requires spring.security.core;
    requires jjwt.api;
    requires jakarta.persistence;
}