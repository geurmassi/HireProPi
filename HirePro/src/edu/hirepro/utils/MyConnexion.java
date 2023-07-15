/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.hirepro.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author samsung
 */
public class MyConnexion {
    private String url="jdbc:mysql://localhost:3306/hirePro";
    private String login="root";
    private String pwd="";
    Connection cnx;
    
    public static MyConnexion instance;

    private MyConnexion() {
        try{
        cnx = DriverManager.getConnection(url, login, pwd);
        System.out.println("connexion établie");
        }catch (SQLException ex){
           System.out.println(ex.getMessage()); 
        }
        
    }

    public Connection getCnx() {
        return cnx;
    }

    public static MyConnexion getInstance() {
        if (instance== null)
        {
            instance = new MyConnexion();
        }
        return instance;
    }
    
    
}
