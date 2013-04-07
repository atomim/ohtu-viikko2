package ohtu.data_access;

import ohtu.domain.User;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.File;
//import org.springframework.stereotype.Component;
public class FileUserDao implements UserDao {

    private List<User> users;
    //@Component
    public FileUserDao(String file) {
        users = new ArrayList<User>();
	try{
           Scanner fileScanner = new Scanner(new File(file));
           while (fileScanner.hasNextLine()) {
               users.add(new User(fileScanner.nextLine(),fileScanner.nextLine()));
	   }
	}catch(Exception e){}
    }        

    @Override
    public List<User> listAll() {
        return users;
    }

    @Override
    public User findByName(String name) {
        for (User user : users) {
            if (user.getUsername().equals(name)) {
                return user;
            }
        }

        return null;
    }

    @Override
    public void add(User user) {
        users.add(user);
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }
}
