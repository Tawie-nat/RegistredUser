


import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;


class AuthTest{
    private static Faker faker;
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\Desktop\\TestoviiRezhim\\driver\\win\\chromedriver.exe");

    }


    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999/");
      }


    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {
        Registration registeredActiveUser = DataGenerator.RegistrationUser.getActiveUser();
        $("[data-test-id='login'] input").setValue(registeredActiveUser.getLogin()).click();
        $("[data-test-id='password'] input").setValue(registeredActiveUser.getPassword()).click();
        $("[data-test-id='action-login'] .button__content").click();


    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = faker.name().fullName();
    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        Registration registeredActiveUser = DataGenerator.RegistrationUser.getBlockedUser();
        $("[data-test-id='login'] input").setValue(registeredActiveUser.getLogin()).click();
        $("[data-test-id='password'] input").setValue(registeredActiveUser.getPassword()).click();
        $("[data-test-id='action-login'] .button__content").click();
        $(".notification__content").shouldHave(text("Пользователь заблокирован"));
    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.RegistrationUser.getActiveUser();
        var wrongLogin = faker.letterify("########");
    }


}
