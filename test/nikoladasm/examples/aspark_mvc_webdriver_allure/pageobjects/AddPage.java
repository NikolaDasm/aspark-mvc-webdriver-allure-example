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

package nikoladasm.examples.aspark_mvc_webdriver_allure.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import nikoladasm.examples.aspark_mvc_webdriver_allure.TestCase;

public class AddPage extends AbstractPage {

	public AddPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public AddPage typeTestCaseData(TestCase testCase) {
		WebElement titleInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[2]"));
		titleInput.sendKeys(testCase.title);
		WebElement descriptionInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[3]"));
		descriptionInput.sendKeys(testCase.description);
		WebElement priorityInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[4]"));
		priorityInput.sendKeys(String.valueOf(testCase.priority));
		WebElement locationInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[5]"));
		locationInput.sendKeys(testCase.location);
		WebElement statusInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[6]"));
		statusInput.sendKeys(String.valueOf(testCase.status));
		WebElement stepsInput = driver().findElement(By.xpath("/html/body/main/form[1]/textarea[1]"));
		stepsInput.sendKeys(testCase.steps);
		WebElement expectedResultsInput = driver().findElement(By.xpath("/html/body/main/form[1]/textarea[2]"));
		expectedResultsInput.sendKeys(testCase.expectedResults);
		WebElement assignedToInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[7]"));
		assignedToInput.sendKeys(testCase.assignedTo);
		WebElement ownerInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[8]"));
		ownerInput.sendKeys(testCase.owner);
		WebElement versionInput = driver().findElement(By.xpath("/html/body/main/form[1]/input[9]"));
		versionInput.sendKeys(String.valueOf(testCase.version));
		return this;
	}

	public MainPage submitAndReturnToMainPage() {
		driver().findElement(By.xpath("/html/body/main/form[1]/button")).click();
		return new MainPage(driver);
	}

	public AddPage submit() {
		driver().findElement(By.xpath("/html/body/main/form[1]/button")).click();
		return this;
	}

	public String headerText() {
		return driver().findElement(By.xpath("/html/body/main/h3")).getText();
	}
}
