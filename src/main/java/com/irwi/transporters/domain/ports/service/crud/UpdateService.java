package com.irwi.transporters.domain.ports.service.crud;

public interface UpdateService<EntityRequest, Entity, ID> {
    public Entity update(EntityRequest request, ID id);
}
