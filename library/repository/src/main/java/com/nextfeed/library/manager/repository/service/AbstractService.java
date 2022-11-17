package com.nextfeed.library.manager.repository.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public abstract class AbstractService <TYPE,REPO extends CrudRepository<TYPE,Integer>>{
    @Getter
    protected final REPO repo;

    public List<TYPE> findAll(){
        return (List<TYPE>) repo.findAll();
    }

    public Optional<TYPE> findById(int id){
        return repo.findById(id);
    }

    public boolean existsById(int id){
        return repo.existsById(id);
    }

    public void delete(TYPE toDelete){
        repo.delete(toDelete);
    }

    public void deleteById(int id){
        repo.deleteById(id);
    }

    public void deleteAll(){
        repo.deleteAll();
    }

    public TYPE save(TYPE toAdd){
        return repo.save(toAdd);
    }

    public List<TYPE> saveAll(Iterable<TYPE> toSave){
        return (List<TYPE>) repo.saveAll(toSave);
    }
}
