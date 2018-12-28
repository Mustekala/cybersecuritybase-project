package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.config.CustomUserDetailsService;
import sec.project.domain.Signup;
import sec.project.repository.SignupRepository;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
        
        String url = "jdbc:mysql://localhost:3306/db_signup";
        
        Connection connection = DriverManager.getConnection (url, "signup", "password");

        String update = "INSERT INTO User (username, password) VALUES ('" + username + "', '" + password + "');";
        
        Statement statement = connection.createStatement();

        statement.executeUpdate(update);

        connection.close();
        return "redirect:/login";
    }

}
