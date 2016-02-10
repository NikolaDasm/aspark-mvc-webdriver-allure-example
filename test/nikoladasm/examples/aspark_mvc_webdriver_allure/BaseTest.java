/*
 *  Examples: ASpark MVC WEBDriver Allure
 *  Copyright (C) 2016  Nikolay Platov
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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.WebDriver;

import ru.yandex.qatools.allure.annotations.Step;

import nikoladasm.examples.aspark_mvc_webdriver_allure.dataobjects.*;
import nikoladasm.examples.aspark_mvc_webdriver_allure.pageobjects.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

import static nikoladasm.examples.aspark_mvc_webdriver_allure.pageobjects.AbstractPage.*;

public class BaseTest {

	protected WebDriver driver;
	private MainPage mainPage;
	private EditPage editPage;
	private DeletePage deletePage;
	private AddPage addPage;
	private FindPage findPage;
	
	private List<TestCaseWithLinks> testCases(List<TestCasesTableRow> rows) {
		List<TestCaseWithLinks> result = new LinkedList<>();
		for (int i=0; i<rows.size(); i++) {
			TestCasesTableRow row = rows.get(i);
			TestCase testCase = new TestCase();
			testCase.id = Long.valueOf(row.cellsText().get(ID_COLUMN_INDEX));
			testCase.title = row.cellsText().get(TITLE_COLUMN_INDEX);
			testCase.description = row.cellsText().get(DESCRIPTION_COLUMN_INDEX);
			testCase.priority = Integer.valueOf(row.cellsText().get(PRIORITY_COLUMN_INDEX));
			testCase.location = row.cellsText().get(LOCATION_COLUMN_INDEX);
			testCase.status = Integer.valueOf(row.cellsText().get(STATUS_COLUMN_INDEX));
			testCase.steps = row.cellsText().get(STEPS_COLUMN_INDEX);
			testCase.expectedResults = row.cellsText().get(EXPECTED_RESULTS_COLUMN_INDEX);
			testCase.assignedTo = row.cellsText().get(ASSIGNED_TO_COLUMN_INDEX);
			testCase.owner = row.cellsText().get(OWNER_COLUMN_INDEX);
			testCase.version = Integer.valueOf(row.cellsText().get(VERSION_COLUMN_INDEX));
			TestCaseWithLinks testCaseWithLinks = new TestCaseWithLinks();
			testCaseWithLinks.row = i;
			testCaseWithLinks.testCase = testCase;
			testCaseWithLinks.editText = row.editText();
			testCaseWithLinks.editReference = row.editReference();
			testCaseWithLinks.deleteText = row.deleteText();
			testCaseWithLinks.deleteReference = row.deleteReference();
			result.add(testCaseWithLinks);
		}
		return result;
	}
	
	protected TestCase testCase(Map<String,String> formData) {
		TestCase testCase = new TestCase();
		testCase.id = Long.valueOf(formData.get(ID_FIELD_NAME));
		testCase.title = formData.get(TITLE_FIELD_NAME);
		testCase.description = formData.get(DESCRIPTION_FIELD_NAME);
		testCase.priority = Integer.valueOf(formData.get(PRIORITY_FIELD_NAME));
		testCase.location = formData.get(LOCATION_FIELD_NAME);
		testCase.status = Integer.valueOf(formData.get(STATUS_FIELD_NAME));
		testCase.steps = formData.get(STEPS_FIELD_NAME);
		testCase.expectedResults = formData.get(EXPECTED_RESULTS_FIELD_NAME);
		testCase.assignedTo = formData.get(ASSIGNED_TO_FIELD_NAME);
		testCase.owner = formData.get(OWNER_FIELD_NAME);
		testCase.version = Integer.valueOf(formData.get(VERSION_FIELD_NAME));
		return testCase;
	}
	
	protected Map<String,Object> formData(TestCase testCase) {
		Map<String,Object> formData = new HashMap<>();
		formData.put(TITLE_FIELD_NAME, testCase.title);
		formData.put(DESCRIPTION_FIELD_NAME, testCase.description);
		formData.put(PRIORITY_FIELD_NAME, testCase.priority);
		formData.put(LOCATION_FIELD_NAME, testCase.location);
		formData.put(STATUS_FIELD_NAME, testCase.status);
		formData.put(STEPS_FIELD_NAME, testCase.steps);
		formData.put(EXPECTED_RESULTS_FIELD_NAME, testCase.expectedResults);
		formData.put(ASSIGNED_TO_FIELD_NAME, testCase.assignedTo);
		formData.put(OWNER_FIELD_NAME, testCase.owner);
		formData.put(VERSION_FIELD_NAME, testCase.version);
		return formData;
	}
	
	@Step("Open main page")
	protected void openMainPage() {
		mainPage = new MainPage(driver).open();
	}
	
	@Step("Verify main page title \"{0}\"")
	protected void verifyMainPageTitle(String title) {
		assertThat(mainPage.title(), is(equalTo("Test cases")));
	}
	
	@Step("Verify pagination")
	protected void verifyPagination(Pagination pagination) {
		assertThat(mainPage.pages(), is(equalTo(pagination.pages())));
		for (Entry<Integer,String> text : pagination.texts().entrySet())
			assertThat(mainPage.paginationText(text.getKey()), is(equalTo(text.getValue())));
		for (Entry<Integer,String> linkText : pagination.linkTexts().entrySet())
			assertThat(mainPage.paginationLinkText(linkText.getKey()), is(equalTo(linkText.getValue())));
		for (Entry<Integer,String> reference : pagination.references().entrySet())
			assertThat(mainPage.paginationReference(reference.getKey()), endsWith(reference.getValue()));
	}
	
	@Step("Verify test case on main page")
	protected void verifyTestCaseOnMainPage(TestCaseWithLinks expectedTestCase) {
		TestCaseWithLinks testCase =
			testCases(mainPage.testCasesTable()).get(expectedTestCase.row);
		assertThat(testCase, is(equalTo(expectedTestCase)));
	}
	
	@Step("Go to {0} page")
	protected void goToPage(int pageNumber) {
		mainPage.goToPage(pageNumber);
	}
	
	@Step("Edit test case {0}")
	protected void editTestCase(int rowNumber) {
		editPage = mainPage.goToEditPage(rowNumber+1);
	}
	
	@Step("Verify test case form")
	protected void verifyTestCaseForm(TestCase expectedTestCase) {
		TestCase testCase = testCase(editPage.readForm());
		assertThat(testCase, is(equalTo(expectedTestCase)));
	}

	@Step("Fill edit test case form")
	protected void fillEditTestCaseForm(TestCase testCase) {
		editPage.fillForm(formData(testCase));
	}
	
	@Step("Submit edit form and return to main page")
	protected void submitEditFormAndReturnToMainPage() {
		mainPage = editPage.submitAndReturnToMainPage();
	}
	
	@Step("Verify error message \"{0}\" on edit page")
	protected void verifyEditPageErrorMessage(String errMessage) {
		assertThat(editPage.headerText(), endsWith(errMessage));
	}

	@Step("Submit edit form")
	protected void submitEditForm() {
		editPage.submit();
	}
	
	@Step("Delete test case {0}")
	protected void deleteTestCase(int rowNumber) {
		deletePage = mainPage.goToDeletePage(rowNumber+1);
	}
	
	@Step("Verify test case on delete page")
	protected void verifyTestCaseOnDeletePage(TestCase expectedTestCase) {
		TestCase testCase =
			testCases(deletePage.testCasesTable()).get(0).testCase;
		assertThat(testCase, is(equalTo(expectedTestCase)));
	}
	
	@Step("Submit delete and return to main page")
	protected void submitDeleteAndReturnToMainPage() {
		mainPage = deletePage.submitAndReturnToMainPage();
	}
	
	@Step("Add test case")
	protected void addTestCase() {
		addPage = mainPage.goToAddPage();
	}
	
	@Step("Fill add test case form")
	protected void fillAddTestCaseForm(TestCase testCase) {
		addPage.fillForm(formData(testCase));
	}
	
	@Step("Submit add form and return to main page")
	protected void submitAddFormAndReturnToMainPage() {
		mainPage = addPage.submitAndReturnToMainPage();
	}
	
	@Step("Submit add form")
	protected void submitAddForm() {
		addPage.submit();
	}
	
	@Step("Verify error message \"{0}\" on delete page")
	protected void verifyAddPageErrorMessage(String errMessage) {
		assertThat(addPage.headerText(), endsWith(errMessage));
	}

	@Step("Fill find test case form")
	protected void fillFindTestCaseForm(String title) {
		mainPage.typeTitleInputText(title);
	}
	
	@Step("Submit find form and go to find page")
	protected void submitFindFormAndGoToFindPage() {
		findPage = mainPage.submitAndGoToFindPage();
	}

	@Step("Verify test case on find page")
	protected void verifyTestCaseOnFindPage(TestCase expectedTestCase) {
		TestCase testCase =
			testCases(findPage.testCasesTable()).get(0).testCase;
		assertThat(testCase, is(equalTo(expectedTestCase)));
	}
}
