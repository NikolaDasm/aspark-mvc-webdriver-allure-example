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

public class FindPage extends AbstractPage {

	public FindPage(WebDriver driver) {
		this.driver = driver;
	}

	public String testCaseTitleText() {
		return driver().findElement(By.xpath("/html/body/main/table/tbody/tr/td[2]")).getText();
	}
}
