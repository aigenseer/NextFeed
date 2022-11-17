package com.nextfeed.library.core.valueobject;


public interface IValueObject<E, D> {

    D getDTO();
    E getEntity();


}
