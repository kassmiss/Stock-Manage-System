package com.grlife.rela_prog.repositiory;

import com.grlife.rela_prog.domain.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<UserInfo, Long> {

    Optional<UserInfo> findByEmail(String email);

}
