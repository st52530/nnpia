package cz.st52530.recipes.controller

import cz.st52530.recipes.config.SWAGGER_AUTH_KEY
import cz.st52530.recipes.model.database.Recipe
import cz.st52530.recipes.security.JwtRequestFilter
import cz.st52530.recipes.service.IRecipeService
import cz.st52530.recipes.service.IUserService
import cz.st52530.recipes.util.JwtTokenUtil
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/recipes/")
@SecurityRequirement(name = SWAGGER_AUTH_KEY)
class RecipesController(
        private val userService: IUserService,
        private val recipesService: IRecipeService,
        private val jwtTokenUtil: JwtTokenUtil
) {

    @GetMapping
    fun getUsersRecipes(@RequestHeader(JwtRequestFilter.AUTHORIZATION_HEADER) tokenHeader: String): List<Recipe> {
        val username = jwtTokenUtil.getUsernameFromToken(jwtTokenUtil.extractBareToken(tokenHeader))
        val user = userService.getUserByUsername(username)

        return recipesService.getByUser(user)
    }
}