package ru.cft.shiftlab.contentmaker.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "history")
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
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


}
