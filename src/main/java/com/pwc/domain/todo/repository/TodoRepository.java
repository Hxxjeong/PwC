package com.pwc.domain.todo.repository;

import com.pwc.domain.todo.dto.request.TodoReqDto;
import com.pwc.domain.todo.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
    // 조회
    @Query("""
        select distinct t from Todo t left join t.tags tg
        where (:createUser is null or t.createUser = :createUser) 
        and (:tagName is null or tg.name = :tagName) 
        and (:isDone is null or t.isDone = :isDone) 
        and t.isDelete = false
        order by t.id desc
        """)
    List<Todo> searchTodo(@Param("createUser") String createUser,
                          @Param("tagName") String tagName,
                          @Param("isDone") Boolean isDone);

    // 삭제
    @Modifying
    @Query(value = "update Todo set isDelete = true where id = :todoId")
    void deleteTodo(@Param("todoId") Long todoId);

    // 복원
    @Modifying
    @Query(value = "update Todo set isDelete = false where id = :todoId")
    void undoDeleteTodo(@Param("todoId") Long todoId);

    // seq순으로 유저의 투두 조회
    List<Todo> findByCreateUserOrderBySeq(String createUser);

    // 유저별 최대 seq
    @Query("select max(t.seq) from Todo t where t.createUser = :createUser")
    Optional<Long> findMaxSeqByUser(@Param("createUser") String createUser);

}
