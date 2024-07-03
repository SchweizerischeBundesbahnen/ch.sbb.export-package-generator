package ch.sbb.export_package_generator;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PackageFinderTest {

    public static final String ORG_JUNIT_JUPITER_API = "org.junit.jupiter.api";
    public static final String COM_GOOGLE_COMMON = "com.google.common";
    public static final String[] ROOT_PACKAGES = {ORG_JUNIT_JUPITER_API, COM_GOOGLE_COMMON};

    @Test
    void findAllExportedPackages() throws IOException {
        Set<String> allExportedPackages = PackageFinder.findAllExportedPackages(ROOT_PACKAGES);

        assertEquals(25, allExportedPackages.size());
    }

    @Test
    void findSubPackages() throws IOException {
        Set<String> subPackages1 = PackageFinder.findSubPackages(ORG_JUNIT_JUPITER_API);
        assertEquals(7, subPackages1.size());

        Set<String> subPackages2 = PackageFinder.findSubPackages(COM_GOOGLE_COMMON);
        assertEquals(18, subPackages2.size());
    }

}