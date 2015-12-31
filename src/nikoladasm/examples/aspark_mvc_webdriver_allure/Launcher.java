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

import java.io.Console;
import java.io.File;

import org.mapdb.DB;
import org.mapdb.DBMaker;

public class Launcher {

	public static void main(String[] args) {
		String ipAddress = System.getProperty("server.ipaddress", "0.0.0.0");
		String port = System.getProperty("server.port", "8080");
		String dbFileName = System.getProperty("db.name", "testdb");
		DB db = DBMaker.fileDB(new File(dbFileName))
			.closeOnJvmShutdown()
			.make();
		TestCasesService service = new TestCasesService(ipAddress, Integer.valueOf(port), db);
		service.start();
		Console console = System.console();
		System.out.println("Application run on address \""+ipAddress+"\" and port \""+port+"\"");
		System.out.println("Enter \"x\" for exit");
		try {
			while (!console.readLine().trim().equalsIgnoreCase("x"));
		} catch (Exception e) {}
		service.stop();
	}
}
