package com.nextfeed.library.core.valueobject;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractValueList<E, D> {

    private final List<IValueObject<E, D>> list;

    public List<D> getDTOList(){
        return list.stream().map(IValueObject::getDTO).toList();
    }

    public List<E> getEntities(){
        return list.stream().map(IValueObject::getEntity).toList();
    }

}
