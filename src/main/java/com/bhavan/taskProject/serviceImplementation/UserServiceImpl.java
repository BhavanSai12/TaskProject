package com.bhavan.taskProject.serviceImplementation;

import com.bhavan.taskProject.entity.Users;
import com.bhavan.taskProject.payload.UserDto;
import com.bhavan.taskProject.repository.UserRepository;
import com.bhavan.taskProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
//    @Autowired
////    private PasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        //userDto is not an entity of users
//        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        Users user=userDtoToEntity(userDto);//converted userData to Users
        Users savedUser=userRepository.save(user);
        return entityToUserDto(savedUser);

    }
    private Users userDtoToEntity(UserDto userDto){
        Users users=new Users();
        users.setName(userDto.getName());
        users.setEmail(userDto.getEmail());
        users.setPassword(userDto.getPassword());
        return users;

    }
    private UserDto entityToUserDto(Users savedUser){
        UserDto userDto=new UserDto();
        userDto.setId(savedUser.getId());
        userDto.setEmail(savedUser.getEmail());
        userDto.setPassword(savedUser.getPassword());
        userDto.setName(savedUser.getName());
        return userDto;
    }
}
