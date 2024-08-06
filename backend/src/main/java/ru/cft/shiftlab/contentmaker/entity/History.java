package ru.cft.shiftlab.contentmaker.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class History {

    public enum Status{
        SUCCESSFUL,
        BAD,
        SERVER_ERROR
    }

    public enum OperationType{
        Create,
        Delete,
        Update,
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

    @Column(name="time")
    LocalDate time;

    @Column(name="user_name")
    String userName;

    @Column(name="status")
    @Enumerated(EnumType.STRING)
    Status status;

    @Column(name="operation_type")
    @Enumerated(EnumType.STRING)
    OperationType operationType;

    @Column(name="Component_type")
    @Enumerated(EnumType.STRING)
    ComponentType componentType;


}
