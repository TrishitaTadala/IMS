package Utilities;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
//import Utility.Log;
import java.io.IOException;
import com.google.common.base.Function;
//import com.sun.jna.platform.FileUtils;
import org.apache.commons.io.FileUtils;


public class Utils {
	public static WebDriver driver = null;
	static String appBrowser;
	static Actions interactDriver;
	static String applicationURL;
	static DesiredCapabilities capabilities;
	public static WebDriver OpenBrowser(String sBrowserName, String sAppURL) throws Exception
	{
		if(sBrowserName.equalsIgnoreCase("firefox"))
		{
			Log.info("I am inside firefox Browser Instantiation");
			
			System.setProperty("webdriver.firefox.bin",
					"D:\\Vodafone\\Automation\\Mozilla Firefox v43\\firefox.exe");

			/*FirefoxProfile fp = new FirefoxProfile(new File("D:\\Mozilla Firefox\\Mozilla Firefox\\Auto_profile"));
			fp.setPreference("network.proxy.type", 2);
			fp.setPreference("network.proxy.autoconfig_url", "http://infypacsrv/prakriti.pac");
			fp.setAcceptUntrustedCertificates(true);
			fp.setAssumeUntrustedCertificateIssuer(true);*/
		
			 driver = new FirefoxDriver();
			//driver = new FirefoxDriver();
			driver.get(sAppURL);
			Thread.sleep(1000);
			driver.manage().window().maximize();
			Log.info("Browser is initiated Successfully and URL is Opened");
		}
			if(sBrowserName.equalsIgnoreCase("ie"))	
		{		
			Log.info("I am inside ie Browser Instantiation step1");
			File ieFile = new File(".\\lib\\drivers\\IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", ieFile.getAbsolutePath());
			capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			driver = new InternetExplorerDriver(capabilities);
			Log.info("I am inside ie Browser Instantiation");
			driver.get(sAppURL);
			Thread.sleep(1000);
			driver.manage().window().maximize();
		}
		
		if(sBrowserName.equalsIgnoreCase("chrome"))	
		 {	
			WindowsUtils.tryToKillByName("chromedriver.exe");
			File chromeDriver = new File("C:\\Users\\Praveen Kumar Voora\\eclipse-workspace\\INMARSAT BTP Regression\\BTP_Regression\\src\\test\\resources\\Utilities\\Drivers\\chromedriver.exe");
			//For language control
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--lang=en-gb");
			capabilities = DesiredCapabilities.chrome();			
			System.setProperty("webdriver.chrome.driver", chromeDriver.getAbsolutePath());			
			capabilities.setCapability("chrome.switches", Arrays.asList("--allow-running-insecure-content"));
			capabilities.setCapability("chrome.switches", Arrays.asList("--start-maximized"));
			capabilities.setVersion("79");
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			try {
			ChromeDriverService chromeService = ChromeDriverService.createDefaultService();
			chromeService.start();
			driver = new ChromeDriver(capabilities);
		    //WindowsUtils.tryToKillByName("chromedriver.exe");
			driver.get(sAppURL);
			Thread.sleep(1000);
			driver.manage().window().maximize();
			} 
			
			catch (IOException e) {		
				Log.error("Unable to Open Browser");
				//e.printStackTrace();
			}	
			
		}	
		//interactDriver = new Actions(driver);
		return driver;
	}
		
		public static WebDriver OpenBrowser_mShop(int iTestCaseRow) throws Exception{
		String sBrowserName;
		try{
			sBrowserName=Constant.Browser;
			//sBrowserName = ExcelUtils.getCellData(iTestCaseRow, Constant.Col_Browser);
			if(sBrowserName.equals("Chrome")){
				driver = new FirefoxDriver();
				Log.info("New driver instantiated");
				driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
				Log.info("Implicit wait applied on the driver for 10 seconds");
				driver.get(Constant.Central_Gateway);
				Log.info("Web application launched successfully");
			}
		}catch (Exception e){
			Log.error("Class Utils | Method OpenBrowser | Exception desc : "+e.getMessage());
		}
		return driver;
	}

	public static String getTestCaseName(String sTestCase)throws Exception{
		String value = sTestCase;
		try{
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 1);
			return value;
		}catch (Exception e){
			Log.error("Class Utils | Method getTestCaseName | Exception desc : "+e.getMessage());
			throw (e);
		}
	}
	
	public static String gettestExecutionStatus(String executionFlag) throws Exception{
		String value = executionFlag;
		try{
			int posi = value.indexOf("@");
			value = value.substring(0, posi);
			posi = value.lastIndexOf(".");	
			value = value.substring(posi + 2);
			return value;
		}catch (Exception e){
			Log.error("Class Utils | Method gettestExecutionStatus | Exception desc : "+e.getMessage());
			throw (e);
		}
	}	
		

	public static void mouseHoverAction(WebElement mainElement, String subElement){

		Actions action = new Actions(driver);
		action.moveToElement(mainElement).perform();
		if(subElement.equals("Accessories")){
			action.moveToElement(driver.findElement(By.linkText("Accessories")));
			Log.info("Accessories link is found under Product Category");
		}
		if(subElement.equals("iMacs")){
			action.moveToElement(driver.findElement(By.linkText("iMacs")));
			Log.info("iMacs link is found under Product Category");
		}
		if(subElement.equals("iPads")){
			action.moveToElement(driver.findElement(By.linkText("iPads")));
			Log.info("iPads link is found under Product Category");
		}
		if(subElement.equals("iPhones")){
			action.moveToElement(driver.findElement(By.linkText("iPhones")));
			Log.info("iPhones link is found under Product Category");
		}
		action.click();
		action.perform();
		Log.info("Click action is performed on the selected Product Type");
	}
	
	public static void takeScreenshot(WebDriver driver, String sTestCaseName) throws Exception{
		try{
			java.util.Date date= new java.util.Date();
			String newString = new SimpleDateFormat("yyyyMMddHHmmss").format(date);
			//System.out.println(newString);
			// String timestamp = new Timestamp(date.getTime()).toString();
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			//FileUtils.copyFile(scrFile, new File(Constant.Path_ScreenShot + sTestCaseName+newString+".jpg"));	
			
			//File destination = new File(Constant.Path_ScreenShot + sTestCaseName+newString+".jpg");
			File destination = new File("C:\\Users\\Praveen Kumar Voora\\eclipse-workspace\\INMARSAT BTP Regression\\BTP_Regression\\Screenshots\\" + sTestCaseName+newString+".jpg");
		    System.out.println("Screenshot stored at:" + destination.getAbsolutePath());
		    FileUtils.copyFile(scrFile, destination);
			
			
		} catch (Exception e){
			Log.error("Class Utils | Method takeScreenshot | Exception occured while capturing ScreenShot : "+e.getMessage());
			throw new Exception();
		}
	}

	/*******************************************************************************	 
	 *   Function Name : Clear Cookies
	 *   Description : unction to Clear Cookies of Web Application
	 **********************************************************************************/
	public static void clearCache()
	{
		Set<Cookie> siteCookies = driver.manage().getCookies();
		for (Cookie siteCookie : siteCookies) {
			driver.manage().deleteCookie(siteCookie);
		}
	}

	/*******************************************************************************	 
	 *   Function Name : WaitForAjaxToLoad
	 *   Description : Wait for Ajax to Load
	 **********************************************************************************/
	public static void waitForAjaxToLoad()
	{
		JavascriptExecutor executor = (JavascriptExecutor) driver;		
		boolean ajaxstatus = true;		
		int i=0;
		while(ajaxstatus)
		{
			boolean ajaxFlag = (Boolean) executor.executeScript("return (window.jQuery !=null) && (jQuery.active==0);");
			if(ajaxFlag){
				break;
			}
			i=i+1;			 
		}
	}

	/*******************************************************************************	 
	 *   Function Name : scollPage
	 *   Description : Scroll the Web Page
	 **********************************************************************************/	
	public static void scollPage(String yCoordinate)
	{
		JavascriptExecutor jscriptExecutor = (JavascriptExecutor)driver;
		jscriptExecutor.executeScript("window.scrollTo(0," + yCoordinate + ")", "");
	}
	
	
	/*******************************************************************************	 
	 *   Function Name : Refresh Page
	 *   Description : Refresh the Web Page
	 **********************************************************************************/	
	public static void RefreshPage()
	{
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	}
	
	
	

	/*******************************************************************************	 
	 *   Function Name : Element is Visible
	 *   Description : To verify if Element is Visible
	 **********************************************************************************/	
	public static boolean isElementVisible(WebElement Object) {
		boolean result = false;
		try {
			if (Object.isDisplayed()) {
				//Log.info("Element is Displayed : " + Object);
				result = true;
				
			}
		}
		catch(Exception e) {       
			Log.error("Element is Not Displayed" +Object);
			result= false;
		}       
		return result;
	}
	
	/*******************************************************************************	 
	 *   Function Name : waitForPageLoaded
	 *   Description :  Wait for Page to Re-Load
	 **********************************************************************************/	
	public static void waitForPageLoaded() {
	     ExpectedCondition<Boolean> expectation = new
	    	ExpectedCondition<Boolean>() {
	        public Boolean apply(WebDriver driver) {
	          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
	        }
	      };

	     Wait<WebDriver> wait = new WebDriverWait(driver,60);
	      try {
	              wait.until(expectation);
	      } catch(Throwable error) {
	             // assertFalse("Timeout waiting for Page Load Request to complete.",true);
	      }
	 } 

	/*******************************************************************************	 
	 *   Function Name : WaitForExistance
	 *   Description : Wait till the element to present
	 **********************************************************************************/
	public static void WaitForExistance(final String xpathexpression){
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.pollingEvery(500,  TimeUnit.MILLISECONDS);
			wait.withTimeout(90, TimeUnit.SECONDS);
			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>()
					{
				public Boolean apply(WebDriver arg0) {
					WebElement Element = arg0.findElement(By.xpath(xpathexpression));
					if (Element.isDisplayed()) {
						return true;
					}
					return false;
				}
					};
					wait.until(function);
		} catch (Exception e) {
			Log.error("Waited for 60 Sec Object Not Found");
		}

	}

	/*******************************************************************************	 
	 *   Function Name : WaitForExistance
	 *   Description : Wait till the element to present finding element by ID
	 **********************************************************************************/
	public static void WaitForExistancebyId(final String xpathexpression){
		try {
			FluentWait<WebDriver> wait = new FluentWait<WebDriver>(driver);
			wait.pollingEvery(500,  TimeUnit.MILLISECONDS);
			wait.withTimeout(60, TimeUnit.SECONDS);

			Function<WebDriver, Boolean> function = new Function<WebDriver, Boolean>()
					{
				public Boolean apply(WebDriver arg0) {
					WebElement Element = arg0.findElement(By.id(xpathexpression));
					if (Element.isDisplayed()) {
						return true;
					}
					return false;
				}
					};
					wait.until(function);
		} catch (Exception e) {
			Log.error("Waited for 60 Sec Object Not Found");
		}

	}

	
	public static void AcceptAlert(){	      
		try {
			Alert alert = driver.switchTo().alert();
			System.out.println(alert.getText());
			alert.accept();
			Log.info("Alert is present : Accepted");
		} catch (Exception e) {
			Log.info("Alert is not present");
		}



	}

}
