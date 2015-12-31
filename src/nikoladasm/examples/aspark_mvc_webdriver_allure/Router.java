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

package nikoladasm.examples.aspark_mvc_webdriver_allure;

import static nikoladasm.aspark.ASpark.*;

public class Router {

	private TestCaseController tcController;
	
	public Router(TestCaseController tcController) {
		this.tcController = tcController;
		initRoutes();
	}
	
	private void initRoutes() {
		defaultResponseTransformer(DEFAULT_VIEW_ENGINE);
		get("/", tcController::mainPage);
		get("/addtestcase", tcController::addPage);
		post("/addtestcase", tcController::processAddForm);
		get("/edittestcase", tcController::editPage);
		post("/edittestcase", tcController::processEditForm);
		get("/deletetestcase", tcController::deletePage);
		post("/deletetestcase", tcController::processDeleteForm);
		post("/findtestcase", tcController::processFindForm);
	}
}
