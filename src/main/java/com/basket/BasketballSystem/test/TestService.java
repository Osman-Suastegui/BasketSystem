package com.basket.BasketballSystem.test;

import com.basket.BasketballSystem.generics.GenericRepository;
import com.basket.BasketballSystem.generics.GenericService;
import org.springframework.stereotype.Service;

@Service
public class TestService extends GenericService<TestEntity,Long> {
    @Override
    protected void updateFields(TestEntity source, TestEntity target) {
        String newName = source.getName();
        target.setName(newName);
    }
}
