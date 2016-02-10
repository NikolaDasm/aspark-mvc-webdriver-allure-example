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

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import j2html.tags.Tag;

import static j2html.TagCreator.*;

public class TestCaseView {
	
	public Object htmlMainPage(List<TestCase> model, int currentPage, int pageCount) {
		return document().render() +
			html().with(
				head().with(
					meta().withCharset("UTF-8"),
					title("Test cases")
				),
				body().with(
					main().with(
						h3("Test cases list"),
						form().withMethod("post").withAction("/findtestcase").withName("findtestcase").with(
							label("title").attr("for", "title"),
							input()
								.withType("text")
								.withName("title")
								.withPlaceholder("title")
								.isRequired(),
							button().withType("submit").withText("Find")
						),
						testCaseTable(model),
						pagination(currentPage, pageCount),
						br(),
						a("Add new test case").withHref("/addtestcase")
					)
				)
			).render();
	}
	
	private Tag testCaseTable(List<TestCase> model) {
		return table().with(
			thead().with(
				testCaseTableHeadRow()
				),
			tbody().with(
				model.stream().map(testCase ->
					testCaseTableRow(true, testCase)
				).collect(Collectors.toList())
			)
		);
	}
	
	private Tag testCaseTableRow(boolean withOperation, TestCase testCase) {
		return tr().with(
			td(new Long(testCase.id).toString()),
			td(testCase.title),
			td(testCase.description),
			td(testCase.owner),
			td(new Integer(testCase.priority).toString()),
			td(testCase.location),
			td(new Integer(testCase.status).toString()),
			td(testCase.steps),
			td(testCase.expectedResults),
			td(testCase.assignedTo),
			td(new Integer(testCase.version).toString()),
			withOperation ? td().with(
				a("edit").withHref("/edittestcase?id="+testCase.id)
			) : td(),
			withOperation ? td().with(
				a("delete").withHref("/deletetestcase?id="+testCase.id)
			) : td()
		);
	}
	
	private Tag testCaseTableHeadRow() {
		return tr().with(
			td("id"),
			td("Title"),
			td("Desctiption"),
			td("Owner"),
			td("Priority"),
			td("Location"),
			td("Status"),
			td("Steps"),
			td("Expected results"),
			td("Assigned to"),
			td("Version"),
			td(),
			td()
		);
	}
	
	private Tag pagination(int currentPage, int pageCount) {
		if (pageCount < 2) return ul().with();
		List<Tag> pageLinks = new LinkedList<>();
		for (int i = 1; i < pageCount+1; i++) {
			String pageNumber = new Integer(i).toString();
			pageLinks.add(
				li().with(
					(i == currentPage) ? span(pageNumber) : a(pageNumber).withHref("/?page="+pageNumber)
				)
			);
		}
		return ul().with(pageLinks);
	}
	
	public Object htmlAddEditPage(boolean addNew, String errMsg, TestCase testCase) {
		String msg = addNew ? "Add test case" : "Edit test case";
		return document().render() +
				html().with(
					head().with(
						meta().withCharset("UTF-8"),
						title(msg)
					),
					body().with(
						main().with(
							(errMsg == null) ? h3(msg) : h3("Error! "+errMsg),
							testCaseForm(addNew, testCase),
							br(),
							a("Main page").withHref("/")
						)
					)
				).render();
	}
	
	private Tag testCaseForm(boolean addNew, TestCase testCase) {
		String action = addNew ? "/addtestcase" : "/edittestcase";
		String buttonText = addNew ? "Add" : "Save";
		return form().withMethod("post").withAction(action).withName("testcase").with(
			input()
				.withType("hidden")
				.withName("id")
				.withValue((testCase == null) ? "" : new Long(testCase.id).toString()),
			label("title").attr("for", "title"),
			input()
				.withType("text")
				.withName("title")
				.withValue((testCase == null) ? "" : testCase.title)
				.withPlaceholder("title")
				.isRequired(),
			br(),
			label("description").attr("for", "description"),
			input()
				.withType("text")
				.withName("description")
				.withValue((testCase == null) ? "" : testCase.description)
				.withPlaceholder("description")
				.isRequired(),
			br(),
			label("priority [1-5]").attr("for", "priority"),
			input()
				.withType("text")
				.withName("priority")
				.withValue((testCase == null) ? "" : new Integer(testCase.priority).toString())
				.withPlaceholder("priority")
				.isRequired(),
			br(),
			label("location").attr("for", "location"),
			input()
				.withType("text")
				.withName("location")
				.withValue((testCase == null) ? "" : testCase.location)
				.withPlaceholder("location")
				.isRequired(),
			br(),
			label("status [1-5]").attr("for", "status"),
			input()
				.withType("text")
				.withName("status")
				.withValue((testCase == null) ? "" : new Integer(testCase.status).toString())
				.withPlaceholder("status")
				.isRequired(),
			br(),
			label("steps").attr("for", "steps"),
			textarea()
				.withName("steps")
				.withText((testCase == null) ? "" : testCase.steps)
				.withPlaceholder("steps")
				.isRequired(),
			br(),
			label("expected results").attr("for", "expected_results"),
			textarea()
				.withName("expected_results")
				.withText((testCase == null) ? "" : testCase.expectedResults)
				.withPlaceholder("expected results")
				.isRequired(),
			br(),
			label("assigned to").attr("for", "assigned_to"),
			input()
				.withType("text")
				.withName("assigned_to")
				.withValue((testCase == null) ? "" : testCase.assignedTo)
				.withPlaceholder("assigned to")
				.isRequired(),
			br(),
			label("owner").attr("for", "owner"),
			input()
				.withType("text")
				.withName("owner")
				.withValue((testCase == null) ? "" : testCase.owner)
				.withPlaceholder("owner")
				.isRequired(),
			br(),
			label("version").attr("for", "version"),
			input()
				.withType("text")
				.withName("version")
				.withValue((testCase == null) ? "" : new Integer(testCase.version).toString())
				.withPlaceholder("version")
				.isRequired(),
			br(),
			button().withType("submit").withText(buttonText)
		);
	}

	private Tag oneRowTable(boolean withOperation, TestCase testCase) {
		if (testCase == null) return table().with();
		return 
			table().with(
					thead().with(
						testCaseTableHeadRow()
					),
					tbody().with(
						testCaseTableRow(withOperation, testCase)
				)
			);	}
	
	public Object htmlDeleteConfirmationPage(TestCase testCase) {
		return document().render() +
			html().with(
				head().with(
					meta().withCharset("UTF-8"),
					title("Test case deletion")
				),
				body().with(
					main().with(
						h3("Delete this test case"),
						oneRowTable(false, testCase),
						form().withMethod("post").withAction("/deletetestcase").withName("deletetestcase").with(
							input()
								.withType("hidden")
								.withName("id")
								.withValue(new Long(testCase.id).toString()),
							br(),
							button().withType("submit").withText("Delete")
						),
						br(),
						a("Main page").withHref("/")
					)
				)
			).render();
	}

	public Object htmlFindPage(TestCase testCase) {
		return document().render() +
			html().with(
				head().with(
					meta().withCharset("UTF-8"),
					title("Find result")
				),
				body().with(
					main().with(
						h3("Test case"),
						oneRowTable(false, testCase),
						br(),
						a("Main page").withHref("/")
					)
				)
			).render();
	}
}
