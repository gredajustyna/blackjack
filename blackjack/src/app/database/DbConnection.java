package app.database;

import app.Controller;
import app.Main;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.mindrot.jbcrypt.BCrypt;

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

            String hashed = BCrypt.hashpw(password, "$2a$10$qtlpCtO6YXgYP1uaiVt9Mu");
            // Check that an unencrypted password matches one that has
            // previously been hashed


            String sql = "INSERT INTO users(login, password, avatar) VALUES(?,?,?) ";
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.setString(2, hashed);
            ps.setBinaryStream(3, fis, length);
            ps.execute();
            System.out.println("Data has been inserted!");
        } catch(SQLException e) {
            System.out.println(e.toString());
            System.out.println("tuu");
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

    public static List<String> selectAllLogins(){
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> users = new ArrayList<>();

        try {
            String sql = "SELECT Login FROM users";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {

                String login = rs.getString("Login");
                users.add(login);
            }

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                rs.close();
                ps.close();
                con.close();
            } catch(SQLException e) {
                System.out.println(e.toString());
            }
        }
        return users;
    }


    public static String  Login(String login, String password){
        Connection con = DbConnection.connect();
        try{
            String hashed = BCrypt.hashpw(password, "$2a$10$qtlpCtO6YXgYP1uaiVt9Mu");
            String sql = "SELECT count(1) FROM users WHERE login = '" + login +"' AND password = '" + hashed + "'";
            Statement statement = con.createStatement();
            ResultSet queryResult = statement.executeQuery(sql);
            while (queryResult.next()){
                if(queryResult.getInt(1)==1){
                    return login;
                }else{
                    con.close();
                    queryResult.close();
                    return "0";
                }
            }
            con.close();
            queryResult.close();
        }catch (SQLException e){
            System.out.println(e.toString());
        } finally{
        try {
            con.close();
        } catch(SQLException e) {
            System.out.println(e.toString());
        }
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

    public static void updateUser(String login, int cards, int time) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE users set Matches = Matches +1 , Cards_used = Cards_used + "  + cards + ", Time_played = Time_played + " + time + " WHERE Login = ?" ;
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.execute();

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
    }

    public static void updateWin(String login) {
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE users set Wins = Wins + 1 WHERE Login = ?" ;
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            ps.execute();

        } catch (SQLException e) {
            // TODO: handle exception
            System.out.println(e.toString());
        }
    }

    public static int[] getData(String login) {

        int[] data = new int[4];
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select Matches, Wins, Cards_used, Time_played from users WHERE Login = ?" ;
            ps = con.prepareStatement(sql);
            ps.setString(1, login);
            rs = ps.executeQuery();


            data[0] = rs.getInt(1);
            data[1] = rs.getInt(2);
            data[2] = rs.getInt(3);
            data[3] = rs.getInt(4);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }

        }
        return data;
    }

    public static int[] getCards() {

        int[] data = new int[13];
        Connection con = DbConnection.connect();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "Select 2, 3, 4, 5, 6, 7, 8, 9, 10, walet, dama, krol, ass from cards where ID = ? " ;
            ps = con.prepareStatement(sql);
            ps.setString(1, "karty");
            rs = ps.executeQuery();

            data[0] = rs.getInt(1);
            data[1] = rs.getInt(2);
            data[2] = rs.getInt(3);
            data[3] = rs.getInt(4);
            data[4] = rs.getInt(5);
            data[5] = rs.getInt(6);
            data[6] = rs.getInt(7);
            data[7] = rs.getInt(8);
            data[8] = rs.getInt(9);
            data[9] = rs.getInt(10);
            data[10] = rs.getInt(11);
            data[11] = rs.getInt(12);
            data[12] = rs.getInt(13);

        } catch(SQLException e) {
            System.out.println(e.toString());
        } finally {
            try{
                rs.close();
                ps.close();
                con.close();
            } catch (SQLException e) {
                // TODO: handle exception
                System.out.println(e.toString());
            }

        }
        return data;
    }


}