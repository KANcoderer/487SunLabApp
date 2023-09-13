package com.example.sunlabswipe;

import java.sql.*;

public class ScanSystem {
    private static Connection conn;
    // Connect to the database
    public static void connect() {
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:C:/projects/SunLabSwipe/identifier.sqlite");
            System.out.println("Connection Success");
        }catch(Exception e){
            System.out.println("Connection Fail");
        }
    }

    // Close the connection to the database
    public static void close() throws SQLException {
        conn.close();
    }
    public static ResultSet browse() throws SQLException {
        String sql = "Select * from access";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }

    public static String getName(int Id) throws SQLException{
        String sql = "SELECT name FROM users where Id =?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, Id);
        ResultSet rs =pstmt.executeQuery();
        rs.next();
        return rs.getString("name");
    }

    public static ResultSet filteredBrowse(String searchId, String accessDate, String startTime, String endTime) throws SQLException {
        StringBuilder sql = new StringBuilder("Select * from access where ");
        boolean idFilter=false;
        boolean dateFilter=false;
        boolean sTimeFilter=false;
        boolean eTimeFilter=false;
        int id=-1;

        if(!searchId.equals("")){
            sql.append("id = ?");
            idFilter=true;
            id=Integer.parseInt(searchId);
        }
        if(!accessDate.equals("")){
            if(idFilter){
                sql.append(" and ");
            }
            sql.append("Date(date)<DATE(?,'+1 day')and Date(date)>= Date(?)");
            dateFilter=true;
        }
        if(!startTime.equals("")){
            if(idFilter ||dateFilter){
                sql.append(" and ");
            }
            sql.append("time(date)>= time(?)");
            sTimeFilter=true;
        }
        if(!endTime.equals("")){
            if(idFilter ||dateFilter||sTimeFilter){
                sql.append(" and ");
            }
            eTimeFilter=true;
            sql.append("time(date)<= time(?)");
        }
        PreparedStatement pstmt = conn.prepareStatement(sql.toString());
        boolean[] filters={idFilter,dateFilter,sTimeFilter,eTimeFilter};

        int j=0;
        for (int i =0; i<filters.length;i++){
            if(filters[i]){
                j++;
                switch (i) {
                    case 0 -> {
                        pstmt.setInt(j, id);
                    }
                    case 1 -> {
                        pstmt.setString(j, accessDate);
                    }
                    case 2 -> {
                        pstmt.setString(j, startTime);
                    }
                    case 3 -> {
                        pstmt.setString(j, endTime);
                    }
                }
                if(i==1){
                    j++;
                    pstmt.setString(j,accessDate);
                }
            }
        }
        return pstmt.executeQuery();
    }

    // Add an access time to the database
    public static void addAccess(int id, Timestamp ts, String inside) {
        try {
            if(getStatus(id)) {
                boolean in= inside.equals("IN");
                String sql = "INSERT INTO access (Id, Name, date , inside) VALUES (?, ?, ?,?)";
                PreparedStatement pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, id);
                pstmt.setString(2, getName(id));
                pstmt.setString(3, ts.toString());
                pstmt.setBoolean(4, in);
                pstmt.executeUpdate();
                System.out.println("Access Allowed");
            }else{
                System.out.println("Access Denied");
            }
        }catch (SQLException sqle){
            System.out.println("Access Denied");
        }
    }
    ;
    // Remove a user from the database
    public static void removeAccess() throws SQLException {
        String sql = "delete FROM access WHERE datetime(date) < DATETIME('now', 'localtime','-5 years')";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.executeUpdate();
    }
    public static boolean checkUser(int Id){
        try {
            String sql = "SELECT id FROM users where Id =?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Id);
            ResultSet rs =pstmt.executeQuery();
            return rs.next();
        }catch (SQLException sqle){

            return false;
        }
    }
    // Add a user to the database
    public static boolean addUser(String id, String name, String type, String status)  {
        boolean stat;
        if(status.equals("Active")){
            stat=true;
        }else if(status.equals("Inactive")){
            stat=false;
        }else{
            return false;
        }
        if (checkUser(Integer.parseInt(id))||id.length()!=9){
            return false;
        }
        try {
            String sql = "INSERT INTO users (Id, Name, type, status) VALUES (?, ?, ?,?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Integer.parseInt(id));
            pstmt.setString(2, name);
            pstmt.setString(3, type);
            pstmt.setBoolean(4,stat);
            pstmt.executeUpdate();
            return true;
        }catch (SQLException sqle){
            return false;
        }
    }
    public static ResultSet SearchUser(int id) throws SQLException {
        String sql = "Select * from users where Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1,id);
        return pstmt.executeQuery();
    }
    // Edit a user in the database
    public static void editUserName(int id, String name) throws SQLException {
        String sql = "UPDATE users SET name = ? WHERE Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }
    public static void editUserNameAccess(int id, String name) throws SQLException {
        String sql = "UPDATE access SET name = ? WHERE Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, name);
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }
    public static boolean getStatus(int id) throws SQLException{
        String sql = "SELECT status FROM users where Id =?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        ResultSet rs =pstmt.executeQuery();
        rs.next();
        return rs.getBoolean("status");
    }
    public static void changeStatus(int id) throws SQLException {
        String sql = "UPDATE users SET status = ? WHERE Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setBoolean(1, !getStatus(id));
        pstmt.setInt(2, id);
        pstmt.executeUpdate();
    }
    // Remove a user from the database
    public static void removeUser(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE Id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, id);
        pstmt.executeUpdate();
    }
    public static ResultSet browseUsers() throws SQLException {
        String sql = "Select * from users";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        return pstmt.executeQuery();
    }
}
