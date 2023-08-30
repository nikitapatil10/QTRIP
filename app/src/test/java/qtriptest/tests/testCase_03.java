package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;

import qtriptest.pages.RegisterPage;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.testng.Assert.assertEquals;

public class testCase_03 {
   @Test(description = "verify that adventure bookiing and cancellation works fine",dataProvider = "data-provider",dataProviderClass = DP.class,priority = 3,groups = {"Booking and Cancellation Flow"})
   public void TestCase03(String userName,String password,String city,String adventureName, String guestName,String date,String persons) throws InterruptedException, IOException
   {
        Boolean status;
        RemoteWebDriver driver= DriverSingleton.getDriver();
        RegisterPage register = new RegisterPage();
        //Thread.sleep(2000);
        register.navigateToRegisterPage();
        //Thread.sleep(2000);
        status = register.registerNewUser(userName, password, true);
        //Thread.sleep(2000);
        
        assertTrue(status,"Registration is not successful");
        String lastGeneratedUsername = register.lastGeneratedUsername;
       // System.out.println(lastGeneratedUsername);
        LoginPage login = new LoginPage();
        login.navigateToLoginPage();
        login.performLogin(lastGeneratedUsername, password);
       // Thread.sleep(2000);
        HomePage home = new HomePage();
        home.navigateToHomePage();
        Thread.sleep(2000);
        home.searchCity(city);
        Boolean result = home.assertAutoCompleteText(city);
        if(!result)
        {
         System.out.println("No city Found");
        }
        else
        {
         home.selectCity(city);
         Thread.sleep(2000);
         AdventurePage adventure = new AdventurePage();
         adventure.selectAdventure(adventureName);
         Thread.sleep(2000);
         AdventureDetailsPage adventureDetails = new AdventureDetailsPage();
         //adventureDetails.navigateToAdventurePage();
         adventureDetails.bookAdventureDetails(guestName, date, persons);
         Thread.sleep(2000);
         HistoryPage history = new HistoryPage();
         history.navigateToHistoryPage();
         Thread.sleep(2000);
         int count = history.getReservations();
         Thread.sleep(2000);
         assertEquals(count,1,"Actual history count and expected history count not match");
         history.cancelReservation(adventureName);
         Thread.sleep(2000);
         driver.navigate().refresh();
         Thread.sleep(2000);
         count = history.getReservations();
         Thread.sleep(2000);
         assertEquals(count,0,"Actual history count and expected history count not match");
         home.loggedOutUSer();
        }    
        
   }


}
