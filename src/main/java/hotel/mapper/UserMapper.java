package hotel.mapper;

import java.util.*;

import hotel.dto.auth.UserView;
import hotel.entities.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "spring"
)
public interface UserMapper {
    @Mapping(target = "user.roles", ignore = true)
    UserView UserToUserView(UserEntity user);
}
