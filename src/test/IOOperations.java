package test;

import java.io.*;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;


public class IOOperations implements Serializable {
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
            while ((s = reader.readLine()) != null) {
                writer.write(s.toUpperCase());
                writer.newLine();
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
//        File fileName = new File("src/data/login_tracker.txt");
//        File fileName2 = new File("src/data/another.txt");
//
//        new IOOperations().copyTextWithBuffer(fileName, fileName2);
//
        IOOperations o = new IOOperations();
//
//        Cat cat = new IOOperations().new Cat();

        var cats = new ArrayList<Cat>();
        cats.add(o.new Cat("Josephine", 12, false));
        cats.add(o.new Cat("Franky", 3, true));
        cats.add(o.new Cat("Lucy", 23, false));
        File dataFile = new File("src/data/cat.data");

        o.saveToFile(cats, dataFile);
        var catsFromDisk = o.readFromFile(dataFile);
        System.out.print(catsFromDisk);
    }


    //**************************************************
    private class Cat implements Serializable {
        private static final long serialVersionUID = 2L;
        private transient String name;
        private transient int Age;
        private transient Boolean isMale;

        public Cat(String name, int Age, Boolean isMale){
            this.name = name;
            this.Age = Age;
            this.isMale = isMale;
        }

        @Override
        public String toString(){
            return name + " " + Age + " " + isMale;
        }
    }

    void saveToFile(List<Cat> cats, File dataFile) throws IOException {
        try (var out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(dataFile)))) {
            for (Cat cat : cats) {
                out.writeObject(cat);
            }
        }
    }

    List<Cat> readFromFile(File dataFile) throws IOException, ClassNotFoundException {
        var cats = new ArrayList<Cat>();
        try (var in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(dataFile)))) {
            while (true) {
                var object = in.readObject();
                if (object instanceof Cat)
                    cats.add((Cat) object);
            }
        } catch (EOFException e) {
            // File end reached
        }
        return cats;
    }

}