### Signup to lucidchart.com and design a UML Class diagram with:
- Concrete classes that Map or Reflect the ERD (Entity Relationship Diagram) you've been provided with. 
- The UML Class Diagram should also have Utility / Helper Classes that use JDBC interfaces for:
  - Connecting to the Database
  - generating PreparedStatements and ResultSets. 
- Use Abstract classes with static methods for handling MySQL SELECT, INSERT, UPDATE, and DELETE operations. 
- Define Functional Interfaces for your Lambda expressions. 
  - You may use Inheritance (where applicable), and use the MVC (Model-View-Controller) Design Pattern. 
  - The Concrete classes, when Instantiated, serve as Objects that map the database table rows for use with Collections such as the ObservableArrayList and ArrayLists. 
---
- Contact your instructor for feedback on your UML diagram 
---
- Use lucidchart.com tools or any wireframing tool to sketch the menus for your application. Contact your instructor for feedback on your wireframe menu design
---
- Utilize the following Course Documentation: Common Fail Points for Software II, C195 Webinar Blast Recording Archive and Resource Map



# Java2PerformanceAssessment


Steps
1. Create a new Java Project (NOT JavaFX project)
2. Make sure the "PATH_TO_FX" is set
  1. File -> Settings: Path Variables
3. Add the lib directory for JavaFX
4. Make model/view/controller packages under src
5. Make Main.java
6. Add "VM Options
  1. --module-path ${PATH_TO_FX} --add-modules javafx.fxml,javafx.controls,javafx.graphics
  2. Note: no spaces after commas!
7. Make the first fxml
8. Make the controller implement `Initializable`
9. Do a test run
10. Add Button and Label
11. Add Button Handler and Label FXID
12. Write some more code!