package online.store.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.store.model.Activity;
import online.store.repositories.ActivityRepository;

@Service
public class ActivityService {
    
    @Autowired
    private ActivityRepository activityRepository;

    public void registerActivity(String serviceName, String status, String description){
        Activity activity = new Activity();
        activity.setStreamName(serviceName);
        activity.setStreamProcessingStatus(status);
        activity.setTimestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        activity.setErrorDescription(description);

        activityRepository.save(activity);
    }
}
