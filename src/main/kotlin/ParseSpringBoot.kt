import org.gradle.tooling.GradleConnector
import org.gradle.tooling.model.idea.IdeaProject
import org.gradle.tooling.model.idea.IdeaSingleEntryLibraryDependency
import org.openrewrite.java.JavaParser
import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.attribute.BasicFileAttributes
import java.util.function.BiPredicate
import kotlin.streams.toList
fun main() {
    val srcDir = Paths.get(
        "../spring-boot")
    val predicate = BiPredicate<Path, BasicFileAttributes> { p, bfa ->
        bfa.isRegularFile
                && p.fileName.toString().endsWith(".java")
//                && p.fileName.toString() == "ApplicationContextAssert.java" //This file throws a StringIndexOutOfBoundsException
    }

    val paths = Files.find(srcDir, 999, predicate).toList()
    val start = System.nanoTime()
    val parser: JavaParser = JavaParser.fromJavaVersion()
        .classpath(getDependencies(srcDir.toAbsolutePath()))
        .logCompilationWarningsAndErrors(false) // optional, for quiet parsing
        .build()
    println("Loaded ${paths.size} files and project dependencies in ${(System.nanoTime() - start)*1e-6}ms")
    parser.parse(paths, srcDir)
    println("Parsed ${paths.size} files in ${(System.nanoTime() - start)*1e-6}ms")

}

    private val skippedScopes=setOf("RUNTIME","PROVIDED")

fun getDependencies(projectDir: Path): List<Path> {
    val connection= GradleConnector.newConnector()
        .forProjectDirectory(projectDir.toFile())
        .connect()
    val dependencies=HashSet<Path>()
    connection.use {
        val project=it.getModel(IdeaProject::class.java)
        for(module in project.modules) {
            for (dependency in module.dependencies) {
                if(skippedScopes.contains(dependency.scope.scope) )
                    continue
                if(dependency is IdeaSingleEntryLibraryDependency)
                    dependencies.add(dependency.file.toPath())
            }
        }
    }
    return dependencies.toList()
}