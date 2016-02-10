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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import ru.yandex.qatools.htmlelements.element.*;

public abstract class AbstractPage {

	public static final int ID_COLUMN_INDEX = 0;
	public static final int TITLE_COLUMN_INDEX = 1;
	public static final int DESCRIPTION_COLUMN_INDEX = 2;
	public static final int OWNER_COLUMN_INDEX = 3;
	public static final int PRIORITY_COLUMN_INDEX = 4;
	public static final int LOCATION_COLUMN_INDEX = 5;
	public static final int STATUS_COLUMN_INDEX = 6;
	public static final int STEPS_COLUMN_INDEX = 7;
	public static final int EXPECTED_RESULTS_COLUMN_INDEX = 8;
	public static final int ASSIGNED_TO_COLUMN_INDEX = 9;
	public static final int VERSION_COLUMN_INDEX = 10;
	public static final int EDIT_COLUMN_INDEX = 11;
	public static final int DELETE_COLUMN_INDEX = 12;

	public static final String ID_FIELD_NAME = "id";
	public static final String TITLE_FIELD_NAME = "title";
	public static final String DESCRIPTION_FIELD_NAME = "description";
	public static final String PRIORITY_FIELD_NAME = "priority";
	public static final String LOCATION_FIELD_NAME = "location";
	public static final String STATUS_FIELD_NAME = "status";
	public static final String STEPS_FIELD_NAME = "steps";
	public static final String EXPECTED_RESULTS_FIELD_NAME = "expected_results";
	public static final String ASSIGNED_TO_FIELD_NAME = "assigned_to";
	public static final String OWNER_FIELD_NAME = "owner";
	public static final String VERSION_FIELD_NAME = "version";
	
	public static class TestCasesTableRow {
		private List<String> cellsText;
		private String editText;
		private String editReference;
		private String deleteText;
		private String deleteReference;
		
		public TestCasesTableRow(
				List<String> cellsText,
				String editText,
				String editReference,
				String deleteText,
				String deleteReference) {
			this.cellsText = cellsText;
			this.editText = editText;
			this.editReference = editReference;
			this.deleteText = deleteText;
			this.deleteReference = deleteReference;
		}
		
		public List<String> cellsText() {
			return cellsText;
		}
		
		public String editText() {
			return editText;
		}
		
		public String editReference() {
			return editReference;
		}
		
		public String deleteText() {
			return deleteText;
		}
		
		public String deleteReference() {
			return deleteReference;
		}
	}
	
	protected String webServerIpAddress = System.getProperty("webserver.ip.address", "127.0.0.1");
	protected String webServerPort = System.getProperty("webserver.port", "8080");
	
	protected WebDriver driver;
	protected Set<String> fieldsNames;
	
	protected WebDriver driver() {
		return driver;
	}

	public String title() {
		return driver().getTitle();
	}
	
	protected List<TestCasesTableRow> testCasesTable(Table testCasesTable, boolean withLink) {
		List<TestCasesTableRow> result = new LinkedList<>();
		for (List<WebElement> row : testCasesTable.getRows()) {
			List<String> cellsText = new LinkedList<>();
			for (int i=ID_COLUMN_INDEX; i<EDIT_COLUMN_INDEX; i++) {
				cellsText.add(row.get(i).getText());
			}
			String editText = null;
			String editReference = null;
			String deleteText = null;
			String deleteReference = null;
			if (withLink) {
				Link edit = new Link(row.get(EDIT_COLUMN_INDEX).findElement(By.tagName("a")));
				Link delete = new Link(row.get(DELETE_COLUMN_INDEX).findElement(By.tagName("a")));
				editText = edit.getText();
				deleteText = delete.getText();
				try {
					URI editUri = new URI(edit.getReference());
					String editQuery = editUri.getQuery();
					String editFragment = editUri.getFragment();
					editReference = editUri.getPath();
					if (editFragment != null && !editFragment.isEmpty()) editReference += "#"+editFragment;
					if (editQuery != null && !editQuery.isEmpty()) editReference += "?"+editQuery;
					URI deleteUri = new URI(delete.getReference());
					String deleteQuery = deleteUri.getQuery();
					String deleteFragment = deleteUri.getFragment();
					deleteReference = deleteUri.getPath();
					if (deleteFragment != null && !deleteFragment.isEmpty()) deleteReference += "#"+deleteFragment;
					if (deleteQuery != null && !deleteQuery.isEmpty()) deleteReference += "?"+deleteQuery;
				} catch (URISyntaxException e) {
					editReference = null;
					deleteReference = null;
				}
			}
			TestCasesTableRow trow = new TestCasesTableRow(
					cellsText,
					editText,
					editReference,
					deleteText,
					deleteReference);
			result.add(trow);
		}
		return result;
	}
	
	protected Set<String> fieldsNames() {
		if (fieldsNames != null) return fieldsNames;
		fieldsNames = new HashSet<>();
		fieldsNames.add(ASSIGNED_TO_FIELD_NAME);
		fieldsNames.add(DESCRIPTION_FIELD_NAME);
		fieldsNames.add(EXPECTED_RESULTS_FIELD_NAME);
		fieldsNames.add(ID_FIELD_NAME);
		fieldsNames.add(LOCATION_FIELD_NAME);
		fieldsNames.add(OWNER_FIELD_NAME);
		fieldsNames.add(PRIORITY_FIELD_NAME);
		fieldsNames.add(STATUS_FIELD_NAME);
		fieldsNames.add(STEPS_FIELD_NAME);
		fieldsNames.add(TITLE_FIELD_NAME);
		fieldsNames.add(VERSION_FIELD_NAME);
		return fieldsNames;
	}
}
