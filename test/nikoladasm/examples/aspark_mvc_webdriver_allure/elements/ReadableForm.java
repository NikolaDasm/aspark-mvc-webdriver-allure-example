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

package nikoladasm.examples.aspark_mvc_webdriver_allure.elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

import ru.yandex.qatools.htmlelements.element.Form;
import ru.yandex.qatools.htmlelements.element.TextInput;

public class ReadableForm extends Form {

	public ReadableForm(WebElement wrappedElement) {
		super(wrappedElement);
	}

	public Map<String,String> read(Set<String> names) {
		Map<String,String> result = new HashMap<>();
		for (String name : names) {
			WebElement elementToRead = findElementByKey(name);
			String value = elementToRead.getAttribute("value");
			result.put(name, value);
		}
		return result;
	}
	
	@Override
	protected void fillInput(WebElement element, String value) {
		TextInput input = new TextInput(element);
		input.clear();
		input.sendKeys(value);
	}
}
