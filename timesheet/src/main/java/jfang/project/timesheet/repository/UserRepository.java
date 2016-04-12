package jfang.project.timesheet.repository;

import jfang.project.timesheet.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jfang on 5/23/15.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserId(Long userId);

    User findByUsername(String username);

    @SuppressWarnings("unchecked")
    User save(User user);

}
