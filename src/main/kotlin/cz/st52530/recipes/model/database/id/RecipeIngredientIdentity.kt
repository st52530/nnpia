package cz.st52530.recipes.model.database.id

import java.io.Serializable
import javax.persistence.Embeddable
import javax.validation.constraints.NotNull

@Embeddable
data class RecipeIngredientIdentity(
        @NotNull
        var recipeId: Int,

        @NotNull
        var ingredientId: Int
) : Serializable