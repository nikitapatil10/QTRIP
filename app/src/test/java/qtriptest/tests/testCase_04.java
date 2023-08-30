package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.AdventureDetailsPage;
import qtriptest.pages.AdventurePage;
import qtriptest.pages.HistoryPage;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;
import javax.lang.model.util.ElementScanner6;
import com.relevantcodes.extentreports.LogStatus;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertEquals;

public class testCase_04 {
    @Test(description = "verify that adventure bookiing and cancellation works fine",dataProvider = "data-provider",dataProviderClass = DP.class,priority = 4,groups = {"Reliability Flow"})
    public void TestCase04(String emailId,String password,String dataSet1,String dataSet2,String dataSet3) throws InterruptedException, IOException
    {
        ReportSingleton.test = ReportSingleton.reports.startTest("verify that adventure bookiing and cancellation works fine");
        List<String> expectedResult1 = Arrays.asList(dataSet1.split(";"));
        List<String> expectedResult2 = Arrays.asList(dataSet2.split(";"));
        List<String> expectedResult3 = Arrays.asList(dataSet3.split(";"));

        Boolean status;
        RemoteWebDriver driver= DriverSingleton.getDriver();
        RegisterPage register = new RegisterPage();
        register.navigateToRegisterPage();
        //Thread.sleep(2000);
        status = register.registerNewUser(emailId, password, true);
        assertTrue(status,"User not registered");
        String lastGeneratedUserName = register.lastGeneratedUsername;
        LoginPage login = new LoginPage();
        login.navigateToLoginPage();
        //Thread.sleep(2000);
        login.performLogin(lastGeneratedUserName, password);
        //Thread.sleep(2000);
        AdventurePage adventure = new AdventurePage();
        HistoryPage history = new HistoryPage();
        AdventureDetailsPage adventureDetails = new AdventureDetailsPage();
        HomePage home = new HomePage();
        home.navigateToHomePage();
        Thread.sleep(2000);
        home.searchCity(expectedResult1.get(0));
        Boolean result = home.assertAutoCompleteText(expectedResult1.get(0));
        if(!result)
        {
            System.out.println("No city Found");
        }
        else
        {
            home.selectCity(expectedResult1.get(0));
            //Thread.sleep(1000);
            
            adventure.selectAdventure(expectedResult1.get(1));
            //Thread.sleep(2000);
           
            adventureDetails.bookAdventureDetails(expectedResult1.get(2), expectedResult1.get(3), expectedResult1.get(4));
            //Thread.sleep(2000);
           
            // history.navigateToHistoryPage();
        }
        
        
        home.navigateToHomePage();
        Thread.sleep(2000);
        home.searchCity(expectedResult2.get(0));
        result = home.assertAutoCompleteText(expectedResult2.get(0));
        if(!result)
        {
            System.out.println("No city Found");
        }
        else
        {
            home.selectCity(expectedResult2.get(0));
            //Thread.sleep(1000);
            adventure.selectAdventure(expectedResult2.get(1));
            //Thread.sleep(2000);
            adventureDetails.bookAdventureDetails(expectedResult2.get(2), expectedResult2.get(3), expectedResult2.get(4));
            // Thread.sleep(2000);
            // history.navigateToHistoryPage();
        }
        
        home.navigateToHomePage();
        Thread.sleep(2000);
        home.searchCity(expectedResult3.get(0));
        result = home.assertAutoCompleteText(expectedResult3.get(0));
        if(!result)
        {
            System.out.println("No city Found");
        }
        else{
            home.selectCity(expectedResult3.get(0));
        //Thread.sleep(1000);
        adventure.selectAdventure(expectedResult3.get(1));
        //Thread.sleep(2000);
        adventureDetails.bookAdventureDetails(expectedResult3.get(2), expectedResult3.get(3), expectedResult3.get(4));
        //Thread.sleep(2000);
        history.navigateToHistoryPage();
        Thread.sleep(2000);
        int count = history.getReservations();
        Thread.sleep(2000);
        assertEquals(count,3,"Actual history count and expected history count not match");
        ReportSingleton.test.log(LogStatus.PASS,ReportSingleton.test.addScreenCapture(ReportSingleton.capture(driver)) ,"Successfully verify that adventure bookiing and cancellation works fine");

        }
    }

}
