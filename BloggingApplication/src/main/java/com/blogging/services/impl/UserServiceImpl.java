package com.blogging.services.impl;

import com.blogging.config.AppConstants;
import com.blogging.entities.Role;
import com.blogging.entities.User;
import com.blogging.exceptions.ResourceNotFoundException;
import com.blogging.payloads.RoleDto;
import com.blogging.payloads.UserDto;
import com.blogging.repositories.RoleRepository;
import com.blogging.repositories.UserRepository;
import com.blogging.services.UserService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private ModelMapper modelMapper;

    private PasswordEncoder passwordEncoder;// To encode the password before saving into database

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = dtoToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return userDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, Integer userId) {

        User user = userRepository.findById(userId).orElseThrow(()
                -> new ResourceNotFoundException("User", "Id", userId));
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setAbout(userDto.getAbout());

        Set<Role> roles = user.getRoles();
        for (RoleDto roleDto : userDto.getRoles()) {
            Role role = roleRepository.findByRoleName(roleDto.getRoleName());
            roles.add(role);
        }

        user.setRoles(roles);

        User updatedUser = userRepository.save(user);
        return userToDto(updatedUser);
    }

    @Override
    public UserDto getUserById(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return userToDto(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
       return userRepository.findAll()
                .stream()
                .map(this::userToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteUser(Integer userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
    }

    @Override
    public UserDto registerNewUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role role = roleRepository.findById(AppConstants.USER_ACCESS).get();
        user.getRoles().add(role);

        User registeredUser = userRepository.save(user);
        return modelMapper.map(registeredUser,UserDto.class);
    }

    private User dtoToUser(UserDto dto){
        /*User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setAbout(dto.getAbout());
        user.setPassword(dto.getPassword());
        return user;*/
       return modelMapper.map(dto,User.class);
    }

    private UserDto userToDto(User user){
       /* UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setAbout(user.getAbout());
        dto.setPassword(user.getPassword());
        return dto;*/
        return modelMapper.map(user,UserDto.class);
    }

}
