package com.basket.BasketballSystem.generics;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class GenericController<T,ID>{
    @Autowired
    public  GenericService<T, ID> service;

    @GetMapping
    public List<T> readAll(@RequestParam(required = false) Integer page,
                           @RequestParam(required = false) Integer size){
        if(page == null || size == null ){
            return service.readAll();
        }

        return service.readAll(page,size);
    }

    @GetMapping("/count")
    public ResponseEntity<Map<String,Long>> count(){
        long count = service.count();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
    @PostMapping()
    public ResponseEntity<T> insert(@Validated @RequestBody T entity){
        final T elementSaved = this.service.insert(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(elementSaved);

    }
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@PathVariable ID id, @RequestBody T entity) {
        T updatedEntity = this.service.update(entity, id);
        return ResponseEntity.ok(updatedEntity);
    }
}
