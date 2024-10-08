package com.irwi.transporters.domain.ports.service.crud;

public interface ReadByIdService<Entity, ID> {
    public Entity readById(ID id);
}
