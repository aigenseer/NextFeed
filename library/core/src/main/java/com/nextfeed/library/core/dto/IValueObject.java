package com.nextfeed.library.core.dto;


public interface IValueObject<E, D> {

    D getDTO();
    E getEntity();


}
