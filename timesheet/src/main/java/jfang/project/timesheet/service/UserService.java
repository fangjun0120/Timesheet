package jfang.project.timesheet.service;

import jfang.project.timesheet.model.Manager;
import jfang.project.timesheet.model.User;

public interface UserService {

    Manager getManagerByUsername(String username);

    Long registerNewManager(User user);

    String resetPasswordFor(String username);

    void disableUser(String username);

    Long updateUser(User userNew);

    User getUserByUsername(String username);
}