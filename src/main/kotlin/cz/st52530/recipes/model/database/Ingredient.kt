package cz.st52530.recipes.model.database

import cz.st52530.recipes.model.database.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity(name = "ingredient")
data class Ingredient(
        @Column(
                nullable = false,
                length = 45,
                unique = true
        )
        var name: String
) : BaseEntity()