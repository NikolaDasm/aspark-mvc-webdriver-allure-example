/*
 *  Examples: ASpark MVC WEBDriver Allure
 *  Copyright (C) 2015-2016  Nikolay Platov
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nikoladasm.examples.aspark_mvc_webdriver_allure;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.ConcurrentNavigableMap;

import org.junit.After;
import org.openqa.selenium.htmlunit.*;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.introspector.BeanAccess;

import ru.yandex.qatools.allure.annotations.*;

import nikoladasm.examples.aspark_mvc_webdriver_allure.TestCasesService;
import nikoladasm.examples.aspark_mvc_webdriver_allure.dataobjects.Pagination;
import nikoladasm.examples.aspark_mvc_webdriver_allure.dataobjects.TestCaseWithLinks;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mapdb.DB;
import org.mapdb.DBMaker;

@Title("Basic UI test suite")
public class UITest extends BaseTest {

	private static TestCasesService service;
	
	private static DB db;
	private Pagination pagination;
	private TestCaseWithLinks testCase18;
	private TestCaseWithLinks testCase25;
	private TestCaseWithLinks testCase5;
	private TestCaseWithLinks testCase5m;
	private TestCaseWithLinks testCase5i;
	private TestCaseWithLinks testCase10;
	private TestCaseWithLinks testCase11ad;
	private TestCaseWithLinks testCase51;
	private TestCaseWithLinks testCase51i;
	private TestCaseWithLinks testCase11;
	
	@BeforeClass
	public static void serviceStart() {
		db = DBMaker.memoryDB().make();
		Config config = new Config();
		config.ipAddress = "0.0.0.0";
		config.port = 8080;
		service = new TestCasesService(config, db);
		service.start();
	}
	
	@AfterClass
	public static void stopService() {
		service.stop();
	}
	
	@Before
	public void setupWD() throws FileNotFoundException {
		String testDataPath = System.getProperty("user.dir")+"/testdata/";
		driver = new HtmlUnitDriver();
		ConcurrentNavigableMap<Long, String> map = db.treeMap("testCases");
		ConcurrentNavigableMap<String, Long> index = db.treeMap("testCasesTitleIndex");
		TestCaseUtil.loadFromCsv(testDataPath+"testcases.csv", ";", map, index);
		db.commit();
		Yaml yaml = new Yaml();
		yaml.setBeanAccess(BeanAccess.FIELD);
		pagination = yaml.loadAs(new FileInputStream(testDataPath+"pagination.yml"), Pagination.class);
		testCase18 = yaml.loadAs(new FileInputStream(testDataPath+"testcase18.yml"), TestCaseWithLinks.class);
		testCase25 = yaml.loadAs(new FileInputStream(testDataPath+"testcase25.yml"), TestCaseWithLinks.class);
		testCase5 = yaml.loadAs(new FileInputStream(testDataPath+"testcase5.yml"), TestCaseWithLinks.class);
		testCase5m = yaml.loadAs(new FileInputStream(testDataPath+"testcase5m.yml"), TestCaseWithLinks.class);
		testCase5i = yaml.loadAs(new FileInputStream(testDataPath+"testcase5i.yml"), TestCaseWithLinks.class);
		testCase10 = yaml.loadAs(new FileInputStream(testDataPath+"testcase10.yml"), TestCaseWithLinks.class);
		testCase11ad = yaml.loadAs(new FileInputStream(testDataPath+"testcase11ad.yml"), TestCaseWithLinks.class);
		testCase51 = yaml.loadAs(new FileInputStream(testDataPath+"testcase51.yml"), TestCaseWithLinks.class);
		testCase51i = yaml.loadAs(new FileInputStream(testDataPath+"testcase51i.yml"), TestCaseWithLinks.class);
		testCase11 = yaml.loadAs(new FileInputStream(testDataPath+"testcase11.yml"), TestCaseWithLinks.class);
	}
	
	@After
	public void closeWD() {
		ConcurrentNavigableMap<Long, String> map = db.treeMap("testCases");
		ConcurrentNavigableMap<String, Long> index = db.treeMap("testCasesTitleIndex");
		map.clear();
		index.clear();
		db.commit();
		driver.quit();
	}
	
	@Test
	public void shouldBeOpenMainPage() {
		openMainPage();
		verifyMainPageTitle("Test cases");
	}
	
	@Test
	public void shouldBeShowedPaginationOnMainPage() {
		openMainPage();
		verifyPagination(pagination);
	}
	
	@Test
	public void shouldBeShowedTestCase18And25() {
		openMainPage();
		verifyTestCaseOnMainPage(testCase18);
		goToPage(2);
		verifyTestCaseOnMainPage(testCase25);
	}
	
	@Test
	public void shouldBeEditTestCase() {
		openMainPage();
		editTestCase(testCase5.row);
		verifyTestCaseForm(testCase5.testCase);
		fillEditTestCaseForm(testCase5m.testCase);
		submitEditFormAndReturnToMainPage();
		verifyTestCaseOnMainPage(testCase5m);
	}
	
	@Test
	public void shouldBeErrorMessageWithEmptyStepsFieldOnEditPage() {
		openMainPage();
		editTestCase(testCase5.row);
		fillEditTestCaseForm(testCase5i.testCase);
		submitEditForm();
		verifyEditPageErrorMessage("Steps can't be empty");
	}
	
	@Test
	public void shouldBeDeleteTestCase() {
		openMainPage();
		deleteTestCase(testCase10.row);
		verifyTestCaseOnDeletePage(testCase10.testCase);
		submitDeleteAndReturnToMainPage();
		verifyTestCaseOnMainPage(testCase11ad);
	}
	
	@Test
	public void shouldBeAddTestCase() {
		openMainPage();
		addTestCase();
		fillAddTestCaseForm(testCase51.testCase);
		submitAddFormAndReturnToMainPage();
		goToPage(3);
		verifyTestCaseOnMainPage(testCase51);
	}

	@Test
	public void shouldBeErrorMessageWithEmptyStepsFieldOnAddPage() {
		openMainPage();
		addTestCase();
		fillAddTestCaseForm(testCase51i.testCase);
		submitAddForm();
		verifyAddPageErrorMessage("Steps can't be empty");
	}
	
	@Test
	public void shouldBeFindTestCaseByTitle() {
		openMainPage();
		fillFindTestCaseForm("title11");
		submitFindFormAndGoToFindPage();
		verifyTestCaseOnFindPage(testCase11.testCase);
	}
}
