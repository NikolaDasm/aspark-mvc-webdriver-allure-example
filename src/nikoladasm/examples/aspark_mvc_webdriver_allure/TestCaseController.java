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

import java.util.List;

import nikoladasm.aspark.Request;
import nikoladasm.aspark.Response;

import static nikoladasm.aspark.ASpark.modelAndView;

import static java.util.Objects.requireNonNull;

public class TestCaseController {
	
	public static final int TEST_CASES_PER_PAGE = 20;
	
	private TestCaseDao dao;
	private TestCaseView view;
	
	public TestCaseController(TestCaseDao dao, TestCaseView view) {
		this.dao = dao;
		this.view = view;
	}
	
	public Object mainPage(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		String pageParam = request.queryParams("page");
		int page = (pageParam == null) ? 1 : Integer.valueOf(pageParam);
		int pages = dao.getTestCaseCount()/TEST_CASES_PER_PAGE;
		if (TEST_CASES_PER_PAGE * pages < dao.getTestCaseCount())
			pages++;
		final int pageCount = (pages < 1) ? 1 : pages;
		List<TestCase> model =
			dao.getTestCases((page-1)*TEST_CASES_PER_PAGE, TEST_CASES_PER_PAGE);
		return modelAndView(() -> {
			return view.htmlMainPage(model, page, pageCount);
		});
	}
	
	public Object addPage(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		return modelAndView(() -> {
			return view.htmlAddEditPage(true, null, null);
		});
	}
	
	public Object editPage(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		String idParam = request.queryParams("id");
		if (idParam == null) {
			response.redirect("/");
			return modelAndView(() -> {
				return "";
			});
		}
		long id = Long.valueOf(idParam);
		TestCase testCase = dao.getTestCase(id);
		return modelAndView(() -> {
			return view.htmlAddEditPage(false, null, testCase);
		});
	}
	
	public Object processAddForm(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		TestCase testCase = new TestCase();
		String err = processForm(request, testCase);
		testCase.id = dao.getLastId()+1;
		if (err == null) {
			try {
				dao.putTestCase(testCase);
			} catch (IllegalArgumentException e) {
				String errMsg = e.getMessage();
				return modelAndView(() -> {
					return view.htmlAddEditPage(true, errMsg, testCase);
				});
			}
			response.redirect("/");
			return modelAndView(() -> {
				return "";
			});
		} else {
			String errMsg = err;
			return modelAndView(() -> {
				return view.htmlAddEditPage(true, errMsg, testCase);
			});
		}
	}
	
	public Object processEditForm(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		TestCase testCase = new TestCase();
		String err = processForm(request, testCase);
		long id = request.postMap("id").longValue();
		testCase.id = id;
		if (err == null) {
			try {
				dao.putTestCase(testCase);
			} catch (IllegalArgumentException e) {
				final String errMsg = e.getMessage();
				return modelAndView(() -> {
					return view.htmlAddEditPage(true, errMsg, testCase);
				});
			}
			response.redirect("/");
			return modelAndView(() -> {
				return "";
			});
		} else {
			final String errMsg = err;
			return modelAndView(() -> {
				return view.htmlAddEditPage(true, errMsg, testCase);
			});
		}
	}
	
	private String processForm(Request request, TestCase testCase) {
		String err = null;
		String title = request.postMap("title").value();
		requireNonNull(title);
		if (!title.trim().isEmpty()) {
			testCase.title = title;
		} else {	
			err = (err == null) ? "Title can't be empty" : err;
			testCase.title = "";
		}
		String description = request.postMap("description").value();
		requireNonNull(description);
		if (!description.trim().isEmpty()) {
			testCase.description = description;
		} else {
			err = (err == null) ? "Description can't be empty" : err;
			testCase.description = "";
		}
		int priority = request.postMap("priority").integerValue();
		if (priority > 0 && priority <= 5) {
			testCase.priority = priority;
		} else {
			err = (err == null) ? "Priority must be from 1 to 5" : err;
			testCase.priority = 1;
		}
		String location = request.postMap("location").value();
		requireNonNull(location);
		if (!location.trim().isEmpty()) {
			testCase.location = location;
		} else {
			err = (err == null) ? "Location can't be empty" : err;
			testCase.location = "";
		}
		int status = request.postMap("status").integerValue();
		if (status > 0 && status <= 5) {
			testCase.status = status;
		} else {
			err = (err == null) ? "Status must be from 1 to 5" : err;
		testCase.status = 1;
		}
		String steps = request.postMap("steps").value();
		requireNonNull(steps);
		if (!steps.trim().isEmpty()) {
			testCase.steps = steps;
		} else {
			err = (err == null) ? "Steps can't be empty" : err;
			testCase.steps = "";
		}
		String expectedResults = request.postMap("expected_results").value();
		requireNonNull(expectedResults);
		if (!expectedResults.trim().isEmpty()) {
			testCase.expectedResults = expectedResults;
		} else {
			err = (err == null) ? "Expected results can't be empty" : err;
			testCase.expectedResults = "";
		}
		String assignedTo = request.postMap("assigned_to").value();
		requireNonNull(assignedTo);
		if (!assignedTo.trim().isEmpty()) {
			testCase.assignedTo = assignedTo;
		} else {
			err = (err == null) ? "Assigned to can't be empty" : err;
			testCase.assignedTo = "";
		}
		String owner = request.postMap("owner").value();
		requireNonNull(owner);
		if (!owner.trim().isEmpty()) {
			testCase.owner = owner;
		} else {
			err = (err == null) ? "Owner to can't be empty" : err;
			testCase.owner = "";
		}
		int version = request.postMap("version").integerValue();
		if (version > 0) {
			testCase.version = version;
		} else {
			err = (err == null) ? "Version must be greater then 0" : err;
			testCase.version = 1;
		}
		return err;
	}
	
	public Object deletePage(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		String idParam = request.queryParams("id");
		if (idParam == null) {
			response.redirect("/");
			return modelAndView(() -> {
				return "";
			});
		}
		long id = Long.valueOf(idParam);
		TestCase testCase = dao.getTestCase(id);
		return modelAndView(() -> {
			return view.htmlDeleteConfirmationPage(testCase);
		});
	}
	
	public Object processDeleteForm(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		long id = request.postMap("id").longValue();
		dao.deleteTestCase(id);
		response.redirect("/");
		return modelAndView(() -> {
			return "";
		});
	}

	public Object processFindForm(Request request, Response response) {
		response.type("text/html; charset=UTF-8");
		String title = request.postMap("title").value();
		TestCase testCase = dao.getTestCase(title);
		if (testCase == null) {
			response.redirect("/");
			return modelAndView(() -> {
				return "";
			});
		}
		return modelAndView(() -> {
			return view.htmlFindPage(testCase);
		});
	}
}
