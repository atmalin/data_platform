package cn.iecas.springboot.bean;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * @author dragon
 * @since 2022/8/15 13:41
 */
@Entity
@Table(name = "di_task")
public class DiTaskBean {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "scheduler_id")
    private long schedulerId;
    @Basic
    @Column(name = "start_time")
    private Timestamp startTime;
    @Basic
    @Column(name = "end_time")
    private Timestamp endTime;
    @Basic
    @Column(name = "status")
    private Integer status;
    @Basic
    @Column(name = "time_out")
    private Integer timeOut;
    @Basic
    @Column(name = "trigger_mode")
    private Integer triggerMode;
    @Basic
    @Column(name = "logs")
    private String logs;
    @Basic
    @Column(name = "data_size")
    private Long dataSize;
    @Basic
    @Column(name = "records")
    private Long records;
    @Basic
    @Column(name = "error_data")
    private Byte errorData;
    @Basic
    @Column(name = "is_import")
    private Byte isImport;
    @Basic
    @Column(name = "speed")
    private Integer speed;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getSchedulerId() {
        return schedulerId;
    }

    public void setSchedulerId(long schedulerId) {
        this.schedulerId = schedulerId;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public Integer getTriggerMode() {
        return triggerMode;
    }

    public void setTriggerMode(Integer triggerMode) {
        this.triggerMode = triggerMode;
    }

    public String getLogs() {
        return logs;
    }

    public void setLogs(String logs) {
        this.logs = logs;
    }

    public Long getDataSize() {
        return dataSize;
    }

    public void setDataSize(Long dataSize) {
        this.dataSize = dataSize;
    }

    public Long getRecords() {
        return records;
    }

    public void setRecords(Long records) {
        this.records = records;
    }

    public Byte getErrorData() {
        return errorData;
    }

    public void setErrorData(Byte errorData) {
        this.errorData = errorData;
    }

    public Byte getIsImport() {
        return isImport;
    }

    public void setIsImport(Byte isImport) {
        this.isImport = isImport;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiTaskBean that = (DiTaskBean) o;
        return id == that.id && schedulerId == that.schedulerId && Objects.equals(startTime, that.startTime) && Objects.equals(endTime, that.endTime) && Objects.equals(status, that.status) && Objects.equals(timeOut, that.timeOut) && Objects.equals(triggerMode, that.triggerMode) && Objects.equals(logs, that.logs) && Objects.equals(dataSize, that.dataSize) && Objects.equals(records, that.records) && Objects.equals(errorData, that.errorData) && Objects.equals(isImport, that.isImport) && Objects.equals(speed, that.speed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, schedulerId, startTime, endTime, status, timeOut, triggerMode, logs, dataSize, records, errorData, isImport, speed);
    }
}
