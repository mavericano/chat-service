package io.dice.chatservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {

    @Id
//    @GeneratedValue(generator = "uuid")
//    @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
    @Column
    @Type(type = "uuid-char")
    private UUID chatId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat", cascade = {CascadeType.MERGE})
    private Set<UserChatCounter> participants;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private Set<Message> messages;
}
