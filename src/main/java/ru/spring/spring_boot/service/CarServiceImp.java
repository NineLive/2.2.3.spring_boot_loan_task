package ru.spring.spring_boot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.spring.spring_boot.exception.SortIsBlockingException;
import ru.spring.spring_boot.configuration.CarProperties;
import ru.spring.spring_boot.model.Car;
import ru.spring.spring_boot.repository.CarRepository;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CarServiceImp implements CarService {
    public CarRepository carRepository;

    public CarProperties carProperties;


    @Autowired
    public CarServiceImp(CarRepository carRepository, CarProperties carProperties) {
        this.carRepository = carRepository;
        this.carProperties = carProperties;
    }

    public List<Car> findAll() {
        return carRepository.findAll();
    }

    public Optional<Car> findById(long id) {
        return carRepository.findById(id);
    }

    @Transactional
    public void save(Car car) {
        carRepository.save(car);
    }

    @Transactional
    public void update(long id, Car updatedCar) {
        carRepository.save(updatedCar);
    }

    @Transactional
    public void delete(long id) {
        carRepository.deleteById(id);
    }

    @Override
    public List<Car> getCarsByGivenCounter(Integer count) {
        Optional<Integer> countOptional = Optional.ofNullable(count);
        if (countOptional.isPresent() && countOptional.get() > 0 && countOptional.get() < carProperties.getMaxCar()) {
            return findCarsBy(Limit.of(count));
        } else {
            return findAll();
        }
    }

    @Override
    public List<Car> sortByField(List<Car> carList, String field) {
        if (Objects.equals(field, "model")) {
            carList.sort(Comparator.comparing(Car::getModel));
        }
        if (Objects.equals(field, "series")) {
            carList.sort(Comparator.comparing(Car::getSeries));
        }
        if (Objects.equals(field, "id")) {
            carList.sort(Comparator.comparing(Car::getId));
        }
        return carList;
    }

    @Override
    public void checkSortBlocking(String sortBy) {
        if (carProperties.getListOfDisabledSort().contains(sortBy)) {
            throw new SortIsBlockingException();
        }
    }

    @Override
    public List<Car> findCarsBy(Limit limit) {
        return carRepository.findCarsBy(limit);
    }

}
