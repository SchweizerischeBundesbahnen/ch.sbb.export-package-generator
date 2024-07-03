# Export-Package Generator

This project is used to find all exported packages and append them to the MANIFEST.MF file. It searches for sub-packages within the specified root packages and creates an "Export-Package" entry in the manifest file.

## Build

The binary artefact can be produced using maven:
```shell
mvn clean package
```

## Usage

To use the Export-Package Generator the following changes in `pom.xml` are required:

```xml
    <dependencies>
        <dependency>
            <groupId>ch.sbb</groupId>
            <artifactId>export-package-generator</artifactId>
            <version>1.0.0</version>
            <scope>runtime</scope>
        </dependency>
    ...
    </dependencies>

<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>${exec-maven-plugin.version}</version>
            <executions>
                <execution>
                    <goals>
                        <goal>exec</goal>
                    </goals>
                    <phase>process-classes</phase>
                </execution>
            </executions>
            <configuration>
                <executable>java</executable>
                <arguments>
                    <argument>-classpath</argument>
                    <classpath/>
                    <argument>ch.sbb.export_package_generator.PackageFinder</argument>
                    <argument>org.apache.poi</argument> <!-- root package -->
                </arguments>
            </configuration>
        </plugin>
    ...
    </plugins>
...
</build>
```
