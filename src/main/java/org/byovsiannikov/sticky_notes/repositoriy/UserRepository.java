package org.byovsiannikov.sticky_notes.repositoriy;

import org.byovsiannikov.sticky_notes.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity>findByName(String name);
}
