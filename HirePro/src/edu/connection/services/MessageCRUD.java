/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.connection.services;

import edu.connection.entities.Message;
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
public class MessageCRUD implements ICRUD<Message> {

    @Override
    public void addEntity(Message t) {
        String requete = "INSERT INTO Messages (msg, dateSend, 	idUserSend, idUserReceive ) VALUES" + "(?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, t.getMsg());
            pst.setTimestamp(2, t.getDateSend());
            pst.setInt(3, t.getIdUserSend());
            pst.setInt(4, t.getIdUserReceive());
            pst.executeUpdate();
            System.out.println("Message  added");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Message> displayEntities() {
        List<Message> myList = new ArrayList<>();
        try {
            String requete = "SELECT*FROM Messages";
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                Message M= new Message();
                M.setIdMsg(rs.getInt(1));
                M.setMsg(rs.getString(2));
                M.setDateSend(rs.getTimestamp(3));
                M.setIdUserSend(rs.getInt(4));
                M.setIdUserReceive(rs.getInt(5));

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
    public void modifier(Message t) {
    }

}
