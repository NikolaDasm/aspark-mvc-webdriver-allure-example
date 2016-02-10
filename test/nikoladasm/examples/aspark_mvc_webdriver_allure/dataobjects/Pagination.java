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

package nikoladasm.examples.aspark_mvc_webdriver_allure.dataobjects;

import java.util.Map;

public class Pagination {
	private int pages;
	private Map<Integer,String> texts;
	private Map<Integer,String> linkTexts;
	private Map<Integer,String> references;
	
	public int pages() {
		return pages;
	}
	
	public Map<Integer,String> texts() {
		return texts;
	}
	
	public Map<Integer,String> linkTexts() {
		return linkTexts;
	}
	
	public Map<Integer,String> references() {
		return references;
	}
}
