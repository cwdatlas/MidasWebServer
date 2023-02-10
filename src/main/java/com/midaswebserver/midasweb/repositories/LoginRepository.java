package com.midaswebserver.midasweb.repositories;

import com.midaswebserver.midasweb.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoginRepository extends JpaRepository<User, Long> {

    List<User> findByUsernameIgnoreCase(String username);
}
