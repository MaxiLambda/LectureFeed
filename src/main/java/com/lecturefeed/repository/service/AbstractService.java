package com.lecturefeed.repository.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@RequiredArgsConstructor
public abstract class AbstractService <TYPE,REPO extends JpaRepository<TYPE,Integer>>{
    protected final REPO repo;

    public List<TYPE> findAll(){
        return repo.findAll();
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

    public List<TYPE> saveAll(Iterable<TYPE> toSave){
        return repo.saveAll(toSave);
    }
}
