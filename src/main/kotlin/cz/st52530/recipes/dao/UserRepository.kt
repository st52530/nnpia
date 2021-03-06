package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Int> {

    fun findByUsername(username: String): User
}