/**
 * Copyright 2013 BancVue, LTD
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bancvue.gradle

import com.bancvue.gradle.categories.ProjectCategory
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.javadoc.Javadoc

public class JavaExtPlugin implements Plugin<Project> {

	static final String PLUGIN_NAME = 'java-ext'

	private Project project

	public void apply(Project project) {
		this.project = project
		project.apply(plugin: 'java')
		addSourcesJarTask()
		addJavadocJarTask()
	}

	private void addSourcesJarTask() {
		use(ProjectCategory) {
			project.createJarTask("sourcesJar", "main", "sources").configure {
				from project.sourceSets.main.allSource
			}
		}
	}

	private void addJavadocJarTask() {
		Javadoc javadocTask = project.tasks.getByName('javadoc')
		use(ProjectCategory) {
			project.createJarTask("javadocJar", "main", "javadoc").configure {
				dependsOn { javadocTask }
				from javadocTask.destinationDir
			}
		}
	}

}
