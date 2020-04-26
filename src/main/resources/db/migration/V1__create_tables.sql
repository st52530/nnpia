-- MySQL Script generated by MySQL Workbench
-- Sun Apr 26 20:28:21 2020
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Table `recipes`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`user` (
  `id` INT NOT NULL,
  `username` VARCHAR(45) NOT NULL,
  `password` VARCHAR(60) NOT NULL,
  `display_name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`category` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`recipe`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`recipe` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  `instructions` TEXT NOT NULL,
  `description` VARCHAR(45) NOT NULL,
  `created_at` DATETIME NOT NULL,
  `author_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  INDEX `fk_recipe_user_idx` (`author_id` ASC),
  CONSTRAINT `fk_recipe_user`
    FOREIGN KEY (`author_id`)
    REFERENCES `recipes`.`user` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`ingredient` (
  `id` INT NOT NULL,
  `name` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `id_UNIQUE` (`id` ASC),
  UNIQUE INDEX `name_UNIQUE` (`name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`recipe_ingredient`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`recipe_ingredient` (
  `recipe_id` INT NOT NULL,
  `ingredient_id` INT NOT NULL,
  `amount` VARCHAR(45) NOT NULL,
  INDEX `fk_recipe_ingredient_recipe1_idx` (`recipe_id` ASC),
  PRIMARY KEY (`recipe_id`, `ingredient_id`),
  INDEX `fk_recipe_ingredient_ingredient1_idx` (`ingredient_id` ASC),
  CONSTRAINT `fk_recipe_ingredient_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `recipes`.`recipe` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_recipe_ingredient_ingredient1`
    FOREIGN KEY (`ingredient_id`)
    REFERENCES `recipes`.`ingredient` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `recipes`.`recipe_category`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `recipes`.`recipe_category` (
  `recipe_id` INT NOT NULL,
  `category_id` INT NOT NULL,
  INDEX `fk_recipe_category_recipe1_idx` (`recipe_id` ASC),
  INDEX `fk_recipe_category_category1_idx` (`category_id` ASC),
  PRIMARY KEY (`recipe_id`, `category_id`),
  CONSTRAINT `fk_recipe_category_recipe1`
    FOREIGN KEY (`recipe_id`)
    REFERENCES `recipes`.`recipe` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `fk_recipe_category_category1`
    FOREIGN KEY (`category_id`)
    REFERENCES `recipes`.`category` (`id`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
