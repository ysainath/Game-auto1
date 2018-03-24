/**
 * 
 */
package com.auto1.group.game.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.auto1.group.game.acid.PlayerEntity;

/**
 * @author yelsa03
 *
 */

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long>{

}
