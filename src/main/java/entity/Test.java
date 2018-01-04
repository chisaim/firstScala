package entity;

import org.apache.spark.sql.DataFrameReader;

import java.sql.*;

public class Test {

    private static String url = "jdbc:mysql://192.168.102.117:3306/site?characterEncoding=utf8&allowMultiQueries=true&useSSL=false";

    private static String driver = "com.mysql.jdbc.Driver";

    private static String username = "chisaim";

    private static String passwd = "chisaim";


    public static void main(String[] args) {
        try {
            Class.forName(driver);

            Connection connection = DriverManager.getConnection(url,username,passwd);

//            PreparedStatement pst = connection.prepareStatement("select name from site.sogoumess");
            PreparedStatement pst = connection.prepareStatement("select strategies from site.strategyTable");

            ResultSet rs = pst.executeQuery();

            while(rs.next()){
//                System.out.println(rs.getString("name"));
                System.out.println(rs.getString("strategies"));
                System.out.println();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
