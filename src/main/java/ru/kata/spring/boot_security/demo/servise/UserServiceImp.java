package ru.kata.spring.boot_security.demo.servise;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.RoleType;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.model.UserModel;
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
    public List<UserModel> getAll() {
        List<User> listAllUser = userRepository.findAll();
        List<UserModel> listAll = listAllUser.stream().map(UserModel::getModel)
                .collect(Collectors.toList());
        return listAll;
    }

    @Override
    @Transactional(readOnly = true)
    public UserModel getUser(long id) {
        return UserModel.getModel(userRepository.findById(id).get());
    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }

    @Override
    public void add(UserModel model) {
        User user = model.getUser(new User());
        user.setRoles(getAllRole().stream()
                .filter(x -> model.getRoles().contains(x.getRole().name()))
                .collect(Collectors.toSet()));
        userRepository.save(user);
    }

    @Override
    public UserModel updateUser(UserModel model) {
        User oldUser = userRepository.findById(model.getId()).get();
        User user = model.getUser(oldUser);
        List<Role> listRole = getAllRole();
        if (model.getRoles() == null || model.getRoles().size() == 0) {
            user.setRoles(oldUser.getRoles());
        } else {
            user.setRoles(listRole
                    .stream()
                    .filter(x -> model.getRoles().contains(x.getRole().name()))
                    .collect(Collectors.toSet()));
        }
        return UserModel.getModel(userRepository.save(user));
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
    @Transactional(readOnly = true)
    public UserModel loadUserModelByUsername(String s) throws UsernameNotFoundException {
        return UserModel.getModel((User) userRepository.findUserByEmail(s));
    }

    @Override
    public void addDefaultRoles() {
        roleRepository.save(new Role(RoleType.ADMIN));
        roleRepository.save(new Role(RoleType.USER));
    }
}