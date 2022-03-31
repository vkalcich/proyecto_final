package com.mitocode.proyectofinal.services;

import com.mitocode.proyectofinal.dtos.UserDto;

public interface UserService {

	UserDto findUserByUsernameOrEmail(String username, String email);

	UserDto save(UserDto userDto);
}
