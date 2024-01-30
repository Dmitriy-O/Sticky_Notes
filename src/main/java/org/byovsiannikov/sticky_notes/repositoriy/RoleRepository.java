package org.byovsiannikov.sticky_notes.repositoriy;

import org.byovsiannikov.sticky_notes.entitiy.RoleEntity;
import org.byovsiannikov.sticky_notes.entitiy.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity,Long> {
    Optional<RoleEntity> findByRoleName(String roleName);

}
