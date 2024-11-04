package com.basket.BasketballSystem.test;

import com.basket.BasketballSystem.generics.GenericController;
import com.basket.BasketballSystem.generics.GenericService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController extends GenericController<TestEntity,Long> {
}
