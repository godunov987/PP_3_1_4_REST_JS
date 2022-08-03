package ru.kata.spring.boot_security.demo.servise;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.RoleType;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImp implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void add(User user, List<String> role) {
        user.setRoles(getAllRole().stream()
                .filter(x -> role.contains(x.getRole().name()))
                .collect(Collectors.toSet()));
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user, List<String> role) {
        User oldUser = userRepository.findById(user.getId()).get();
        List<Role> listRole = getAllRole();
        if (user.getPassword().length() == 0) {
            user.setPassword(oldUser.getPassword());
        }
        if (role == null) {
            user.setRoles(oldUser.getRoles());
        } else {
            user.setRoles(listRole
                    .stream()
                    .filter(x -> role.contains(x.getRole().name()))
                    .collect(Collectors.toSet()));
        }
        userRepository.save(user);
    }

    @Override
    public void remove(long id) {
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(s);
    }

    @Override
    public void addDefaultRoles() {
        roleRepository.save(new Role(RoleType.ADMIN));
        roleRepository.save(new Role(RoleType.USER));
    }
}