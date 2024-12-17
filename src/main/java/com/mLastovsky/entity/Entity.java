package com.mLastovsky.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@SuperBuilder
@Data
public abstract class Entity implements Cloneable, Serializable {
    protected Long id;

    @Override
    public Entity clone() {
        try {
            return (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
