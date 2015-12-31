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

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import ru.yandex.qatools.allure.junit.AllureRunListener;

public class AllureJUnit4 extends BlockJUnit4ClassRunner {

	public AllureJUnit4(Class<?> clazz) throws InitializationError {
		super(clazz);
	}
	
	@Override
	public void run(RunNotifier notifier){
		notifier.addListener(new AllureRunListener());
		super.run(notifier);
	}
}
