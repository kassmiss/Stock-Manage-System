package com.grlife.rela_prog.service;

import com.grlife.rela_prog.repositiory.LoginRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginService implements UserDetailsService {

    private final LoginRepository loginRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return loginRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException((username)));
    }

}
