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

import java.util.List;

import org.openqa.selenium.support.FindBy;

import ru.yandex.qatools.htmlelements.element.*;

public class Pagination extends HtmlElement {
	
	@FindBy(tagName="li")
	private List<PaginationElement> pagination;
	
	public int pageCount() {
		return pagination.size();
	}
	
	public String text(int pageNumber) {
		return pagination.get(pageNumber-1).getText();
	}
	
	public String linkText(int pageNumber) {
		return pagination.get(pageNumber-1).getText();
	}
	
	public String reference(int pageNumber) {
		return pagination.get(pageNumber-1).reference();
	}
	
	public void click(int pageNumber) {
		pagination.get(pageNumber-1).click();
	}
}
