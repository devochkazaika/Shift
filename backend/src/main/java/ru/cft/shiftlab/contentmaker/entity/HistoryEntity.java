package ru.cft.shiftlab.contentmaker.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class HistoryEntity {

    public enum Status{
        SUCCESSFUL,
        BAD,
        SERVER_ERROR;
        public static Status getStatus(int statusCode){
            if(statusCode / 100 == 2) return SUCCESSFUL;
            if(statusCode / 100 == 4) return BAD;
            if(statusCode / 100 == 5) return SERVER_ERROR;
            return SUCCESSFUL;
        }
    }

    public enum OperationType{
        Create,
        Delete,
        Update,
        Change
    }

    public enum ComponentType{
        STORIES,
        BANNERS,
        FRAMES
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name="component_id")
    Long componentId;

    @Column(name="additional_uuid")
    UUID additional_uuid;

    @Column(name="day")
    LocalDate day;

    @Column(name="time")
    LocalTime time;

    @Column(name="user_name")
    String userName;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name="bank")
    String bankId;

    @Column(name="platform")
    String platform;

    @Column(name="operation_type")
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @Column(name="Component_type")
    @Enumerated(EnumType.STRING)
    ComponentType componentType;

    @Column(name="rollback_able")
    @JsonIgnore
    Boolean rollBackAble;

}
