/**
 * @author az
 * @since 2020-03-31
 */
module little.java {
    requires java.base;
    requires com.google.common;
    requires hutool.all;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.web;
    requires java.management;
    requires org.apache.commons.codec;
    requires spring.context;
    requires org.apache.tomcat.embed.core;
    requires org.apache.commons.lang3;
    requires com.google.gson;
    requires spring.core;
    requires spring.beans;

    exports cn.az.code.util;
}