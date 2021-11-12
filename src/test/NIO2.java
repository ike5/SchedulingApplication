package test;

import java.io.*;
import java.io.Reader;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class NIO2 implements Serializable {
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


    //**************************************************
    private class Cat implements Serializable {
        private static final long serialVersionUID = 2L;
        private transient String name;
        private transient int Age;
        private transient Boolean isMale;

        public Cat(String name, int Age, Boolean isMale) {
            this.name = name;
            this.Age = Age;
            this.isMale = isMale;
        }

        @Override
        public String toString() {
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

    void subPathing() {
        Path path = Paths.get("/mammal/omnivore/raccoon/racoon_mother/raccoon.image");
        System.out.println("The Path Name is: " + path);
        for (int i = 0; i < path.getNameCount(); i++) {
            System.out.println("   Element " + i + " is: " + path.getName(i));
        }
        System.out.println();
        System.out.println("subPath(0,3): " + path.subpath(0, 3));
        System.out.println("sbpath(1,2): " + path.subpath(1, 6));
    }

    void pathInfoTest() {
        NIO2 o = new NIO2();
        o.printPathInformation(Path.of("zoo"));
        o.printPathInformation(Path.of("/zoo/armadillo/shells.txt"));
        o.printPathInformation(Path.of("./armadillo/../shells.txt"));
    }

    void toAbsolutePathDemo() {
        String curWorkingDirectory = System.getProperty("user.dir");
        System.out.println(curWorkingDirectory);

        var path = Paths.get("src/data/login_tracker.log");
        System.out.println("Absolute path: " + path.toAbsolutePath());
    }

    void resolvingPaths() {
        Path p1 = Path.of("/cats/../panther");
        Path p2 = Path.of("food");
        Path p3 = Path.of("/turkey/food");
        System.out.println(p1.resolve(p2));
        System.out.println(p3.resolve("/tiger/cage"));
    }

    void derivingPaths() {
        var p1 = Path.of("fish.txt");
        var p2 = Path.of("friendly/birds.txt");
        System.out.println(p1.relativize(p2));
        System.out.println(p2.relativize(p1));
    }

    void printPathInformation(Path path) {
        System.out.println("Filename is: " + path.getFileName());
        System.out.println("    Root is: " + path.getRoot());
        Path currentParent = path;
        while ((currentParent = currentParent.getParent()) != null) {
            System.out.println("    Current parent is: " + currentParent);
        }
    }

    void normalizingPaths() {
        var p1 = Path.of("./armadillo/../shells.txt");
        System.out.println(p1.normalize());

        var p2 = Path.of("/cats/../panther/food");
        System.out.println(p2.normalize());

        var p3 = Path.of("../../fish.txt");
        System.out.println(p3.normalize());
    }

    void accessCurrentWorkingDirectory() throws IOException {
        System.out.println(Paths.get(".").toRealPath());
    }

    void checkExistsFilesClass() {
        var b1 = Files.exists(Paths.get("/osterich/feathers.png"));
        System.out.println("Path " + (b1 ? "Exists" : "Missing"));

        var b2 = Files.exists(Paths.get("src/data"));
        System.out.println("Path " + (b2 ? "Exists" : "Missing"));
    }

    void bufferedReader() throws IOException {
        var path = Path.of("src/data/login_tracker.log");
        try (var reader = Files.newBufferedReader(path)) {
            String currentLine = null;
            while ((currentLine = reader.readLine()) != null) {
                System.out.println(currentLine);
            }
        }
    }

    void bufferedWriter() throws IOException {
        var list = new ArrayList<String>();
        list.add("Smokey");
        list.add("Yogi");

        var path = Path.of("src/data/login_tracker.log");
        try (var writer = Files.newBufferedWriter(path)) {
            for (var line : list) {
                writer.write(line);
                writer.newLine();
            }
        }
    }

    //Reading a File
    void readingAFile() throws IOException {
        var path = Path.of("src/data/login_tracker.log");
        final List<String> lines = Files.readAllLines(path);
        lines.forEach(System.out::println);
    }

    void tryCatchSyntax() {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("src/test/basic_try_demo.txt"));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (FileNotFoundException e) {
            System.err.format("File not found: %s%n", e);
        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void commonFileAttributes() {
        System.out.println(Files.isDirectory(Paths.get("/canine/fur.jpg")));
        System.out.println(Files.isSymbolicLink(Paths.get("src/data/login_tracker.log")));
        System.out.println(Files.isRegularFile(Paths.get("src/data/login_tracker.log")));

        System.out.println(Files.isReadable(Paths.get("src/data/login_tracker.log")));
        System.out.println(Files.isWritable(Paths.get("src/data/login_tracker.log")));
        System.out.println(Files.isExecutable(Paths.get("src/data/login_tracker.log")));

        try {
            System.out.println(Files.isHidden(Paths.get("src/data/login_tracker.log"))); // throws exception
        } catch (IOException e) {
            System.err.println("Doesn't work");
        } finally {
            System.err.println("Processed... ");
        }
    }

    void readingFileSize() {
        // Returns in bytes
        // Only  defined in files, not directories
        // Walk a directory to get the size of a directory
        try {
            System.out.println(Files.size(Paths.get("src/data/login_tracker.log")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void checkingForFileChanges() {
        // returns a FileTime object
        final Path path = Paths.get("src/data/login_tracker.log");

        try {
            System.out.println(Files.getLastModifiedTime(path));
            System.out.println(Files.getLastModifiedTime(path).toMillis());
        } catch (IOException e) {
            ///
        }
    }

    void retrievingAttributes() {
        //read only using readAttributes()
        var path = Paths.get("src/data/login_tracker.log");

        try {
            BasicFileAttributes data = Files.readAttributes(path, BasicFileAttributes.class);

            System.out.println("Is a directory? " + data.isDirectory());
            System.out.println("Is regular file? " + data.isRegularFile());
            System.out.println("Is symbolic link? " + data.isSymbolicLink());
            System.out.println("Size (in bytes): " + data.size());
            System.out.println("Last modified: " + data.lastModifiedTime());

        } catch (IOException e) {
            //
        }
    }

    void modifyingAttributes() {
        //updatable with getFileAttributeView()
        var path = Paths.get("src/data/login_tracker.log");

        BasicFileAttributeView view = Files.getFileAttributeView(path, BasicFileAttributeView.class);

        try {
            BasicFileAttributes attributes = view.readAttributes();
            System.out.println(attributes.lastModifiedTime()); // original time

            // add 10k milliseconds
            FileTime lastModifiedTime = FileTime.fromMillis(attributes.lastModifiedTime().toMillis() + 10_000);
            view.setTimes(lastModifiedTime, null, null);

            System.out.println(view.readAttributes().lastModifiedTime()); // modified time
        } catch (IOException e) {
            // reading attributes
        }

    }

    void listingDirectoryContents() {
        try (Stream<Path> dataPath = Files.list(Path.of("src/data"));
             Stream<Path> srcPath = Files.list(Path.of("src"));
             Stream<Path> allPath = Files.list(Path.of("/"))) {
            dataPath.forEach(System.out::println);
            srcPath.forEach(System.out::println);
            allPath.forEach(System.out::println);
        } catch (NoSuchFileException e) {
            System.err.println("No such file");
        } catch (IOException e) {
            System.err.println("Regular io exception");
        }
    }

    class CopyPath{
        //Makes a deep copy
        // will not follow symbolic links
        void copyPath(Path source, Path target){
            try{
                Files.copy(source, target);
                if(Files.isDirectory(source)){
                    try (Stream<Path> s = Files.list(source)){
                        s.forEach(p -> copyPath(p, target.resolve(p.getFileName())));
                    }
                }
            } catch (IOException e){
                // Handle exception
            }
        }

        CopyPath(){
            copyPath(Paths.get("src/data/login_tracker.log"), Path.of("src/test/login.log"));
        }
    }

    // helper method for method below
    long getSize(Path p ){
        try{
            return Files.size(p);
        }catch (IOException e){
            //
        }
        return 0L;
    }

    long getPathSize(Path source) throws IOException {
        try(var s = Files.walk(source)){
            return s.parallel()
                    .filter(p -> !Files.isDirectory(p))
                    .mapToLong(this::getSize) // uses helper method
                    .sum();
        }
    }

    void printData(){
        try {
            var size = getPathSize(Path.of("src"));
            System.out.format("Total size: %.2f megabytes", (size/1000000.0));
        } catch (IOException e){
            //
        }
    }


    public static void main(String[] args) throws IOException {
        new NIO2().printData();
    }


}
