package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String loadRegister() {
        return "register";
    }
    
    //Simple and bad registering that allows sql injection
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String submitRegister(@RequestParam String username, @RequestParam String password) throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/db_signup?allowMultiQueries=true";
               
        Connection connection = null;
        try {
        connection = DriverManager.getConnection(url, "signup", "password");
        
        DatabaseMetaData dbm = connection.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "User", null);
        if (tables.next()) {
          
        }
        else {
            String create = "CREATE TABLE User(username varchar(100), password varchar(100));"; 
            Statement statement = connection.createStatement();
            statement.execute(create);
        }
        String update = "INSERT INTO User (username, password) VALUES ('" + username + "', '" + password + "');";      
        System.out.println(update);
        
        Statement statement = connection.createStatement();
        
        statement.execute(update);
        } catch (SQLException e) {}
        
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {}
            }
        }
        return "redirect:/login";
    }

}
