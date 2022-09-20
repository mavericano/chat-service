package io.dice.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.UUID;

@Table
@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    @Type(type = "uuid-char")
    private UUID uuid;

    @ManyToOne
    private Chat chat;

    @Column
    private Timestamp timestamp;

    @Column
    private String text;

    @ManyToOne
    private User sender;
}
