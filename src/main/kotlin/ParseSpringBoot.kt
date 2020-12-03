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
    }
    val paths = Files.find(srcDir, 999, predicate).toList()
    val start = System.nanoTime()
    val parser: JavaParser = JavaParser.fromJavaVersion()
        .logCompilationWarningsAndErrors(false) // optional, for quiet parsing
        .build()
    println("Loaded ${paths.size} files in ${(System.nanoTime() - start)*1e-6}ms")
    parser.parse(paths, srcDir)
    println("Parsed ${paths.size} files in ${(System.nanoTime() - start)*1e-6}ms")

}