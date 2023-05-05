package ru.javaops.topjava.repository;

import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava.model.Dish;

@Transactional(readOnly = true)
public interface DishRepository extends BaseRepository<Dish> {

}
