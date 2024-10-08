package com.irwi.transporters.domain.ports.service.crud;

import java.util.List;

public interface AssignUsersService<Entity,IdEntity, IdUser>{
    public Entity assignUser(IdEntity idEntity, IdUser idUsers);
}
