package org.byovsiannikov.sticky_notes.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.byovsiannikov.sticky_notes.entitiy.UserEntity;
import org.byovsiannikov.sticky_notes.repositoriy.RoleRepository;
import org.byovsiannikov.sticky_notes.repositoriy.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserEntity findUserByName(String name) {
        return userRepository.findByName(name).orElseThrow
                (() -> new UsernameNotFoundException(
                        String.format("User %s not found.", name)
                ));
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = findUserByName(username);

        return new User(
                user.getName(),
                user.getPassword(),
                user.getUserRoleList()
                        .stream()
                        .map(el -> new SimpleGrantedAuthority(el.getRole()))
                        .toList()


        );
    }
    public void CreateNewUser(UserEntity newUser){

        if(userRepository.findByName(newUser.getName())!=null){
            logger.error("User {} already exists",newUser.getName());
        }
        newUser.setUserRoleList(List.of(roleRepository.findByRoleName("ROLE_USER").get()));
        userRepository.save(newUser);
    }

}
