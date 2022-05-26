package com.applitools.quickstarts;

import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.RectangleSize;
import com.applitools.eyes.TestResultsSummary;
import com.applitools.eyes.selenium.BrowserType;
import com.applitools.eyes.selenium.ClassicRunner;
import com.applitools.eyes.selenium.Configuration;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.fluent.Target;
import com.applitools.eyes.visualgrid.model.DeviceName;
import com.applitools.eyes.visualgrid.model.ScreenOrientation;
import com.applitools.eyes.visualgrid.services.VisualGridRunner;
import com.applitools.eyes.visualgrid.services.RunnerOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;


public class AppTest {
	public static void main(String[] args) {
		WebDriver webDriver = new EdgeDriver();
		ClassicRunner runner = new ClassicRunner();
		Eyes eyes = new Eyes(runner);
		setUp(eyes);
		try {
			ultraFastTest(webDriver, eyes);
		} finally {
			tearDown(webDriver, runner);
		}
	}

	public static boolean getCI() {
		String env = System.getenv("CI");
		return Boolean.parseBoolean(env);
	}

	public static void setUp(Eyes eyes) {
		Configuration config = eyes.getConfiguration();
		config.setApiKey(System.getenv("APPLITOOLS_API_KEY"));
		config.setBatch(new BatchInfo("Quest Diagnostics UFG grey box"));

		config.addBrowser(800, 600, BrowserType.CHROME);
		config.addBrowser(700, 500, BrowserType.FIREFOX);
		config.addBrowser(1600, 1200, BrowserType.IE_11);
		config.addBrowser(1024, 768, BrowserType.EDGE_CHROMIUM);
		config.addBrowser(800, 600, BrowserType.SAFARI);

		config.addDeviceEmulation(DeviceName.iPhone_X, ScreenOrientation.PORTRAIT);
		config.addDeviceEmulation(DeviceName.Pixel_2, ScreenOrientation.PORTRAIT);

		eyes.setConfiguration(config);
	}

	public static void ultraFastTest(WebDriver webDriver, Eyes eyes) {
		try {
			webDriver.get("http://localhost:2814/QuestSite/Lab%20Result.html");
			eyes.open(webDriver, "Quest App", "Quest Test", new RectangleSize(800, 600));
			eyes.check(Target.window().fully().withName("Quest Page"));
			eyes.closeAsync();
		} finally  {
			eyes.abortAsync();
		}
	}

	private static void tearDown(WebDriver webDriver, ClassicRunner runner) {
		webDriver.quit();

		TestResultsSummary allTestResults = runner.getAllTestResults(true);
		System.out.println(allTestResults);
	}

}
