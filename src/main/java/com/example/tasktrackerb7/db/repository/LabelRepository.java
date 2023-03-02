package com.example.tasktrackerb7.db.repository;

import com.example.tasktrackerb7.db.entities.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

    @Query(value = "select * from label where id in (select labels_id from cards_labels where card_id = :id)", nativeQuery = true)
    List<Label> getAllLabelResponse(Long id); //cardId

    @Modifying
    @Query(value = """
            delete from cards_labels where card_id in
            (select id from cards where column_id in
            (select id from columns where board_id in
            (select id from boards where workspace_id = :id)));
            """, nativeQuery = true)
    void deletes(Long id);

}