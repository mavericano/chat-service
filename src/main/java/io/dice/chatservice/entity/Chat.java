package io.dice.chatservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
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

    @Column
    private String chatName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat", cascade = {CascadeType.MERGE})
    private Set<UserChatCounter> participants;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chat")
    private Set<Message> messages;
}
