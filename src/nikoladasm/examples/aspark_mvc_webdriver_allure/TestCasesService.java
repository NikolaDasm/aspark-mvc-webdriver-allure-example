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

import static nikoladasm.aspark.ASpark.*;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

import nikoladasm.aspark.ASpark;

public class TestCasesService {

	private DB db;
	private String ipAddress;
	private int port;
	
	public TestCasesService(Config config) {
		this(config, DBMaker.fileDB(new File(config.dbFileName))
			.closeOnJvmShutdown()
			.make());
	}
	
	public TestCasesService(Config config, DB db) {
		this.db = db;
		ipAddress = config.ipAddress;
		port = config.port;
	}
	
	public void start() {
		ipAddress(ipAddress);
		port(port);
		init();
		awaitInitialization();
		ConcurrentNavigableMap<Long, String> map = db.treeMap("testCases");
		ConcurrentNavigableMap<String, Long> index = db.treeMap("testCasesTitleIndex");
		new Router(
			new TestCaseController(
				new TestCaseDao(map, index, db), new TestCaseView()
			)
		);
	}
	
	public void stop() {
		ASpark.stop();
		db.commit();
		db.close();
	}
	
	public void await() {
		ASpark.await();
	}
}
