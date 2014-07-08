/**
 * Created with IntelliJ IDEA.
 * User: Saurabh
 * Date: 6/30/14
 * Time: 7:23 PM
 * To change this template use File | Settings | File Templates.
 */
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.remote.DesiredCapabilities;

public class SharedProperties {
    public static WebDriver driver;

    public static void openBrowser(String AppURL, String BrowserName) {
        if (BrowserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);

        } else if (BrowserName.equalsIgnoreCase("IE")) {
            System.setProperty("webdriver.ie.driver", "D:\\SeleniumUse\\IEDriverServer.exe");
            DesiredCapabilities cap = DesiredCapabilities.internetExplorer();
            cap.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
            driver = new InternetExplorerDriver(cap);
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        } else if (BrowserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", "E:\\Healthkart\\MainAutoQa\\Automation_testing_v4\\chromedriver.exe");
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(6, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        } else {
            driver = new FirefoxDriver();
            driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
            driver.manage().window().maximize();
            driver.get(AppURL);
        }
    }
    public static void sendKeys(String elementXpath, String elementObjectName, String currentPage,  String value, WebDriver driver) {
        driver.findElement(By.xpath(elementXpath)).sendKeys(value);

    }
    public static void Click(String elementXpath, String elementObjectName, String currentPage, WebDriver driver)  {
        driver.findElement(By.xpath(elementXpath)).click();
       // checkHttpResponse(driver.getCurrentUrl());
    }

    public static void clear(String elementXpath,  WebDriver driver)  {
        driver.findElement(By.xpath(elementXpath)).clear();
        // checkHttpResponse(driver.getCurrentUrl());
    }

    public static void Click2(String CSSSelector, String elementObjectName, String currentPage, WebDriver driver)  {
        driver.findElement(By.cssSelector(CSSSelector)).click();
        // checkHttpResponse(driver.getCurrentUrl());
    }

    public static boolean checkHttpResponse(String testUrl) throws IOException {
        URL url = new URL(testUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();
        System.out.println("Response code of the object is " + code);
        if (code == 200) {
            return true;
        } else
            return false;
    }


}
