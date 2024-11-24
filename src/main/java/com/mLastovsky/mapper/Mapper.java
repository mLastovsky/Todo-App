package com.mLastovsky.mapper;

public interface Mapper<F, T> {

    T mapFrom(F object);
}
