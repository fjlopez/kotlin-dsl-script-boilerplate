package com.example.script

import kotlin.script.experimental.annotations.KotlinScript
import kotlin.script.experimental.api.*
import kotlin.script.experimental.jvm.dependenciesFromClassloader
import kotlin.script.experimental.jvm.jvm
import kotlin.script.experimental.jvm.mainArguments

@KotlinScript(
    // File extension for the script type
    fileExtension = "html.kts",
    // Compilation configuration for the script type
    compilationConfiguration = HtmlScriptConfiguration::class,
    // the class or object that defines script evaluation configuration for this type of scripts
    evaluationConfiguration = HtmlScriptEvaluationConfiguration::class
)
abstract class HtmlScript

/**
 * This definition is the same as [HtmlScript] but the [args] parameter is required by IntelliJ IDEA to work as script.
 */
@Suppress("unused")
@KotlinScript(
    fileExtension = "html.kts",
    compilationConfiguration = HtmlScriptConfiguration::class,
    evaluationConfiguration = HtmlScriptEvaluationConfiguration::class
)
abstract class HtmlScriptIDE(val args: Array<String>)

object HtmlScriptConfiguration: ScriptCompilationConfiguration(
    {
        defaultImports("com.example.html.*")
        jvm {
            dependenciesFromClassloader(classLoader = HtmlScriptConfiguration::class.java.classLoader, wholeClasspath = true)
        }
        ide {
            // these scripts are recognized everywhere in the project structure
            acceptedLocations(ScriptAcceptedLocation.Everywhere)
        }
    }
)

object HtmlScriptEvaluationConfiguration : ScriptEvaluationConfiguration(
    {
        scriptsInstancesSharing(true)

        // before evaluation, call this handler to refine configuration properties
        refineConfigurationBeforeEvaluate(::configureConstructorArgsFromMainArgs)
    }
)

fun configureConstructorArgsFromMainArgs(context: ScriptEvaluationConfigurationRefinementContext): ResultWithDiagnostics<ScriptEvaluationConfiguration> {
    val mainArgs = context.evaluationConfiguration[ScriptEvaluationConfiguration.jvm.mainArguments]
    val res = if (context.evaluationConfiguration[ScriptEvaluationConfiguration.constructorArgs] == null && mainArgs != null) {
        context.evaluationConfiguration.with {
            constructorArgs(mainArgs)
        }
    } else context.evaluationConfiguration
    return res.asSuccess()
}