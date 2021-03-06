package cz.st52530.recipes.dao

import cz.st52530.recipes.model.database.Ingredient
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query

interface IngredientRepository : JpaRepository<Ingredient, Int> {

    fun findAllByOrderByName(): List<Ingredient>

    fun findAllByIdIn(ids: Collection<Int>): List<Ingredient>
}