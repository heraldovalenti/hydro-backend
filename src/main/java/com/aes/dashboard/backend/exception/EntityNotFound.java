package com.aes.dashboard.backend.exception;

public class EntityNotFound extends RuntimeException {

    private Long id;
    private Class entity;

    public EntityNotFound(Long id, Class entity) {
        this.id = id;
        this.entity = entity;
    }

    public Long getId() {
        return id;
    }

    public Class getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "EntityNotFound{" +
                "id=" + id +
                ", entity=" + entity +
                '}';
    }
}
