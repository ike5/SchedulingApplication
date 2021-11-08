package test;

import java.io.*;

public class IOOperations {
    /*
3 File constructors:

public File(String pathname)
public File(File parent, String child)
public File(String parent, String child)

 */
    void copyStream(InputStream in, OutputStream out) throws IOException {
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
    }

    void copyStream(Reader in, Writer out) throws IOException {
        int b;
        while ((b = in.read()) != -1) {
            out.write(b);
        }
    }

    public void printData(InputStream is) throws IOException {
        int b;
        StringBuilder sb = new StringBuilder();
        while ((b = is.read()) != -1) {
            System.out.println(sb.append(b));
        }
    }

    public void readFile(String fileName) throws IOException {
        try (var fis = new FileInputStream(fileName)) {
            printData(fis);
        }
    }

    void copyTextFile(File src, File dest) throws IOException {
        try (var reader = new FileReader(src); var writer = new FileWriter(dest)) {
            int b;
            while ((b = reader.read()) != -1) {
                writer.write(b);
                writer.write("#");
            }
        }
    }

    void copyTextWithBuffer(File src, File dest) throws IOException {
        try (var reader = new BufferedReader(new FileReader(src));
             var writer = new BufferedWriter(new FileWriter(dest))) {
            String s;
            while((s = reader.readLine()) != null){
                writer.write(s.toUpperCase());
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        File fileName = new File("src/data/login_tracker.txt");
        File fileName2 = new File("src/data/another.txt");

        new IOOperations().copyTextWithBuffer(fileName, fileName2);
    }


}
