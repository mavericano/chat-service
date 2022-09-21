package io.dice.chatservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Table
@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @Column
    @Type(type = "uuid-char")
    private UUID uuid;

    @Column
    private String nickname;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE})
    private Set<UserChatCounter> chats;
}
