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

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.Form;
import ru.yandex.qatools.htmlelements.element.Table;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class DeletePage extends AbstractPage {

	@FindBy(xpath="/html/body/main/table/tbody")
	private Table testCasesTable;
	
	@FindBy(xpath="/html/body/main/form")
	private Form form;
	
	public DeletePage(WebDriver driver) {
		HtmlElementLoader.populatePageObject(this, driver);
		this.driver = driver;
	}

	public List<TestCasesTableRow> testCasesTable() {
		return testCasesTable(testCasesTable, false);
	}
	
	public MainPage submitAndReturnToMainPage() {
		form.submit();
		return new MainPage(driver);
	}
}
