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
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;

import com.google.gson.Gson;

public class TestCaseDao {
	
	private ConcurrentNavigableMap<Long, String> testCaseMap;
	ConcurrentNavigableMap<String, Long> testCaseIndex;
	private DB db;
	
	public TestCaseDao(ConcurrentNavigableMap<Long, String> testCaseMap,
			ConcurrentNavigableMap<String, Long> testCaseIndex,
			DB db) {
		this.testCaseMap = testCaseMap;
		this.testCaseIndex = testCaseIndex;
		this.db = db;
	}
	
	public List<TestCase> getAllTestCases() {
		List<TestCase> result = new LinkedList<>();
		testCaseMap.forEach((id, jsonTestCase) -> {
			result.add(new Gson().fromJson(jsonTestCase, TestCase.class));
		});
		return result;
	}
	
	public List<TestCase> getTestCases(int startWith, int count) {
		List<TestCase> result = new LinkedList<>();
		int i = 0;
		for (Entry<Long, String> entry : testCaseMap.entrySet()) {
			if (i >= startWith + count) break;
			if (i >= startWith) result.add(new Gson().fromJson(entry.getValue(), TestCase.class));
			i++;
		}
		return result;
	}
	
	public TestCase getTestCase(long id) {
		String jsonTestCase = testCaseMap.get(id);
		return (jsonTestCase == null) ? null : new Gson().fromJson(jsonTestCase, TestCase.class);
	}
	
	public TestCase getTestCase(String title) {
		Long id = testCaseIndex.get(title);
		if (id == null) return null;
		String jsonTestCase = testCaseMap.get(id);
		return (jsonTestCase == null) ? null : new Gson().fromJson(jsonTestCase, TestCase.class);
	}
	
	public void putTestCase(TestCase testCase) {
		Long id = testCaseIndex.get(testCase.title);
		if (id != null && !id.equals(testCase.id))
			throw new IllegalArgumentException("Duplicate title");
		String jsonTestCase = new Gson().toJson(testCase);
		testCaseMap.put(testCase.id, jsonTestCase);
		testCaseIndex.put(testCase.title, testCase.id);
		db.commit();
	}
	
	public void deleteTestCase(long id) {
		String jsonTestCase = testCaseMap.get(id);
		if (jsonTestCase != null) {
			TestCase testCase = new Gson().fromJson(jsonTestCase, TestCase.class);
			testCaseIndex.remove(testCase.title);
			testCaseMap.remove(id);
			db.commit();
		}
	}
	
	public int getTestCaseCount() {
		return testCaseMap.size();
	}
	
	public long getLastId() {
		if (testCaseMap.isEmpty()) return 0;
		return testCaseMap.lastEntry().getKey();
	}
}
