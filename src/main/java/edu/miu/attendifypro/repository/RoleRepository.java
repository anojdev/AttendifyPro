package edu.miu.attendifypro.repository;

import edu.miu.attendifypro.domain.auth.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author kush
 */
public interface RoleRepository extends JpaRepository<Role,Long> {

}
