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

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;

import nikoladasm.examples.aspark_mvc_webdriver_allure.elements.ReadableForm;
import ru.yandex.qatools.htmlelements.element.TextBlock;
import ru.yandex.qatools.htmlelements.loader.HtmlElementLoader;

public class EditPage extends AbstractPage {

	@FindBy(xpath="/html/body/main/form")
	private ReadableForm testCaseForm;
	
	@FindBy(xpath="/html/body/main/h3")
	private TextBlock header;
	
	public EditPage(WebDriver driver) {
		HtmlElementLoader.populatePageObject(this, driver);
		this.driver = driver;
	}
	
	public Map<String,String> readForm() {
		return testCaseForm.read(fieldsNames());
	}
	
	public void fillForm(Map<String,Object> formData) {
		testCaseForm.fill(formData);
	}
	
	public MainPage submitAndReturnToMainPage() {
		testCaseForm.submit();
		return new MainPage(driver);
	}

	public EditPage submit() {
		testCaseForm.submit();
		return this;
	}
	
	public String headerText() {
		return header.getText();
	}
}
