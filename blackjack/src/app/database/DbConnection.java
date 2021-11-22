package app.database;

import app.Controller;
import app.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbConnection {
    public static Connection connect() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC");
            con = DriverManager.getConnection("jdbc:sqlite:Database.db"); // connecting to our database
        } catch (ClassNotFoundException | SQLException e ) {
            // TODO Auto-generated catch block
            System.out.println(e+"");
        }

        return con;
    }

    public static void insert(String login, String password, FileInputStream fis, int length) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO users(login, password, avatar) VALUES(?,?,?) ";
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, password);
            ps.setBinaryStream(3, fis, length);
            ps.execute();
            System.out.println("Data has been inserted!");
        } catch(SQLException e) {
            System.out.println(e.toString());
            // always remember to close database connections
        } finally {
            try{
                ps.close();
                con.close();
            } catch(SQLException e) {
                System.out.println(e.toString());
            }

        }
    }


    public static String  Login(String login, String password){
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try{
            String sql = "SELECT count(1) FROM users WHERE login = '" + login +"' AND password = '" + password + "'";
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    return login;
                }else{
                    return "0";
                }
            }
        }catch (SQLException e){
            System.out.println(e.toString());
        }
        return "0";
    }

    public static Image setAvatar(String login) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Image image1=null;
        try {
            String sql = "Select avatar from users where login = '" + login + "'";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            InputStream is = rs.getBinaryStream("avatar");
            OutputStream os = new FileOutputStream(new File("photo.png"));
            byte[]content = new byte[1024];
            int size = 0;
            while((size=is.read(content))!= -1)
            {
                os.write(content,0,size);
            }
            os.close();
            is.close();
            image1 = new Image("file:photo.png",300,300,true,true);

        } catch(SQLException | FileNotFoundException e) {
            System.out.println(e.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }
            return image1;
        }
    }
}