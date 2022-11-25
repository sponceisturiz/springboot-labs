package online.store.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="activity_log")
public class Activity {

    @Id
    @GeneratedValue
    private int idLog;

    private String streamName;
    private String streamProcessingStatus;
    private Long timestamp;
    private String errorDescription;

    public Activity() {

    }
    public Activity(int idLog, String streamName, String streamProcessingStatus, Long timestamp,
            String errorDescription) {
        this.idLog = idLog;
        this.streamName = streamName;
        this.streamProcessingStatus = streamProcessingStatus;
        this.timestamp = timestamp;
        this.errorDescription = errorDescription;
    }
    
    public int getIdLog() {
        return idLog;
    }
    public void setIdLog(int idLog) {
        this.idLog = idLog;
    }
    public String getStreamName() {
        return streamName;
    }
    public void setStreamName(String streamName) {
        this.streamName = streamName;
    }
    public String getStreamProcessingStatus() {
        return streamProcessingStatus;
    }
    public void setStreamProcessingStatus(String streamProcessingStatus) {
        this.streamProcessingStatus = streamProcessingStatus;
    }
    public Long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
    public String getErrorDescription() {
        return errorDescription;
    }
    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    
}
