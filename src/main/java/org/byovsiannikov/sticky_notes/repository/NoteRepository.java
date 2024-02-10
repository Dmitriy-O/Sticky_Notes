package org.byovsiannikov.sticky_notes.repository;

import org.byovsiannikov.sticky_notes.entitiy.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NoteRepository extends JpaRepository<NoteEntity,Long> {
    Optional<NoteEntity> findByTitle(String title);
}
