package jfang.project.timesheet.repository;

import jfang.project.timesheet.model.Manager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    @SuppressWarnings("unchecked")
    Manager save(Manager manager);

    @Query("select m from Manager m where m.user.username = :username")
    //@Query(value = "select * from MANAGER m join USER u on m.USER_ID = u.USER_ID where u.USER_ID = :userId", nativeQuery = true)
    Manager findByUsername(@Param("username") String username);
}
