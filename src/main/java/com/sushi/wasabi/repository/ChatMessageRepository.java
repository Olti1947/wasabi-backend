package com.sushi.wasabi.repository;

import com.sushi.wasabi.entity.ChatMessageEntity;
import com.sushi.wasabi.entity.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findByToUserAndReadFalse(String toUser);

    List<ChatMessageEntity> findByFromUserOrToUserOrderByTimestampAsc(
            String fromUser,
            String toUser
    );

    @Query("""
    select c.fromUser
    from ChatMessageEntity c
    where c.toUser = 'ADMIN'
    group by c.fromUser
    order by max(c.timestamp) desc
""")
    List<String> findDistinctFromUsers();



    @Query("""
        select c
        from ChatMessageEntity c
        where 
            (c.fromUser = :username and c.toUser = 'ADMIN')
         or (c.fromUser = 'ADMIN' and c.toUser = :username)
        order by c.timestamp asc
    """)
    List<ChatMessageEntity> findConversationWithUser(@Param("username") String username);

    long countByFromUserAndReadFalse(String from);

    @Query(
            """
                    select c
                    from ChatMessageEntity c
                    where c.fromUser = :username
                    and c.toUser = 'ADMIN'
                    and c.read = false
                    order by c.timestamp asc
                    """
    )
    List<ChatMessageEntity> findUnreadConvo(@Param("username") String username);
}
