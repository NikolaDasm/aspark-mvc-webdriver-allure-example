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

package nikoladasm.examples.aspark_mvc_webdriver_allure.pageobjects;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import nikoladasm.examples.aspark_mvc_webdriver_allure.elements.Pagination;
import ru.yandex.qatools.htmlelements.element.*;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class MainPage extends AbstractPage {

	@FindBy(xpath="/html/body/main/ul")
	protected Pagination pagination;
	
	@FindBy(xpath="/html/body/main/table/tbody")
	private Table testCasesTable;
	
	@FindBy(xpath="/html/body/main/a")
	private Link add;
	
	@FindBy(xpath="/html/body/main/form")
	private Form findForm;

	public MainPage(WebDriver driver) {
		HtmlElementLoader.populatePageObject(this, driver);
		this.driver = driver;
	}
	
	public MainPage open() {
		driver().get("http://"+webServerIpAddress+":"+webServerPort+"/");
		return this;
	}
	
	public int pages() {
		return pagination.pageCount();
	}
	
	public String paginationText(int pageNumber) {
		return pagination.text(pageNumber);
	}

	public String paginationLinkText(int pageNumber) {
		return pagination.linkText(pageNumber);
	}
	
	public String paginationReference(int pageNumber) {
		return pagination.reference(pageNumber);
	}
	
	public List<TestCasesTableRow> testCasesTable() {
		return testCasesTable(testCasesTable, true);
	}
	
	public MainPage goToPage(int pageNumber) {
		pagination.click(pageNumber);
		return this;
	}
	
	public EditPage goToEditPage(int index) {
		testCasesTable.getRows().get(index-1).get(EDIT_COLUMN_INDEX).findElement(By.tagName("a")).click();
		return new EditPage(driver);
	}

	public DeletePage goToDeletePage(int index) {
		testCasesTable.getRows().get(index-1).get(DELETE_COLUMN_INDEX).findElement(By.tagName("a")).click();
		return new DeletePage(driver);
	}

	public AddPage goToAddPage() {
		add.click();
		return new AddPage(driver);
	}

	public MainPage typeTitleInputText(String title) {
		Map<String,Object> formData = new HashMap<>();
		formData.put(TITLE_FIELD_NAME, title);
		findForm.fill(formData);
		return this;
	}
	
	public FindPage submitAndGoToFindPage() {
		findForm.submit();
		return new FindPage(driver);
	}
}
