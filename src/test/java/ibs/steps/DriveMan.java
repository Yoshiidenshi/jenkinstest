package ibs.steps;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class DriveMan {

    public static WebDriver createDriver() throws MalformedURLException {
        //String runType = System.getProperty("type.run", System.getProperty("type.run"));
        String browser = System.getProperty("type.browser", System.getProperty("type.browser"));
        String version = System.getProperty("browser.version", System.getProperty("browser.version"));
	String url = System.getProperty("selenoid.url");

            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("browserName", "chrome");
            capabilities.setCapability("browserVersion", "109.0");

            Map<String, Object> selenoidOptions = new HashMap<>();
            selenoidOptions.put("enableVNC", true);
            selenoidOptions.put("enableVideo", false);
            capabilities.setCapability("selenoid:options", selenoidOptions);

            return new RemoteWebDriver(
                    URI.create("http://jenkins.applineselenoid.fvds.ru:4444/wd/hub").toURL(),
                    capabilities
            );

    }

}
