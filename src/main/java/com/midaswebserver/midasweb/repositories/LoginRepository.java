package com.midaswebserver.midasweb.repositories;

import com.midaswebserver.midasweb.models.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LoginRepository extends JpaRepository<Login, Long> {

    List<Login> findByUsernameIgnoreCase(String username);
}
