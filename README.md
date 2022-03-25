# Kotlin DSL Script

This DSL example shows how to create an executable script language with Kotlin.

The project has 3 modules:

- `dsl`, the language (we reuse the example of HTML builder from [Type-safe builders](https://kotlinlang.org/docs/type-safe-builders.html))
- `script`, the `HtmlScript` Kotlin Script definition.
- `host`, the command line scripting runner.

The `HtmlScript` script language:

- uses as extension `html.kts`
- imports by default `com.example.html.*`
- scripts can be run by IntelliJ (uses the `HtmlScriptIDE` definition)

Below is an example script:

```kotlin
val addressee = "World"

print(
    html {
        body {
            h1 { +"Hello, $addressee!" }
        }
    }
)
```

You can run the scripts:

- as an IDE script, `host/src/test/resources/test.html.kts`
- in tests see `host/src/test/kotlin/SimpleTest.kt`
- in command line, see below.

To test the command line, execute the following snippet:

```shell
rm -rf build
./gradlew clean assemble
unzip host/build/distributions/host.zip -d build
build/host/bin/host host/src/test/resources/test.html.kts
```

The expected output is:

```html
<html>
  <body>
    <h1>
      Hello, World!
    </h1>
  </body>
</html>
```