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

package nikoladasm.examples.aspark_mvc_webdriver_allure;

public class TestCase {
	public long id;
	public String title;
	public String description;
	public int priority;
	public String location;
	public int status;
	public String steps;
	public String expectedResults;
	public String assignedTo;
	public String owner;
	public int version;
	
	@Override
	public boolean equals(Object object) {
		if (object == null || !(object instanceof TestCase)) return false;
		TestCase testCase = (TestCase) object;
		return (title == null ? testCase.title == null : title.equals(testCase.title)) &&
			(description == null ? testCase.description == null : description.equals(testCase.description)) &&
			(location == null ? testCase.location == null : location.equals(testCase.location)) &&
			(steps == null ? testCase.steps == null : steps.equals(testCase.steps)) &&
			(expectedResults == null ? testCase.expectedResults == null : expectedResults.equals(testCase.expectedResults)) &&
			(assignedTo == null ? testCase.assignedTo == null : assignedTo.equals(testCase.assignedTo)) &&
			(owner == null ? testCase.owner == null : owner.equals(testCase.owner)) &&
			id == testCase.id &&
			priority == testCase.priority &&
			status == testCase.status &&
			version == testCase.version;
	}
}
