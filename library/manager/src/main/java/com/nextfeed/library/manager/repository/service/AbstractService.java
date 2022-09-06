package com.nextfeed.library.manager.repository.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

@AllArgsConstructor
public abstract class AbstractService <TYPE,REPO extends CrudRepository<TYPE,Integer>>{
    protected final REPO repo;

    public List<TYPE> findAll(){
        return (List<TYPE>) repo.findAll();
    }

    public TYPE findById(int id){
        return repo.findById(id).orElse(null);
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

    public Thread saveAsync(TYPE toAdd){
        return new Thread(() -> repo.save(toAdd));
    }

    public List<TYPE> saveAll(Iterable<TYPE> toSave){
        return (List<TYPE>) repo.saveAll(toSave);
    }
}
