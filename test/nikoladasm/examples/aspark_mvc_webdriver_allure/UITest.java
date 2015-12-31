/*
 *  Examples: ASpark MVC WEBDriver Allure
 *  Copyright (C) 2015  Nikolay Platov
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nikoladasm.examples.aspark_mvc_webdriver_allure;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.concurrent.ConcurrentNavigableMap;

import org.junit.After;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.*;

import nikoladasm.examples.aspark_mvc_webdriver_allure.TestCase;
import nikoladasm.examples.aspark_mvc_webdriver_allure.TestCasesService;
import nikoladasm.examples.aspark_mvc_webdriver_allure.pageobjects.*;
import ru.yandex.qatools.allure.annotations.*;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mapdb.DB;
import org.mapdb.DBMaker;

@Title("Basic UI test suite")
@RunWith(AllureJUnit4.class)
public class UITest {

	private static TestCasesService service;
	
	private WebDriver driver;
	private static DB db;
	
	@BeforeClass
	public static void serviceStart() {
		db = DBMaker.memoryDB().make();
		service = new TestCasesService("0.0.0.0", 8080, db);
		service.start();
	}
	
	@AfterClass
	public static void stopService() {
		service.stop();
	}
	
	@Before
	public void setupWD() {
		driver = new HtmlUnitDriver();
		ConcurrentNavigableMap<Long, String> map = db.treeMap("testCases");
		ConcurrentNavigableMap<String, Long> index = db.treeMap("testCasesTitleIndex");
		map.clear();
		index.clear();
		db.commit();
		TestCaseUtil.loadFromCsv(System.getProperty("user.dir")+"/testdata/testcases.csv", ";", map, index);
		db.commit();
	}
	
	@After
	public void closeWD() {
		driver.close();
	}
	
	@Test
	public void shouldBeOpenMainPage() {
		String title = new MainPage(driver).open().title();
		assertThat(title, is(equalTo("Test cases")));
	}
	
	@Test
	public void shouldBeShowedNavigationOnMainPage() {
		MainPage mainPage = new MainPage(driver).open();
		assertThat(mainPage.navigationText(1), is(equalTo("1")));
		assertThat(mainPage.navigationLinkText(2), is(equalTo("2")));
		assertThat(mainPage.navigationLink(2), endsWith("/?page=2"));
		assertThat(mainPage.navigationLinkText(3), is(equalTo("3")));
		assertThat(mainPage.navigationLink(3), endsWith("/?page=3"));
	}
	
	@Test
	public void shouldBeShowedTestCase18And25() {
		MainPage mainPage = new MainPage(driver).open();
		assertThat(mainPage.testCaseTitleText(18), is(equalTo("title18")));
		assertThat(mainPage.testCaseEditLink(18), endsWith("/edittestcase?id=18"));
		assertThat(mainPage.testCaseDeleteLink(18), endsWith("/deletetestcase?id=18"));
		mainPage.goToPage(2);
		assertThat(mainPage.testCaseTitleText(5), is(equalTo("title25")));
		assertThat(mainPage.testCaseEditLink(5), endsWith("/edittestcase?id=25"));
		assertThat(mainPage.testCaseDeleteLink(5), endsWith("/deletetestcase?id=25"));
	}
	
	@Test
	public void shouldBeEditTestCase() {
		MainPage mainPage = new MainPage(driver).open();
		EditPage editPage = mainPage.goToEditPage(5);
		assertThat(editPage.testCaseStepsText(), is(equalTo("steps5")));
		editPage
			.typeTestCaseSteps("steps after edited")
			.typeTestCaseVersion(2);
		mainPage = editPage.submitAndReturnToMainPage();
		assertThat(mainPage.testCaseStepsText(5), is(equalTo("steps after edited")));
		assertThat(mainPage.testCaseVersionText(5), is(equalTo("2")));
	}
	
	@Test
	public void shouldBeErrorMessageWithEmptyStepsFieldOnEditPage() {
		MainPage mainPage = new MainPage(driver).open();
		EditPage editPage = mainPage.goToEditPage(5);
		editPage
			.typeTestCaseSteps(" ")
			.submit();
		assertThat(editPage.headerText(), endsWith("Steps can't be empty"));
	}
	
	@Test
	public void shouldBeDeleteTestCase() {
		MainPage mainPage = new MainPage(driver).open();
		DeletePage deletePage = mainPage.goToDeletePage(10);
		assertThat(deletePage.testCaseTitleText(), is(equalTo("title10")));
		mainPage = deletePage.submitAndReturnToMainPage();
		assertThat(mainPage.testCaseTitleText(10), is(equalTo("title11")));
		assertThat(mainPage.testCaseEditLink(10), endsWith("/edittestcase?id=11"));
		assertThat(mainPage.testCaseDeleteLink(10), endsWith("/deletetestcase?id=11"));
	}
	
	@Test
	public void shouldBeAddTestCase() {
		MainPage mainPage = new MainPage(driver).open();
		AddPage addPage = mainPage.goToAddPage();
		TestCase testCase = new TestCase();
		testCase.title = "title51";
		testCase.description = "description51";
		testCase.priority = 1;
		testCase.location = "location51";
		testCase.status = 1;
		testCase.steps = "steps51";
		testCase.expectedResults = "expectedResults51";
		testCase.assignedTo = "user51";
		testCase.owner = "user51";
		testCase.version = 1;
		addPage.typeTestCaseData(testCase);
		mainPage = addPage.submitAndReturnToMainPage();
		mainPage.goToPage(3);
		assertThat(mainPage.testCaseTitleText(11), is(equalTo("title51")));
		assertThat(mainPage.testCaseEditLink(11), endsWith("/edittestcase?id=51"));
		assertThat(mainPage.testCaseDeleteLink(11), endsWith("/deletetestcase?id=51"));
		assertThat(mainPage.testCaseVersionText(11), is(equalTo("1")));
	}

	@Test
	public void shouldBeErrorMessageWithEmptyStepsFieldOnAddPage() {
		MainPage mainPage = new MainPage(driver).open();
		AddPage addPage = mainPage.goToAddPage();
		TestCase testCase = new TestCase();
		testCase.title = "title51";
		testCase.description = "description51";
		testCase.priority = 1;
		testCase.location = "location51";
		testCase.status = 1;
		testCase.steps = " ";
		testCase.expectedResults = "expectedResults51";
		testCase.assignedTo = "user51";
		testCase.owner = "user51";
		testCase.version = 1;
		addPage
			.typeTestCaseData(testCase)
			.submit();
		assertThat(addPage.headerText(), endsWith("Steps can't be empty"));
	}
	
	@Test
	public void shouldBeFindTestCaseByTitle() {
		MainPage mainPage = new MainPage(driver).open();
		FindPage findPage =
			mainPage
				.typeTitleInputText("title11")
				.submitAndGoToFindPage();
		assertThat(findPage.testCaseTitleText(), is(equalTo("title11")));
	}
}
