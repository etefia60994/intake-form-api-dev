package com.bfl.intakeform.security;

import com.bfl.intakeform.exception.ResourceNotFoundException;
import com.bfl.intakeform.model.CaseManager;
import com.bfl.intakeform.repository.CaseManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    CaseManagerRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CaseManager user = userRepository.findByUserName(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username or email : " + username)
                );
        return UserPrincipal.create(user);
    }

    @Transactional
    public UserPrincipal loadUserPrincipalById(Long id) {
        CaseManager user = userRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", id)
        );

        return UserPrincipal.create(user);
    }
      @Transactional
    public CaseManager loadUserById(Long id){
          CaseManager user = userRepository.findById(id).orElseThrow(
                  () -> new ResourceNotFoundException("User", "id", id)
           );
       return user;
      }

}
