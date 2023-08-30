package qtriptest.tests;

import qtriptest.DP;
import qtriptest.DriverSingleton;
import qtriptest.ReportSingleton;
import qtriptest.pages.HomePage;
import qtriptest.pages.LoginPage;
import qtriptest.pages.RegisterPage;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.Test;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import java.io.IOException;
import java.net.MalformedURLException;
import com.relevantcodes.extentreports.LogStatus;


public class testCase_01 {
    @Test(description = "verify the user is register successfully",dataProvider = "data-provider",dataProviderClass = DP.class,priority = 1,groups = {"Login Flow"})
    //@Parameters({ "TC1_Username", "TC1_Password" })
    public void TestCase01(String TC1_Username,String TC1_Password) throws InterruptedException, IOException
    {
        Boolean status;  
        ReportSingleton.test = ReportSingleton.reports.startTest("verify the user is register successfully");
        RemoteWebDriver driver= DriverSingleton.getDriver();
        RegisterPage register = new RegisterPage();
        //Thread.sleep(2000);
        register.navigateToRegisterPage();
        //Thread.sleep(2000);
        status = register.registerNewUser(TC1_Username, TC1_Password, true);
        //Thread.sleep(2000);
        
        assertTrue(status,"Registration is not successful");
        String lastGeneratedUsername = register.lastGeneratedUsername;
       // System.out.println(lastGeneratedUsername);
        LoginPage login = new LoginPage();
        login.navigateToLoginPage();
        login.performLogin(lastGeneratedUsername, TC1_Password);
        //Thread.sleep(2000);
        ReportSingleton.test.log(LogStatus.PASS,ReportSingleton.test.addScreenCapture(ReportSingleton.capture(driver)) ,"Successfully logged in with the registered  user");
        HomePage home = new HomePage();
        status = home.isUserLoggedIn();
        //Thread.sleep(2000);
        assertTrue(status,"User not logged in");
        home.loggedOutUSer();
        // // Thread.sleep(2000);
        // assertFalse(status,"User is still logged in");
    }

    
    
    
    
}



