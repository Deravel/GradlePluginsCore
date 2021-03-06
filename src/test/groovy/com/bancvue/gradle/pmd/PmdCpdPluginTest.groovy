/**
 * Copyright 2013 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.gradle.pmd

import com.bancvue.gradle.test.AbstractPluginTest
import org.junit.Before
import org.junit.Test

class PmdCpdPluginTest extends AbstractPluginTest {

	PmdCpdPluginTest() {
		super(PmdCpdPlugin.PLUGIN_NAME)
	}

	@Before
	void setup() {
		project.apply(plugin: 'java')
		project.apply(plugin: PmdCpdPlugin.PLUGIN_NAME)
	}

	@Test
	void apply_ShouldConfigureTaskFromExtension() {
		project.sourceSets {
			main
		}

		CpdExtension extension = new CpdExtension()
		int expectedMinimumTokenCount = extension.minimumTokenCount + 10
		boolean expectedIgnoreLiterals = !extension.ignoreLiterals
		boolean expectedIgnoreIdentifiers = !extension.ignoreIdentifiers
		boolean expectedIgnoreFailures = !extension.ignoreFailures
		String expectedCpdXsltPath = "${extension.cpdXsltPath}/otherpath"
		project.cpd {
			ignoreFailures = expectedIgnoreFailures
			minimumTokenCount = expectedMinimumTokenCount
			ignoreLiterals = expectedIgnoreLiterals
			ignoreIdentifiers = expectedIgnoreIdentifiers
			cpdXsltPath = expectedCpdXsltPath
		}

		project.tasks.withType(Cpd) { Cpd task ->
			assert task != null
			assert task.ignoreFailures == expectedIgnoreFailures
			assert task.ignoreLiterals == expectedIgnoreLiterals
			assert task.ignoreIdentifiers == expectedIgnoreIdentifiers
			assert task.minimumTokenCount == expectedMinimumTokenCount
			assert task.cpdXsltPath == expectedCpdXsltPath
		}
	}

	@Test
	void apply_ShouldUseExtensionDefaults_IfValuesNotProvidedInTask() {
		project.sourceSets {
			main
		}

		CpdExtension extension = new CpdExtension()
		project.tasks.withType(Cpd) { Cpd task ->
			assert task != null
			assert task.ignoreFailures == extension.ignoreIdentifiers
			assert task.ignoreLiterals == extension.ignoreLiterals
			assert task.ignoreIdentifiers == extension.ignoreIdentifiers
			assert task.minimumTokenCount == extension.minimumTokenCount
			assert task.cpdXsltPath == extension.cpdXsltPath
		}
	}

}
