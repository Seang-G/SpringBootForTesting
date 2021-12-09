package com.coco.board.domain.user;

import com.coco.board.domain.TimeEntity;
import com.coco.board.domain.posts.Posts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Entity
public class User extends TimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Column(nullable = false, length = 30, unique = true)
    private String username; // 아이디

    @Column(nullable = false, length = 100) // 123456 => 해쉬(암호화)를 위해 length 넉넉히
    private String password;

    @Column(nullable = false, length = 50)
    private String email;

//    @OneToMany(mappedBy = "user")
//    private List<Posts> postsList;
    // 나중에 Role 추가


}