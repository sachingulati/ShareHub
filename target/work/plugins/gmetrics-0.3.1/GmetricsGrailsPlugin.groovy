class GmetricsGrailsPlugin
{
    // the plugin version
    def version = "0.3.1"
    // the version or versions of Grails the plugin is designed for
    def grailsVersion = "1.2.2 > *"
    // the other plugins this plugin depends on
    def dependsOn = [:]
    // resources that are excluded from plugin packaging
    def pluginExcludes = [
            "grails-app/views/error.gsp"
    ]
    def author = "Scott Ryan"
    def authorEmail = "scott@theryansplace.com"
    def title = "An implementation of the GMetrics library (http://gmetrics.sourceforge.net/index.html) to allow analysis of Grails code."
    def description = '''\\ This plugin allows for analysis of grails code with the gmetrics library which calculates lines of code and complexity.'''
    // URL to the plugin's documentation
    def documentation = "http://grails.org/plugin/grails-gmetrics-plugin"

    def doWithWebDescriptor = { xml ->
    }

    def doWithSpring = {
    }

    def doWithDynamicMethods = { ctx ->
    }

    def doWithApplicationContext = { applicationContext ->
    }

    def onChange = { event ->

    }

    def onConfigChange = { event ->
    }
}
