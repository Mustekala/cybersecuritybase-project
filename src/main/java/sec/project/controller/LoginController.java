package sec.project.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import java.sql.*;

@Controller
public class LoginController {

    @RequestMapping("*")
    public String defaultMapping() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loadLogin() {
        return "login";
    }
    
    //Simple and bad login
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String submitLogin(@RequestParam String username, @RequestParam String password) throws SQLException {
        
        String url = "jdbc:mysql://localhost:3306/db_signup?allowMultiQueries=true";
        
        Connection connection = DriverManager.getConnection(url, "signup", "password");

        String query = "SELECT * FROM User";
        
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()) {
            String dbUsername = resultSet.getString("username");
            String dbPassword = resultSet.getString("password");
            if (dbUsername.equals(username) && dbPassword.equals(password)) {
                return "form";
            } 
        }

        connection.close();
        return "redirect:/login";
    }

}
