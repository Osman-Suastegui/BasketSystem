package com.basket.BasketballSystem.generics;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface GenericRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}