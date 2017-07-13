package com.viseo.companion.converter;

import com.viseo.companion.domain.User;
import com.viseo.companion.dto.UserDTO;

public class UserConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    public UserDTO getDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setVersion(user.getVersion());
        dto.setEmail(user.getEmail());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setImageUrl(user.getImageUrl());
        return dto;
    }
}
