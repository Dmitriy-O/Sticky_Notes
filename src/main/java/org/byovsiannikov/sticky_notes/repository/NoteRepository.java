package org.byovsiannikov.sticky_notes.repository;

import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Long> {
    Optional<NoteEntity> findByTitle(String title);
    List<NoteEntity> findAllByIsActiveFalse();
    Optional<Boolean> findByIdAndIsActiveIsFalse(Long id);
}
