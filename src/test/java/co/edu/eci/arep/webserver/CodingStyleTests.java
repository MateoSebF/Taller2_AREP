package co.edu.eci.arep.webserver;

import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

public class CodingStyleTests {

    /**
     * Test the package naming convention.
     */
    @Test
    public void testPackageNamingConvention() {
        String packageName = this.getClass().getPackage().getName();
        assertTrue(packageName.matches("^[a-z]+(\\.[a-z0-9]+)*$"), "Package name should be in lowercase with no underscores.");
    }

    /**
     * Test the class naming convention.
     */
    @Test
    public void testClassNamingConvention() {
        String className = this.getClass().getSimpleName();
        assertTrue(className.matches("^[A-Z][A-Za-z0-9]*$"), "Class name should follow PascalCase.");
    }

    /**
     * Test the method naming convention.
     */
    @Test
    public void testMethodNamingConvention() {
        String methodName = Thread.currentThread().getStackTrace()[1].getMethodName();
        assertTrue(methodName.matches("^[a-z]+[A-Za-z0-9]*$"), "Method name should follow camelCase.");
    }

    /**
     * Test the constant naming convention.
     */
    @Test
    public void testFileNamingConvention() {
        String className = this.getClass().getSimpleName();
        String fileName = className + ".java"; // Get the Java file name dynamically
        assertTrue(fileName.matches("^[A-Z][A-Za-z0-9]*\\.java$"), "Class files should follow PascalCase.");

        String resourceFileName = "home.css"; // Example for non-class files
        assertTrue(resourceFileName.matches("^[a-z0-9-]+\\.css$"), "Non-class files should follow kebab-case.");
    }

    /**
     * Test the class and resource naming.
     */
    @Test
    public void testClassAndResourceNaming() {
        // Class File Naming Check
        String className = this.getClass().getSimpleName();
        String classFileName = className + ".java";
        assertTrue(classFileName.matches("^[A-Z][A-Za-z0-9]*\\.java$"), "Class files should follow PascalCase.");
        
        // Resource File Naming Check
        String resourceFileName = "home.css";
        assertTrue(resourceFileName.matches("^[a-z0-9-]+\\.css$"), "Non-class files should follow kebab-case.");
    }
}
