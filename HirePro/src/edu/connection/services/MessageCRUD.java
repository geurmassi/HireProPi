/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.connection.services;

import edu.connection.entities.Messages;
import edu.connection.interfaces.ICRUD;
import edu.connection.utils.MyConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author roamsmart
 */
public class MessageCRUD implements ICRUD<Messages> {

    @Override
    public void addEntity(Messages t) {
        String requete = "INSERT INTO Messages (msg, dateSend, 	idUserSend, idUserReceive,file,fileName ) VALUES" + "(?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getMsg());
            pst.setTimestamp(2, t.getDateSend());
            pst.setInt(3, t.getIdUserSend());
            pst.setInt(4, t.getIdUserReceive());
            pst.setString(5, t.getFile());
            pst.setString(6, t.getFileName());
            pst.executeUpdate();
            System.out.println("Message  added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Messages> displayEntities() {
        List<Messages> myList = new ArrayList<>();
        try {
            String requete = "SELECT*FROM Messages";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Messages M= new Messages();
                M.setIdMsg(rs.getInt(1));
                M.setMsg(rs.getString(2));
                M.setDateSend(rs.getTimestamp(3));
                M.setIdUserSend(rs.getInt(4));
                M.setIdUserReceive(rs.getInt(5));
                M.setFile(rs.getString(6));
                M.setFileName(rs.getString(7));
                myList.add(M);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return myList;
       
    }

    @Override
    public void supprimer(int id) {
        String requet = "DELETE FROM Messages WHERE idMsg = " + id;
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requet)) {
            pst.executeUpdate(requet);

            

            System.out.println("Messages supprimé");

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void modifier(Messages t) {
    }

    @Override
    public void updateEntity(Messages t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteEntity(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
