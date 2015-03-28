/*
 * Copyright 2010 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import grails.util.GrailsUtil

includeTargets << grailsScript('Compile')

configClassname = 'Config'

target('gmetrics': 'Run GMetrics') {
    depends(compile)

    runGmetrics()
}

private void runGmetrics()
{
    ant.taskdef(name: 'gmetrics', classname: 'org.gmetrics.ant.GMetricsTask')

    def config = loadConfig()

    String outputFile = config.outputFile ?: 'target/GMetricsReport.html'
    String reportType = config.reportType ?: 'org.gmetrics.report.BasicHtmlReportWriter'
    String reportTitle = config.reportTitle ?: 'GMetrics Report'
    String metricSetFilename = config.metricSetfilename ?: ''

    List includes = configureIncludes(config)

    println "Running Gmetrics ..."

    ant.gmetrics()
            {
                report(type: reportType) {
                    option(name: 'outputFile', value: outputFile)
                    option(name: 'title', value: reportTitle)
                }
                fileset(dir: '.', includes: includes.join(','))
            }

    println "GMetrics finished; report generated: $outputFile with title:$reportTitle"
}

private ConfigObject loadConfig()
{
    def classLoader = Thread.currentThread().contextClassLoader
    classLoader.addURL(new File(classesDirPath).toURL())
    try
    {
        def className = getProperty('configClassname')
        return new ConfigSlurper(GrailsUtil.environment).parse(classLoader.loadClass(className)).gmetrics
    }
    catch (ClassNotFoundException e)
    {
        return new ConfigObject()
    }
}

private int getConfigInt(config, String name, int defaultIfMissing)
{
    def value = config[name]
    return value instanceof Integer ? value : defaultIfMissing
}

private boolean getConfigBoolean(config, String name)
{
    def value = config[name]
    return value instanceof Boolean ? value : true
}

private List configureIncludes(config)
{
    List includes = []

    if (getConfigBoolean(config, 'processSrcGroovy'))
    {
        includes << 'src/groovy/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processControllers'))
    {
        includes << 'grails-app/controllers/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processDomain'))
    {
        includes << 'grails-app/domain/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processServices'))
    {
        includes << 'grails-app/services/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processTaglib'))
    {
        includes << 'grails-app/taglib/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processUtils'))
    {
        includes << 'grails-app/utils/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processTestUnit'))
    {
        includes << 'test/unit/**/*.groovy'
    }

    if (getConfigBoolean(config, 'processTestIntegration'))
    {
        includes << 'test/integration/**/*.groovy'
    }

    for (includeDir in config.extraIncludeDirs)
    {
        includes << "$includeDir/**/*.groovy"
    }

    return includes
}

try
{
    // Required for Grails 1.3 and later
    setDefaultTarget("gmetrics")
}
catch (MissingMethodException e)
{
    // Ignore. Older versions of Groovy/Grails do not implement this method
}

