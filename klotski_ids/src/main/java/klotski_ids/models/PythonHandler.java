package klotski_ids.models;

import java.io.*;

/**
 * The PythonHandler class is responsible for executing Python code and handling the interaction
 * between Java and Python in a seamless manner.
 * It provides methods to execute Python scripts, pass arguments, and retrieve the output.
 */
public class PythonHandler {

    /**
     * Checks if Python is installed on the system.
     *
     * @return true if Python is installed, false otherwise.
     * @throws IOException if an I/O error occurs.
     */
    public static boolean isPythonInstalled() throws IOException {
        boolean isPythoninstalled = false;
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("python", "--version");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String output = reader.readLine();

            if (output != null && output.contains("Python")) {

                isPythoninstalled = true;

            } else {
                System.out.println("python not found");
            }
        } catch (IOException e) {
            //e.printStackTrace();
        }
        return isPythoninstalled;
    }

    /**
     * Executes a Python script as a separate process.
     *
     * @param pythonScriptPath the path to the Python script
     */
    public static void runPythonScript(String pythonScriptPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder("python", pythonScriptPath);
            Process process = pb.start();

            // Read the output stream
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            // Read the error stream
            InputStream errorStream = process.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorStream));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                System.err.println(errorLine);
            }

            int exitCode = process.waitFor();
            System.out.println("Python program exited with code: " + exitCode);

        } catch (IOException | InterruptedException e) {

            MyAlerts pythonAllert = new MyAlerts("Can't find python :/");
            pythonAllert.missingPythonAlert();

        }
    }

}