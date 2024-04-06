package ru.spring.spring_boot.services;

import org.springframework.data.domain.Limit;
import ru.spring.spring_boot.models.Car;

import java.util.List;
import java.util.Optional;

public interface CarService {
    public List<Car> findAll();

    public Optional<Car> findById(long id);

    public void save(Car car);

    public void update(long id, Car updatedCar);

    public void delete(long id);

    public List<Car> getCarsByGivenCounter(Integer count);

    public List<Car> sortByField(List<Car> carList, String field);

    public void checkSortBlocking(String sortBy);

    List<Car> findCarsBy(Limit limit);
}
