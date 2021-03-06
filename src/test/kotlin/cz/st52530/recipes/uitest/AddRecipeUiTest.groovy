package cz.st52530.recipes.uitest

import cz.st52530.recipes.model.database.Category
import cz.st52530.recipes.model.database.Ingredient
import cz.st52530.recipes.model.database.User
import cz.st52530.recipes.testutil.Creator
import geb.Browser
import org.junit.jupiter.api.Test
import org.openqa.selenium.By
import org.openqa.selenium.support.ui.ExpectedConditions
import org.openqa.selenium.support.ui.WebDriverWait
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT
)
class AddRecipeUiTest {

    @Value("\${recipes.frontend.url}")
    private String frontendUrl

    @Autowired
    private Creator creator

    @Autowired
    private PasswordEncoder encoder

    @Test
    void givenCorrectInformation_thenAddRecipeIsSuccessful() {
        prepareTestData()
        login()

        // Navigate to Add Recipe page.
        Browser.drive {
            assert title == "Seznam receptů | Rodinné recepty"

            driver.findElement(By.linkText("PŘIDAT RECEPT")).click()
            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.titleIs("Přidání receptu | Rodinné recepty"))

            // Submit button should be disabled as the required information is not filled in.
            def button = driver.findElement(By.xpath("//button[span[text()='Uložit']]"))
            assert button.getAttribute("class").contains("disabled")
        }

        // Actually add the recipe!
        Browser.drive {
            // Type basic info
            driver.findElement(By.name("recipeName")).sendKeys("Thai chicken curry")
            driver.findElement(By.name("preparationTime")).sendKeys("30 minutes")

            // Select category.
            driver.findElement(By.xpath("//span[text()='Přidat kategorii']")).click()
            driver.findElement(By.xpath("//li[text()='Chicken category']")).click()

            // Type description
            driver.findElement(By.cssSelector("textarea[name='description']")).sendKeys("Recipe description")

            // Select ingredients.
            driver.findElement(By.name("ingredient")).sendKeys("Ch")
            // Wait for ingredients to load.
            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//li[text()='Chicken']")))
            driver.findElement(By.xpath("//li[text()='Chicken']")).click()
            driver.findElement(By.xpath("//span[text()='Přidat ingredienci']")).click()
            driver.findElement(By.xpath("//input[@name='amount']")).sendKeys("1 kg")

            // Type instructions.
            driver.findElement(By.xpath("//span[text()='Přidat další']")).click()
            driver.findElement(By.name("instruction")).sendKeys("Instructions part 1")

            // Submit.
            def submitButton = driver.findElement(By.xpath("//button[span[text()='Uložit']]"))
            assert !submitButton.getAttribute("class").contains("disabled")
            submitButton.click()
        }

        // Assert success.
        Browser.drive {
            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.titleIs("Thai chicken curry | Rodinné recepty"))
            assert driver.findElement(By.xpath("//h1[text()='Thai chicken curry']")).displayed
        }
    }

    private Browser login() {
        // Prepare user.
        creator.saveEntity(new User(
                "username",
                encoder.encode("password"),
                "My Name"
        ))

        return Browser.drive {
            go frontendUrl
            assert title == "Přihlášení | Rodinné recepty"

            // Type username with jQuery-like syntax.
            $("input[name='username']").value("username")

            // Type password using core WebDriver API
            driver.findElement(By.name("password")).sendKeys("password")

            // Click on login button by xpath expression.
            driver.findElement(By.xpath("//button[*[contains(text(),'Přihlásit')]]")).click()

            WebDriverWait wait = new WebDriverWait(driver, 5)
            wait.until(ExpectedConditions.titleIs("Seznam receptů | Rodinné recepty"))
        }
    }

    private void prepareTestData() {
        // Prepare categories.
        creator.saveEntity(new Category(
                name: "Chicken category"
        ))
        creator.saveEntity(new Category(
                name: "Another category"
        ))

        // Prepare ingredients.
        creator.saveEntity(new Ingredient(
                name: "Chicken"
        ))
        creator.saveEntity(new Ingredient(
                name: "Pork"
        ))
        creator.saveEntity(new Ingredient(
                name: "Another ingredient"
        ))
    }
}
