package ch.sbb.export_package_generator;

import com.google.common.reflect.ClassPath;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * The {@code PackageFinder} class is responsible for finding and appending all exported packages
 * to the project's MANIFEST.MF file. It scans for subpackages within specified root packages
 * and generates an 'Export-Package' entry in the manifest file.
 */
public class PackageFinder {

    /**
     * Delimiter used to separate package names in the 'Export-Package' entry.
     */
    private static final String PACKAGES_DELIMITER = "," + System.lineSeparator() + " ";

    /**
     * Path to the MANIFEST.MF file within the target/classes/META-INF directory.
     */
    private static final String MANIFEST_MF = "target/classes/META-INF/MANIFEST.MF";

    /**
     * Main method that finds all exported packages from the specified root packages and appends them
     * to the MANIFEST.MF file.
     *
     * @param args Array of root package names to scan for exported packages.
     * @throws IOException If an I/O error occurs writing to the manifest file.
     */
    public static void main(String[] args) throws IOException {
        Set<String> allExportedPackages = findAllExportedPackages(args);

        String exportPackagesContent = "Export-Package: " + String.join(PACKAGES_DELIMITER, allExportedPackages) + System.lineSeparator();

        appendToManifest(exportPackagesContent);
    }

    /**
     * Finds all exported packages starting from the specified root packages.
     *
     * @param rootPackages Array of root package names to start the search from.
     * @return A set of all exported package names.
     * @throws IOException If an I/O error occurs during package search.
     */
    public static Set<String> findAllExportedPackages(String[] rootPackages) throws IOException {
        Set<String> allExportedPackages = new HashSet<>();

        for (String rootPackage : rootPackages) {
            Set<String> subPackages = findSubPackages(rootPackage);
            allExportedPackages.addAll(subPackages);
        }

        return allExportedPackages;
    }

    /**
     * Finds all subpackages for a given package name.
     *
     * @param packageName The name of the package to find subpackages for.
     * @return A set of subpackage names.
     * @throws IOException If an I/O error occurs during classpath scanning.
     */
    public static Set<String> findSubPackages(String packageName) throws IOException {
        ClassLoader systemClassLoader = ClassLoader.getSystemClassLoader();
        return ClassPath.from(systemClassLoader)
                .getTopLevelClassesRecursive(packageName)
                .stream()
                .map(ClassPath.ClassInfo::getPackageName)
                .collect(Collectors.toCollection(TreeSet::new));
    }

    /**
     * Appends the 'Export-Package' entry to the MANIFEST.MF file.
     *
     * @param exportPackagesContent The content to append to the manifest file.
     * @throws IOException If an I/O error occurs writing to the manifest file.
     */
    public static void appendToManifest(String exportPackagesContent) throws IOException {
        Path path = Paths.get(MANIFEST_MF);
        Files.write(path, exportPackagesContent.getBytes(), StandardOpenOption.APPEND);

        System.out.printf("'Export-Package' entry has been added to file: %s%n", path.toAbsolutePath());
    }

}
