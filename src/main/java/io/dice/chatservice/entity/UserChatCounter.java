package io.dice.chatservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserChatCounter {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    @Type(type = "uuid-char")
    private UUID uuid;

    @ManyToOne
    private User user;

    @ManyToOne
    private Chat chat;

    @Column
    private long counter;

    public String toString() {
        return this.user.getUuid().toString();
    }
}
