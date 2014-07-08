import com.google.common.collect.Lists;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: Nitin Kukna
 * Date: 7/7/14
 * Time: 6:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class SignupCODorder extends SharedProperties{
    String baseUrl;
    public  String browser;
    /*LoginPage loginPage = new LoginPage();*/
    SignupPage signupage = new SignupPage();
    ProductPage productpage = new ProductPage();
    CartPage cartpage = new CartPage();
    AddressPage addresspage = new AddressPage();
    PaymentPage paymentpage = new PaymentPage();
    ReadExcel readexcel =new ReadExcel();

    @Parameters({"BaseURL", "Browser"})
    @BeforeClass
    public void g(String baseUrl, String browser) {
        this.baseUrl = baseUrl;
        this.browser = browser;
    }

    @DataProvider(name = "CombinedData")
    public Iterator<Object[]> dataProviderCombined() {
        List<Object[]> result = Lists.newArrayList();
        List<String> finalObjectString = new ArrayList<String>();

        try{


            finalObjectString.addAll(readexcel.mainReadFromExcelIterator("C:\\selenium\\Automation_testing_v4\\signup.xls"));
            finalObjectString.addAll(readexcel.mainReadFromExcelIterator("C:\\selenium\\Automation_testing_v4\\productId.xls"));
            result.add(new Object[]{finalObjectString});

            System.out.println("List iterator : "+result.iterator());
        }
        catch(FileNotFoundException fex){
            System.out.println(fex.getMessage());
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        return result.iterator();
    }





    @Parameters("BaseURL")
    @Test(dataProvider = "CombinedData", enabled = true)
    public void login(List<String> dataArray)  throws InterruptedException, IOException {
        openBrowser(baseUrl, browser);
       /* System.out.println("username: "  +dataArray.get(0));
        System.out.println("Password: "  +dataArray.get(1));*/


/*        System.out.println("Email Xpath: "  +loginPage.getEmailIdTextBox());
        System.out.println("Password Xpath: "  +loginPage.getPasswordTextBox());
        System.out.println("SignIn Xpath: "  +loginPage.getSignInBtn());*/

        Thread.sleep(7000);

        /*Click(loginPage.getSignInBtn(), "Create an account button", "Sign in page", driver);
        Thread.sleep(3000);

        sendKeys(loginPage.getEmailIdTextBox(), "Login page", "Enter username", dataArray.get(3), driver);
        sendKeys(loginPage.getPasswordTextBox(), "Login page", "Enter password", dataArray.get(2), driver);
        Click(loginPage.getSignInBtn(), "Login page", "Sign in Button", driver);
        sendKeys(loginPage.getEmailIdTextBox(), "Login page", "Enter username", dataArray.get(1), driver);
        sendKeys(loginPage.getPasswordTextBox(), "Login page", "Enter password", dataArray.get(1), driver);
        Click(loginPage.getSignInBtn(), "Login page", "Sign in page", driver);
*/

        for(int i=4;i<dataArray.size();i++){
            driver.navigate().to("http://www.centralhk.com:9090/sv/on-%28optimum-nutrition%29-gold-standard-100-whey-protein/SP-9558?navKey=VRNT-"+dataArray.get(i));
            WebElement buyNow = driver.findElement(By.cssSelector("input[class='addToCart btn btn-blue btn2 mrgn-b-5 disp-inln']"));
            buyNow.click();

        }

        //WebElement exp = driver.findElement(By.cssSelector("a[href*='Cart.action']"));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout( 30, TimeUnit.SECONDS )
                .pollingEvery( 5, TimeUnit.SECONDS )
                .ignoring( NoSuchElementException.class, StaleElementReferenceException.class );
        WebElement cartLink = wait.until( ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a[href*='Cart.action']")));
        cartLink.click();



        //Code to add more quantity
        //code to redeem reward points
        //code to add coupons

        Click(cartpage.proceedToCheckout(), "Login to checkout", "Cart Page", driver);
        Thread.sleep(2000);
        Click(signupage.signupPage(), "Create an account button", "Sign up page", driver);
        sendKeys(signupage.name(), "SignupPage page", "Enter name", dataArray.get(0), driver);
        sendKeys(signupage.emailid(), "SignupPage page", "Enter email", dataArray.get(1), driver);
        sendKeys(signupage.password(), "SignupPage page", "Enter password", dataArray.get(2), driver);
        sendKeys(signupage.confirmpassword(), "SignupPage page", "Enter confirmpassword", dataArray.get(3), driver);
        Click(signupage.createaccount(), "Create an account", "Sign up page", driver);
        Thread.sleep(2000);
        Click(cartpage.proceedToCheckout(), "Proceed to checkout", "Cart Page", driver);
        Thread.sleep(2000);
        sendKeys(addresspage.name(), "Address page", "Name", "Nitin" , driver);
        sendKeys(addresspage.mobile(), "Address page", "Mobile", "9999999999" , driver);
        sendKeys(addresspage.address(), "Address page", "Name", "Test" , driver);
        sendKeys(addresspage.pincode(), "Address page", "Name", "122001" , driver);
        Thread.sleep(2000);
        Click(addresspage.delivertoaddress(), "Deliver to this address", "Address Page", driver);
        Thread.sleep(5000);


       /* private boolean isElementPresent(By by) {
            try {
                WebElement wb = driver.findElement(by);
                return wb == null? false:true;
            } catch (NoSuchElementException e) {
                return false;
            }
        }*/

        Click(paymentpage.cashOnDelivery(), "COD tab", "payment page", driver);
        Thread.sleep(5000);
        Click(paymentpage.payOnDelivery(), "finally cod", "paymnet page", driver);
    }
}
