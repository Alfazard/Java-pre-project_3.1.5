package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alfazard on 10.08.2023
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserDao userDao;
    private final RoleService roleService;
    private final PasswordEncoder encoder;

    public UserServiceImpl(UserDao userDao, RoleService roleService, @Lazy PasswordEncoder encoder) {
        this.userDao = userDao;
        this.roleService = roleService;
        this.encoder = encoder;
    }

    @Override
    public List<User> getUsers() {
        return userDao.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userDao.save(settingRoles(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(User user) {
        User editUser = userDao.findByEmail(user.getEmail());
        if (!editUser.getPassword().equals(user.getPassword())) {
            editUser.setPassword(encoder.encode(user.getPassword()));
        }
        if (user.getRoles() == null) {
            user.setRoles(editUser.getRoles());
        }
        userDao.save(settingRoles(user));
    }

    @Override
    public User getUser(Long id) {
        return userDao.findById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeUser(Long id) {
        userDao.deleteById(id);
    }

    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with email '%s' not found", email));
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getRoleName())).collect(Collectors.toList());
    }

    private User settingRoles(User user) {
        var roles = user.getRoles();
        var roleList = roleService.getRolesList();
        var list = new HashSet<Role>();
        for (Role role : roleList) {
            for (Role userRole : roles) {
                if (role.getRoleName().equals(userRole.getRoleName())) {
                    list.add(role);
                }
            }
        }
        user.setRoles(list);
        return user;
    }
}