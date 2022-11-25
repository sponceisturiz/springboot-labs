package online.store.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import online.store.model.Activity;

@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer>{
    
}
