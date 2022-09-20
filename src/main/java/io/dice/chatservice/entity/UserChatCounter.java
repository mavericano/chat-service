package io.dice.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table
@Builder
@Data
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
