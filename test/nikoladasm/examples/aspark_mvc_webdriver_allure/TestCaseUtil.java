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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;

import nikoladasm.examples.aspark_mvc_webdriver_allure.TestCase;

public class TestCaseUtil {
	
	public static void loadFromCsv(String fileName,
			String separator,
			Map<Long, String> testCaseMap,
			Map<String, Long> testCaseIndex) {
		List<String> lines;
		try {
			lines = Files.readAllLines(Paths.get(fileName), Charset.forName("UTF-8"));
		} catch (IOException e) {
			return;
		}
		for (String line : lines) {
			String[] components = line.trim().split(separator);
			TestCase testCase = new TestCase();
			testCase.id = Long.valueOf(components[0].trim());
			testCase.title = components[1].trim();
			testCase.description = components[2].trim();
			testCase.priority = Integer.valueOf(components[3].trim());
			testCase.location = components[4].trim();
			testCase.status = Integer.valueOf(components[5].trim());
			testCase.steps = components[6].trim();
			testCase.expectedResults = components[7].trim();
			testCase.assignedTo = components[8].trim();
			testCase.owner = components[9].trim();
			testCase.version = Integer.valueOf(components[10].trim());
			String jsonTestCase = new Gson().toJson(testCase);
			testCaseMap.put(testCase.id, jsonTestCase);
			testCaseIndex.put(testCase.title, testCase.id);
		}
	}
}
