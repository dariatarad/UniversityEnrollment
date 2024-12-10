package application;

//Daria Taradina	
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import javafx.util.Duration;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Glow;
import javafx.geometry.*;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.animation.*;


public class Homework extends Application {
	BorderPane borderPane = new BorderPane();
	GridPane gridPane = new GridPane();
	Tooltip tooltip = new Tooltip();
	MenuBar menuBar;
	Menu fileMenu;
	Menu studentsMenu;
	Menu coursesMenu;
	Menu enrollmentsMenu;
	Menu gradesMenu;
	Menu reportsMenu;	
	VBox mainPage;

    static StudentFileManager studentManager;
    static CourseFileManager courseManager;
    static EnrollmentFileManager enrollmentManager;
    EasterEgg easterEgg = new EasterEgg();
    
    public void file() throws IOException {
        studentManager = new StudentFileManager("students.txt");
        courseManager = new CourseFileManager("courses.txt");
        enrollmentManager = new EnrollmentFileManager("enrollment.txt");
    }
    
    private boolean isValidInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
    
    //CONFIRMATION
    private void showConfirmation(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
		
	@Override
	public void start(Stage primaryStage) {
		try {
			Label welcomeMessage = new Label("Welcome to University Enrollment!");
			Scene scene = new Scene(borderPane,671.5,400);
			
			Image applicationIcon = new Image(getClass().getResourceAsStream("icon.png"));
            primaryStage.getIcons().add(applicationIcon);
            
            Image animation = new Image(getClass().getResourceAsStream("mainP.gif"));
            ImageView imageView = new ImageView(animation);
            imageView.setFitHeight(120);
            imageView.setPreserveRatio(true);
            DropShadow dropShadow = new DropShadow();
            dropShadow.setRadius(5.0);
            dropShadow.setOffsetX(3.0);
            dropShadow.setOffsetY(3.0);
            dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
            imageView.setEffect(dropShadow);
            
            Label note = new Label("➤ find Easter egg 🔍  ");
            note.setStyle("-fx-text-fill: #0028ac; -fx-font-weight: bold; -fx-font-size: 12pt;");
            Glow glow = new Glow();
            note.setEffect(glow);
           			
			mainPage = new VBox(5);
			mainPage.setAlignment(Pos.CENTER);
						
			mainPage.getChildren().addAll(welcomeMessage, imageView, note);
			
			menuBar = new MenuBar();
			borderPane.setTop(menuBar);
			borderPane.setCenter(mainPage);	                 
            
			buildFileMenu(primaryStage);
			buildStudentsMenu();
			buildCoursesMenu();
			buildEnrollmentsMenu();
			buildReportsMenu();			
	            
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			welcomeMessage.getStyleClass().add("label-welcome");
			borderPane.getStyleClass().add("border-Pane");
			primaryStage.setScene(scene);
			primaryStage.setTitle("University Enrollment");
			primaryStage.show();			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void buildFileMenu(Stage primaryStage) {		
		fileMenu = new Menu("File");
		
		MenuItem mainP = new MenuItem("Main Page");
		fileMenu.getItems().add(mainP);
		mainP.setOnAction(e -> borderPane.setCenter(mainPage));
		
		MenuItem readmeItem = new MenuItem("README");
		fileMenu.getItems().add(readmeItem);
		readmeItem.setOnAction(e -> {
			try {
				readMe();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
		
		MenuItem exitItem = new MenuItem("Exit");
		fileMenu.getItems().add(exitItem);
		exitItem.setOnAction(e -> System.exit(0)); 
		
		menuBar.getMenus().add(fileMenu);	    
	}

	private void readMe() throws IOException {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		TextArea textArea = new TextArea();
		textArea.setPrefRowCount(20);
		textArea.setMaxWidth(430);
		textArea.setEditable(false);
		textArea.setStyle("-fx-font-size: 10pt;");
 		
		VBox vbox = new VBox(10);
		vbox.setStyle("-fx-padding: 10px;");
		vbox.setAlignment(Pos.CENTER);		
		
		BufferedReader br = new BufferedReader(new FileReader("README.txt"));
		String line;
        while ((line = br.readLine()) != null) {
        	textArea.appendText(line + "\n");
        }
        br.close();
        textArea.positionCaret(0);
        vbox.getChildren().addAll(textArea);
		borderPane.setCenter(vbox);
	}
	
	private void buildStudentsMenu() {
		studentsMenu = new Menu("Students");
		
		MenuItem addStudentItem = new MenuItem("Add Student");
		studentsMenu.getItems().add(addStudentItem);
		addStudentItem.setOnAction(e -> addStudentInformation());
		
		MenuItem viewStudentItem = new MenuItem("View Student");
		studentsMenu.getItems().add(viewStudentItem);
		viewStudentItem.setOnAction(e -> viewStudent());
		
		MenuItem editStudentItem = new MenuItem("Edit Student");
		studentsMenu.getItems().add(editStudentItem);
		editStudentItem.setOnAction(e -> editStudent());
		
		menuBar.getMenus().add(studentsMenu);		
	}
	
	private void addStudentInformation() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox addStudentView = new VBox(10);
		addStudentView.setStyle("-fx-padding: 10px;");
		addStudentView.setAlignment(Pos.CENTER);
		
		Label newStudentLabel = new Label("New Student Information");
		newStudentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	      
		Text studentIDLabel = new Text("Student ID ");
		TextField studentID = new TextField();
		
		Label errorLabel = new Label("*invalid ID");		
		errorLabel.setMaxWidth(60);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setStyle("-fx-font-size: 12px;");
        errorLabel.setVisible(false);
        
        Label idExistsLabel = new Label("*ID exists");
        idExistsLabel.setMaxWidth(60);
        idExistsLabel.setTextFill(Color.RED);
        idExistsLabel.setStyle("-fx-font-size: 12px;");
        idExistsLabel.setVisible(false);
		
		studentID.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidInteger(newValue)) {
                int studentId = Integer.parseInt(newValue);
                try {
					file();
				} catch (IOException e) {
					e.printStackTrace();
				}
                Student student = studentManager.getStudent(studentId);

                if (student == null) {
                	studentID.setStyle("-fx-border-color: none;");
                	errorLabel.setVisible(false);
                	idExistsLabel.setVisible(false);
                } else {
                	studentID.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                	idExistsLabel.setVisible(true);
                	errorLabel.setVisible(false);
                }
            } else {
            	studentID.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            	errorLabel.setVisible(true);
            	idExistsLabel.setVisible(false);
            }
        });
		 
		Text firstNameLabel = new Text("First Name");
		TextField firstName = new TextField();		
		
		Text lastNameLabel = new Text("Last Name");
		TextField lastName = new TextField();		
		
		Text addressLabel = new Text("Address");
		TextField addressField = new TextField();		
		
		Text cityLabel = new Text("City");
		TextField cityField = new TextField();		
		
		Text stateLabel = new Text("       State");
		ComboBox<String> stateBox = new ComboBox<>();
		stateBox.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CO", "DE", "FL", "GA", 
				 "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
				 "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", 
				 "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");		
				
		Text zipCodeLabel = new Text("Zip Code");
		TextField zipCode = new TextField();
				
		gridPane.add(studentIDLabel, 0, 0); 
	    gridPane.add(studentID, 1, 0);
	    gridPane.add(errorLabel, 2, 0);
	    gridPane.add(idExistsLabel, 2, 0);
	    
	    gridPane.add(firstNameLabel, 0, 1);       
	    gridPane.add(firstName, 1, 1); 
	    gridPane.add(lastNameLabel, 0, 2); 
	    gridPane.add(lastName, 1, 2);
	    gridPane.add(addressLabel, 0, 3); 
	    gridPane.add(addressField, 1, 3); 
	    gridPane.add(cityLabel, 0, 4);       
	    gridPane.add(cityField, 1, 4); 
	    gridPane.add(stateLabel, 2, 4); 
	    gridPane.add(stateBox, 3, 4);
	    gridPane.add(zipCodeLabel, 0, 5); 
	    gridPane.add(zipCode, 1, 5);
	    

	    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = studentID.getText();
	            String first = firstName.getText();
	            String last = lastName.getText();
	            String address = addressField.getText();
	            String city = cityField.getText();
	            String state = (String) stateBox.getValue();
	            String zip = zipCode.getText();

	            if (idText == null || first == null || last == null || address == null || 
					     city == null || state == null || zip == null) {           
	            	showAlert("Error", "Fields can't be empty");
	            	
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);
	                    file();	                    
	                    
	                   if (studentManager.getStudent(id) != null) {
	                	   showAlert("Error", "Student with ID " + id + " exists.");
	                    } else {
	                    	studentManager.addStudent(id, first, last, address, city, state, zip);
		                    showConfirmation("Success", "Student added successfully!");
		                    studentID.setText("");
		                    firstName.setText("");
		                    lastName.setText("");
		                    addressField.setText("");
		                    cityField.setText("");
		                    stateBox.setValue(null);
		                    zipCode.setText("");
		                    studentID.setStyle(null);
	                    }	                    
	                    
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Invalid ID, please enter a valid integer.");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    	
		Button createStudentBtn = new Button("Create Student");
		createStudentBtn.setOnAction(buttonHandler);
		
		addStudentView.getChildren().addAll(newStudentLabel, gridPane, createStudentBtn);
		borderPane.setCenter(addStudentView);
		
	}
		
	private void viewStudent() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox viewStudentItem = new VBox(10);
		viewStudentItem.setStyle("-fx-padding: 10px;");
		viewStudentItem.setAlignment(Pos.CENTER);
		
		Label viewStudentLabel = new Label("View Student Information");
		viewStudentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	      
		Text studentIDLabel = new Text("Student ID ");
		TextField studentID = new TextField();
		Button searchID = new Button("Search");
		 
		Text firstNameLabel = new Text("First Name");
		TextField firstName = new TextField();
		firstName.setEditable(false);
		
		Text lastNameLabel = new Text("Last Name");
		TextField lastName = new TextField();
		lastName.setEditable(false);
		
		Text addressLabel = new Text("Address");
		TextField address = new TextField();
		address.setEditable(false);
		
		Text cityLabel = new Text("City");
		TextField city = new TextField();
		city.setEditable(false);
		
		Text stateLabel = new Text("State");
		TextField state = new TextField();
		state.setEditable(false);
		
		Text zipCodeLabel = new Text("Zip Code");
		TextField zipCode = new TextField();
		zipCode.setEditable(false);
		
		Button resetBtn = new Button("Reset");
		
		gridPane.add(studentIDLabel, 0, 0); 
	    gridPane.add(studentID, 1, 0);
	    gridPane.add(searchID, 2, 0);
	    gridPane.add(firstNameLabel, 0, 1);       
	    gridPane.add(firstName, 1, 1); 
	    gridPane.add(lastNameLabel, 0, 2); 
	    gridPane.add(lastName, 1, 2);
	    gridPane.add(addressLabel, 0, 3); 
	    gridPane.add(address, 1, 3); 
	    gridPane.add(cityLabel, 0, 4);       
	    gridPane.add(city, 1, 4); 
	    gridPane.add(stateLabel, 0, 5);
	    gridPane.add(state, 1, 5);
	    gridPane.add(zipCodeLabel, 0, 6); 
	    gridPane.add(zipCode, 1, 6);
	    //VIEW STUDENT !!!!!!
	    EventHandler<ActionEvent> buttonHandlerView = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = studentID.getText();
	            if (idText == null) {
	                showAlert("Error", "ID field can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);
	                    file();
	                    
	                   Student student = studentManager.getStudent(id); 
	                   if (student != null) {
	                	   firstName.setText(student.first);
	                	   lastName.setText(student.last);
	                	   address.setText(student.address);
	                	   city.setText(student.city);
	                	   state.setText(student.state);
	                	   zipCode.setText(student.zip);	                        
	                    } else {
	                    	showAlert("Error", "Student with ID " + id + " doesn't exists.");
	                    	firstName.setText("");
	        				lastName.setText("");
	        				address.setText("");
	        				city.setText("");
	        				state.setText("");
	        				zipCode.setText("");
	                    }
	                    
	                } catch (NumberFormatException e) {
	                    showAlert("Error", "Student ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    
	    EventHandler<ActionEvent> buttonHandlerReset = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				studentID.setText("");
				firstName.setText("");
				lastName.setText("");
				address.setText("");
				city.setText("");
				state.setText("");
				zipCode.setText("");                
			}	    	
	    };
	    
	    searchID.setOnAction(buttonHandlerView);
	    resetBtn.setOnAction(buttonHandlerReset);
	    
	    viewStudentItem.getChildren().addAll(viewStudentLabel, gridPane, resetBtn);
	    borderPane.setCenter(viewStudentItem);
	}
	
	private void editStudent() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox editStudentItem = new VBox(10);
		editStudentItem.setStyle("-fx-padding: 10px;");
		editStudentItem.setAlignment(Pos.CENTER);
		
		Label editStudentLabel = new Label("Edit Student Information");
		editStudentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	      
		Text studentIDLabel = new Text("Student ID ");
		TextField studentID = new TextField();
		Button searchID = new Button("Search");
		 
		Text firstNameLabel = new Text("First Name");
		TextField firstName = new TextField();		
		
		Text lastNameLabel = new Text("Last Name");
		TextField lastName = new TextField();
		
		Text addressLabel = new Text("Address");
		TextField addressField = new TextField();
		
		Text cityLabel = new Text("City");
		TextField cityField = new TextField();
		
		Text stateLabel = new Text("State");
		ComboBox<String> stateBox = new ComboBox<>();
		stateBox.getItems().addAll("AL", "AK", "AZ", "AR", "CA", "CO", "CO", "DE", "FL", "GA", 
				 "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME", "MD", "MA", "MI", "MN", 
				 "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "OH", "OK", 
				 "OR", "PA", "RI", "SC", "SD", "TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY");
		
		Text zipCodeLabel = new Text("Zip Code");
		TextField zipCode = new TextField();
		
		Button resetStudent = new Button("Reset");
		Button updateStudent = new Button("Update");
		
		gridPane.add(studentIDLabel, 0, 0); 
	    gridPane.add(studentID, 1, 0);
	    gridPane.add(searchID, 2, 0);
	    gridPane.add(firstNameLabel, 0, 1);       
	    gridPane.add(firstName, 1, 1); 
	    gridPane.add(lastNameLabel, 0, 2); 
	    gridPane.add(lastName, 1, 2);
	    gridPane.add(addressLabel, 0, 3); 
	    gridPane.add(addressField, 1, 3); 
	    gridPane.add(cityLabel, 0, 4);       
	    gridPane.add(cityField, 1, 4); 
	    gridPane.add(stateLabel, 0, 5);
	    gridPane.add(stateBox, 1, 5);
	    gridPane.add(zipCodeLabel, 0, 6); 
	    gridPane.add(zipCode, 1, 6);
	    gridPane.add(updateStudent, 1, 7);
	    gridPane.add(resetStudent, 3, 7); 
	    
	    //EDIT STUDENT VIEW !!!
	    EventHandler<ActionEvent> buttonHandlerView = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = studentID.getText();

	            if (idText == null) {
	            	showAlert("Error", "ID field can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);

	                    file();
	                   Student student = studentManager.getStudent(id); 
	                   if (student != null) {
	                	   studentID.setEditable(false);
	                	   studentID.setStyle("-fx-opacity: 0.5;");
	                	   firstName.setText(student.first);
	                	   lastName.setText(student.last);
	                	   addressField.setText(student.address);
	                	   cityField.setText(student.city);
	                	   stateBox.setValue(student.state);
	                	   zipCode.setText(student.zip);	                        
	                    } else {
	                    	showAlert("Error", "Student with ID " + id + " doesn't exists.");
	                    }
	                    
	                } catch (NumberFormatException e) {
	                    showAlert("Error", "Student ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    //EDIT STUDENT UPDATE
	    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = studentID.getText();
	            String first = firstName.getText();
	            String last = lastName.getText();
	            String address = addressField.getText();
	            String city = cityField.getText();
	            String state = (String) stateBox.getValue();
	            String zip = zipCode.getText();

	            if (idText == null || first == null || last == null || address == null || 
					     city == null || state == null || zip == null) {
	            	showAlert("Error", "Fields can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);
	                    file();
	                    
	                   if (studentManager.getStudent(id) != null) {
	                	   
	                	   studentManager.editStudent(id, first, last, address, city, state, zip);	                	   
	                	  //update student information for all enrollments under this student id 
	                	   //when it's been updated in student menu
	                	   enrollmentManager.updateStudent(id, first, last);
	                	   showConfirmation("Success", "Student successfully updated!");
	                	   studentID.setEditable(true);
	                	   studentID.setStyle(null);
	                    }
	                    studentID.setText("");
	                    firstName.setText("");
	                    lastName.setText("");
	                    addressField.setText("");
	                    cityField.setText("");
	                    stateBox.setValue(null);
	                    zipCode.setText("");
	                    
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    
	    EventHandler<ActionEvent> buttonHandlerReset = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				studentID.setEditable(true);
				studentID.setStyle(null);
				studentID.setText("");
				firstName.setText("");
				lastName.setText("");
				addressField.setText("");
				cityField.setText("");
				stateBox.setValue(null);
				zipCode.setText("");                
			}	    	
	    };
	    
	    searchID.setOnAction(buttonHandlerView);
	    updateStudent.setOnAction(buttonHandler);
	    resetStudent.setOnAction(buttonHandlerReset);
	    		
	    editStudentItem.getChildren().addAll(editStudentLabel, gridPane);
	    borderPane.setCenter(editStudentItem);
	}
	
	private void buildCoursesMenu() {
		coursesMenu = new Menu("Courses");
		
		MenuItem addCourseItem = new MenuItem("Add course");
		coursesMenu.getItems().add(addCourseItem);
		addCourseItem.setOnAction(e -> addCourseInformation());
		
		MenuItem viewCourseItem = new MenuItem("View Course");
		coursesMenu.getItems().add(viewCourseItem);
		viewCourseItem.setOnAction(e -> viewCourse());
		
		MenuItem editCourseItem = new MenuItem("Edit Course");
		coursesMenu.getItems().add(editCourseItem);
		editCourseItem.setOnAction(e -> editCourse());
		
		menuBar.getMenus().add(coursesMenu);
	}
	
	private void addCourseInformation() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox addCourseView = new VBox(10);
		addCourseView.setStyle("-fx-padding: 10px;");
		addCourseView.setAlignment(Pos.CENTER);
		
		Label newCourseLabel = new Label("New Course Information");
		newCourseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	      
		Text courseIDLabel = new Text("Course ID ");
		TextField courseIDField = new TextField();
		
		Label errorLabel = new Label("*invalid ID");
		errorLabel.setMaxWidth(60);
        errorLabel.setTextFill(Color.RED);
        errorLabel.setStyle("-fx-font-size: 12px;");
        errorLabel.setVisible(false);
        
        Label idExistsLabel = new Label("*ID exists");
        idExistsLabel.setMaxWidth(60);
        idExistsLabel.setTextFill(Color.RED);
        idExistsLabel.setStyle("-fx-font-size: 12px;");
        idExistsLabel.setVisible(false);
		
        courseIDField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidInteger(newValue)) {
                int courseID = Integer.parseInt(newValue);
                try {
					file();
				} catch (IOException e) {
					e.printStackTrace();
				}
                Course course = courseManager.getCourse(courseID);

                if (course == null) {
                	courseIDField.setStyle("-fx-border-color: none;");
                	errorLabel.setVisible(false);
                	idExistsLabel.setVisible(false);
                } else {
                	courseIDField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                    idExistsLabel.setVisible(true);
                	errorLabel.setVisible(false);
                }
            } else {
            	courseIDField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            	errorLabel.setVisible(true);
            	idExistsLabel.setVisible(false);
            }
        });		
		 
		Text courseNameLabel = new Text("Course Name");
		TextField courseName = new TextField();
		
		Text departmentLabel = new Text("Department");
		ComboBox<String> departmentBox = new ComboBox<>();
		departmentBox.getItems().addAll("ART", "BUSINESS", "CO SI", "ENGLISH", "HISTORY", "MATH", "PHYSICS");
		
		Text courseNumberLabel = new Text("Course Number");
		TextField courseNumber = new TextField();
		
		Text instructorNameLabel = new Text("Instructor");
		TextField instructorName = new TextField();
		
		gridPane.add(courseIDLabel, 0, 0); 
	    gridPane.add(courseIDField, 1, 0);
	    gridPane.add(errorLabel, 2, 0); 
	    gridPane.add(idExistsLabel, 2, 0);
	    
	    gridPane.add(courseNameLabel, 0, 1);       
	    gridPane.add(courseName, 1, 1); 
	    gridPane.add(departmentLabel, 0, 2); 
	    gridPane.add(departmentBox, 1, 2);
	    gridPane.add(courseNumberLabel, 0, 3); 
	    gridPane.add(courseNumber, 1, 3); 
	    gridPane.add(instructorNameLabel, 0, 4);       
	    gridPane.add(instructorName, 1, 4); 
	    //ADD COURSE !!!!!
	    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = courseIDField.getText();
	            String name = courseName.getText();
	            String department = (String) departmentBox.getValue();
	            String number = courseNumber.getText();
	            String instructor = instructorName.getText();

	            if (idText == null || name == null || department == null || 
	    		 						number == null || instructor == null) {
	            	showAlert("Error", "Fields can't be empty");
	            } else {
	                try {
	                   int id = Integer.parseInt(idText);
	                   file();
	                   if (courseManager.getCourse(id) != null) {
	                	   showAlert("Error", "Course with ID " + id + " exists.");
	                    } else {
	                    
	                   courseManager.addCourse(id, name, department, number, instructor);
	                   showConfirmation("Success", "Course added successfully!");
	                   
	                   courseIDField.setText("");
	                   courseName.setText("");
	                   departmentBox.setValue(null);
	                   courseNumber.setText("");
	                   instructorName.setText("");
	                   courseIDField.setStyle(null);
	                    }  
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Invalid ID, please enter a valid integer.");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
		
		Button createCourse = new Button("Create Course");
		createCourse.setOnAction(buttonHandler);
		
		addCourseView.getChildren().addAll(newCourseLabel, gridPane, createCourse);
		borderPane.setCenter(addCourseView);
	}
	
	private void viewCourse() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox viewCourseItem = new VBox(10);
		viewCourseItem.setStyle("-fx-padding: 10px;");
		viewCourseItem.setAlignment(Pos.CENTER);
		
		Label viewCourseLabel = new Label("View Course Information");
		viewCourseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	      
	    Text courseIDLabel = new Text("Course ID ");
		TextField courseID = new TextField();
		Button searchID = new Button("Search");
		 
		Text courseNameLabel = new Text("Course Name");
		TextField courseName = new TextField();
		courseName.setEditable(false);
		
		Text departmentLabel = new Text("Department");
		TextField department = new TextField();
		department.setEditable(false);
		
		Text courseNumberLabel = new Text("Course Number");
		TextField courseNumber = new TextField();
		courseNumber.setEditable(false);
		
		Text instructorNameLabel = new Text("Instructor");
		TextField instructorName = new TextField();
		instructorName.setEditable(false);
		
		Button resetBtn = new Button("Reset");
		
		gridPane.add(courseIDLabel, 0, 0); 
	    gridPane.add(courseID, 1, 0);
	    gridPane.add(searchID, 2, 0);
	    gridPane.add(courseNameLabel, 0, 1);       
	    gridPane.add(courseName, 1, 1); 
	    gridPane.add(departmentLabel, 0, 2);
	    gridPane.add(department, 1, 2);
	    gridPane.add(courseNumberLabel, 0, 3); 
	    gridPane.add(courseNumber, 1, 3); 
	    gridPane.add(instructorNameLabel, 0, 4);       
	    gridPane.add(instructorName, 1, 4);
	    //VIEW COURSE !!!
	    EventHandler<ActionEvent> buttonHandlerView = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = courseID.getText();

	            if (idText == null) {
	            	showAlert("Error", "ID field can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);

	                    file();
	                   Course course = courseManager.getCourse(id); 
	                   if (course != null) {
	                	   courseName.setText(course.name);
	                	   department.setText(course.department);
	                	   courseNumber.setText(course.number);
	                	   instructorName.setText(course.instructor);
	                        
	                    } else {
	                    	showAlert("Error", "Course with ID " + id + " doesn't exists.");
	        				courseName.setText("");
	        				department.setText("");
	        				courseNumber.setText("");
	        				instructorName.setText(""); 
	                    }	                   
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Course ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    
	    EventHandler<ActionEvent> buttonHandlerReset = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				courseID.setText("");
				courseName.setText("");
				department.setText("");
				courseNumber.setText("");
				instructorName.setText("");            
			}	    	
	    };
	    
	    searchID.setOnAction(buttonHandlerView);
	    resetBtn.setOnAction(buttonHandlerReset);
		
	    viewCourseItem.getChildren().addAll(viewCourseLabel, gridPane, resetBtn);
	    borderPane.setCenter(viewCourseItem);
	}
	
	private void editCourse() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox editCourseItem = new VBox(10);
		editCourseItem.setStyle("-fx-padding: 10px;");
		editCourseItem.setAlignment(Pos.CENTER);
		
		Label editCourseLabel = new Label("Edit Course Information");
		editCourseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	      
	    Text courseIDLabel = new Text("Course ID ");
		TextField courseID = new TextField();
		Button searchID = new Button("Search");
		 
		Text courseNameLabel = new Text("Course Name");
		TextField courseName = new TextField();		
		
		Text departmentLabel = new Text("Department");
		ComboBox<String> departmentBox = new ComboBox<>();
		departmentBox.getItems().addAll("ART", "BUSINESS", "CO SI", "ENGLISH", "HISTORY", "MATH", "SIENCE");
		
		Text courseNumberLabel = new Text("Course Number");
		TextField courseNumber = new TextField();
		
		Text instructorNameLabel = new Text("Instructor");
		TextField instructorName = new TextField();
				
		Button updateCourse = new Button("Update");
		Button resetBtn = new Button("Reset");
		
		gridPane.add(courseIDLabel, 0, 0); 
	    gridPane.add(courseID, 1, 0);
	    gridPane.add(searchID, 2, 0);
	    gridPane.add(courseNameLabel, 0, 1);       
	    gridPane.add(courseName, 1, 1); 
	    gridPane.add(departmentLabel, 0, 2);
	    gridPane.add(departmentBox, 1, 2);
	    gridPane.add(courseNumberLabel, 0, 3); 
	    gridPane.add(courseNumber, 1, 3); 
	    gridPane.add(instructorNameLabel, 0, 4);       
	    gridPane.add(instructorName, 1, 4);
	    gridPane.add(updateCourse, 1, 7);
	    gridPane.add(resetBtn, 3, 7);
	    //COURSE EDIT VIEW !!!!
	    EventHandler<ActionEvent> buttonHandlerView = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = courseID.getText();

	            if (idText == null) {
	            	showAlert("Error", "ID field can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);

	                    file();
	                   Course course = courseManager.getCourse(id); 
	                   if (course != null) {
	                	   courseID.setEditable(false);
	                	   courseID.setStyle("-fx-opacity: 0.5;");
	                	   courseName.setText(course.name);
	                	   departmentBox.setValue(course.department);
	                	   courseNumber.setText(course.number);
	                	   instructorName.setText(course.instructor);
	                        
	                    } else {
	                    	showAlert("Error", "Course with ID " + id + " doesn't exists.");
	                    }
	                    
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Course ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    //COURSE UPDATE 
	    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = courseID.getText();
	            String name = courseName.getText();	            
	            String department = (String) departmentBox.getValue();
	            String number = courseNumber.getText();
	            String instructor = instructorName.getText();

	            if (idText == null || name == null || department == null || 
	    		 						number == null || instructor == null) {
	            	showAlert("Error", "Fields can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);
	                    file();
	                    
	                   if (courseManager.getCourse(id) != null) {
	                	   courseManager.editCourse(id, name, department, number, instructor);
	                	   enrollmentManager.updateCourse(id, department, number, name);
	                	   
	                	   showConfirmation("Success", "Course successfully updated!");
	                	   courseID.setEditable(true);
	                	   courseID.setStyle(null);
	                    }
	                   courseID.setText("");
	                   courseName.setText("");
	                   departmentBox.setValue(null);
	                   courseNumber.setText("");
	                   instructorName.setText("");	                   
	               
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };	    
	    
	    EventHandler<ActionEvent> buttonHandlerReset = new EventHandler<ActionEvent>() {

			public void handle(ActionEvent evt) {
				courseID.setEditable(true);
         	    courseID.setStyle(null);
				courseID.setText("");
				courseName.setText("");
				departmentBox.setValue(null);
				courseNumber.setText("");
				instructorName.setText("");            
			}	    	
	    };
	    
	    searchID.setOnAction(buttonHandlerView);
	    updateCourse.setOnAction(buttonHandler);
	    resetBtn.setOnAction(buttonHandlerReset);
		
	    editCourseItem.getChildren().addAll(editCourseLabel, gridPane);
	    borderPane.setCenter(editCourseItem);
	}
	
	private void buildEnrollmentsMenu() {
		enrollmentsMenu = new Menu("Enrollments");
		
		MenuItem addEnrollmentItem = new MenuItem("Add Enrollment");
		enrollmentsMenu.getItems().add(addEnrollmentItem);
		addEnrollmentItem.setOnAction(e -> addEnrollmentInformation());
		
		MenuItem viewOrEditEnrollmentItem = new MenuItem("View/Edit Enrollment");
		enrollmentsMenu.getItems().add(viewOrEditEnrollmentItem);
		viewOrEditEnrollmentItem.setOnAction(e -> viewOrEditEnrollment()); 
		
		menuBar.getMenus().add(enrollmentsMenu);
	}
	
	private void addEnrollmentInformation() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox addEnrollmentView = new VBox(10);
		addEnrollmentView.setStyle("-fx-padding: 10px;");
		addEnrollmentView.setAlignment(Pos.CENTER);
		
		Label newCourseLabel = new Label("New Enrollment Information");
		newCourseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(400, 200);
		gridPane.setPadding(new Insets(10, 10, 10, 10));
		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
	    
	    Text studentIDLabel = new Text("Student ID ");
	    TextField studentIDField = new TextField();
	    studentIDField.setPromptText("Student ID");
	    Button findStudent = new Button("Find Student");
	    
	    Text nameLabel = new Text("Student Name");
	    TextField nameField = new TextField();
	    nameField.setPromptText("Student Name");
	    
	    Text courseIDLabel = new Text("Course ID ");
	    TextField courseIDField = new TextField();
	    courseIDField.setPromptText("Course ID");
	    Button findCourse = new Button("Find Course");
	    
	    Text courseNumberLabel = new Text("Course Number");
	    TextField courseNumberField = new TextField();
	    courseNumberField.setPromptText("Course Number");
	    
	    Text courseNameLabel = new Text("Course Name");
	    TextField courseNameField = new TextField();
	    courseNameField.setPromptText("Course Name");
	    
	    Text semesterLabel = new Text("Semester: ");
	    ComboBox<String> semesterBox = new ComboBox<>();
	    semesterBox.getItems().addAll("Fall", "Winter", "Spring", "Summer");
	    
	    Text yearLabel = new Text("Year: ");
	    ComboBox<String> yearBox = new ComboBox<>();
	    yearBox.getItems().addAll("2026", "2025", "2024", "2023");
	    
	    Text gradeLabel = new Text("Grade: ");
	    ComboBox<String> gradeBox = new ComboBox<>();
	    gradeBox.getItems().addAll("NA", "A", "B", "C", "D", "F", "W");
	    
	    HBox hbox = new HBox(5);
	    hbox.setStyle("-fx-padding: 10px;");
	    hbox.setAlignment(Pos.CENTER);
	    hbox.getChildren().addAll(semesterLabel, semesterBox, yearLabel, yearBox, gradeLabel, gradeBox);
	    
	    Text enrollmentLabel = new Text("Enrollment ID");
	    TextField enrollmentIDField = new TextField();	    
	    enrollmentIDField.setPromptText("Enrollment ID");
	    
	    enrollmentIDField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (isValidInteger(newValue)) {
                int enrollmentId = Integer.parseInt(newValue);
                try {
					file();
				} catch (IOException e) {
					e.printStackTrace();
				}
                Enrollment enrollment = enrollmentManager.getEnrollment(enrollmentId);

                if (enrollment == null) {
                	enrollmentIDField.setStyle("-fx-border-color: none;");
                	enrollmentIDField.setTooltip(null); 
                } else {
                	enrollmentIDField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
                	tooltip.setText("Enrollment with this ID already exists!");
                    enrollmentIDField.setTooltip(tooltip);
                }
            } else {
            	enrollmentIDField.setStyle("-fx-border-color: red; -fx-border-width: 2px;");
            	tooltip.setText("Please enter a valid integer");
                enrollmentIDField.setTooltip(tooltip);
            }
        });
	    
	    Button createEnrollment = new Button("Create Enrollment");
	    
	    Button resetBtn = new Button("Reset");
	    
 	    nameField.setEditable(false);
 	    courseNumberField.setEditable(false);
	    courseNameField.setEditable(false);
	    
	    gridPane.add(studentIDLabel, 0, 0);
	    gridPane.add(studentIDField, 1, 0); 
	    gridPane.add(findStudent, 2, 0);
	    gridPane.add(nameLabel, 0, 1);
	    gridPane.add(nameField, 1, 1);       
	     
	    gridPane.add(courseIDLabel, 0, 2);
	    gridPane.add(courseIDField, 1, 2); 
	    gridPane.add(findCourse, 2, 2);
	    gridPane.add(courseNumberLabel, 0, 3);
	    gridPane.add(courseNumberField, 1, 3);
	    gridPane.add(courseNameLabel, 0, 4);
	    gridPane.add(courseNameField, 1, 4);
	    
	    gridPane.add(enrollmentLabel, 0, 8);
	    gridPane.add(enrollmentIDField, 1, 8);
	    gridPane.add(createEnrollment, 2, 8);
	    
	  //VIEW STUDENT !!!!!!
	    EventHandler<ActionEvent> buttonHandlerStudent = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = studentIDField.getText();

	            if (idText == null) {
	            	showAlert("Error", "Student ID field can't be empty");
	            } else {
	                try {
	                    int studentID = Integer.parseInt(idText);

	                    file();
	                   Student student = studentManager.getStudent(studentID); 
	                   if (student != null) {
	                	   nameField.setText(student.first + " " + student.last);
	                	   nameField.setEditable(false);
	                	   studentIDField.setEditable(false);
	                	   studentIDField.setStyle("-fx-opacity: 0.5;");
	                        
	                    } else {
	                    	showAlert("Error", "Student with ID " + studentID + " doesn't exists.");
	                    }
	                    
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Student ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
		
	  //VIEW COURSE !!!
	    EventHandler<ActionEvent> buttonHandlerCourse = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = courseIDField.getText();

	            if (idText == null) {
	            	showAlert("Error", "ID field can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);

	                    file();
	                   Course course = courseManager.getCourse(id); 
	                   if (course != null) {
	                	   courseNumberField.setText(course.department + " " + course.number);
	                	   courseNameField.setText(course.name);
	                	   courseNumberField.setEditable(false);
	                	   courseNameField.setEditable(false);
	                	   courseIDField.setEditable(false);
	                	   courseIDField.setStyle("-fx-opacity: 0.5;");
	                        
	                    } else {
	                    	showAlert("Error", "Course with ID " + id + " doesn't exists.");
	                    }	                    
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Course ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	  //ADD ENROLLMENT
	    EventHandler<ActionEvent> buttonHandlerEnrollment = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String stText = studentIDField.getText();
	            String name = nameField.getText();
	            String coText = courseIDField.getText();
	            String courseNum = courseNumberField.getText();
	            String courseName = courseNameField.getText();
	            String year = (String) yearBox.getValue();
	            String semester = (String) semesterBox.getValue();
	            String grade = (String) gradeBox.getValue();
	            String enText = enrollmentIDField.getText();	            

	     if (stText == null || name == null || coText == null || courseNum == null || courseName == null || 
	            		year == null || semester == null || grade == null || enText == null) {
	            	showAlert("Error", "Fields can't be empty");
	            } else {
	                try {
	                    int enrollmentID = Integer.parseInt(enText);
	                    int studentID = Integer.parseInt(stText);
	                    int courseID = Integer.parseInt(coText);

	                    file();
	                    
	                   if (enrollmentManager.getEnrollment(enrollmentID) != null) {
	                        showAlert("Error", "Enrollment with this ID already exists");
	                    } else {
	                    	enrollmentManager.addEnrollment(enrollmentID, studentID, name, courseID, courseNum, courseName, year, semester, grade);
	                    	showConfirmation("Success", "Enrollment added successfully!");
	                       
	                    	studentIDField.setStyle(null);
	                    	courseIDField.setStyle(null);
	                       studentIDField.setEditable(true);
	                       courseIDField.setEditable(true);
	                       studentIDField.setText("");
	 	                   nameField.setText("");
	 	                   courseIDField.setText("");
	 	                   courseNumberField.setText("");
	 	                   courseNameField.setText("");
	 	                   semesterBox.setValue(null);
	 	                   yearBox.setValue(null);
	 	                   gradeBox.setValue(null);
	 	                   enrollmentIDField.setText("");
	 	                   enrollmentIDField.setText("");
	 	                  enrollmentIDField.setStyle(null);
	                    }
	                    
	                } catch (NumberFormatException e) {
	                    showAlert("Error", "Enrollment ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    
	    EventHandler<ActionEvent> buttonHandlerReset = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				studentIDField.setStyle(null);
            	courseIDField.setStyle(null);
				studentIDField.setEditable(true);
                courseIDField.setEditable(true);
				studentIDField.setText("");
                nameField.setText("");
                courseIDField.setText("");
                courseNumberField.setText("");
                courseNameField.setText("");
                semesterBox.setValue(null);
                yearBox.setValue(null);
                enrollmentIDField.setText("");
                enrollmentIDField.setStyle(null);
			}	    	
	    };
	    
	    findStudent.setOnAction(buttonHandlerStudent);
	    findCourse.setOnAction(buttonHandlerCourse);
	    createEnrollment.setOnAction(buttonHandlerEnrollment);
	    resetBtn.setOnAction(buttonHandlerReset);	    
		
		addEnrollmentView.getChildren().addAll(newCourseLabel, gridPane, hbox, resetBtn);
		borderPane.setCenter(addEnrollmentView);
		addEnrollmentView.requestFocus();
	}
		
	
	private void viewOrEditEnrollment() {
		borderPane.setCenter(null);
		gridPane.getChildren().clear();
		
		VBox viewOrEditEnrollmentItem = new VBox(10);
		viewOrEditEnrollmentItem.setStyle("-fx-padding: 10px;");
		viewOrEditEnrollmentItem.setAlignment(Pos.CENTER);
		
		Label viewOrEditEnrollmentLabel = new Label("View/Edit Enrollment Information");
		viewOrEditEnrollmentLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 12pt;");
		
		gridPane.setMinSize(600, 50);
		gridPane.setPadding(new Insets(10, 10, 10, 10));		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.CENTER);
		
	    Text enrollmentIDLabel = new Text("Enrollment ID");
		TextField enrollmentIDField = new TextField();
		Button searchEnrollmentID = new Button("Search");
	    
	    Text studentIDLabel = new Text("Student ID");
		TextField studentID = new TextField();
		
		Text studentNameLabel = new Text("Student Name");
		TextField studentName = new TextField();
		
		Text courseIDLabel = new Text("Course ID");
		TextField courseID = new TextField();
		
		Text courseNumberLabel = new Text("Course Number");
		TextField courseNumber = new TextField();
		
		Text courseNameLabel = new Text("Course Name");
		TextField courseName = new TextField();
		
		Text semesterLabel = new Text("Semester");
		TextField semesterField = new TextField();
		semesterField.setMaxWidth(50);
		
		Text yearLabel = new Text("Year");
		TextField yearField = new TextField();
		yearField.setMaxWidth(50);
		
		Text gradeLabel = new Text("Grade");
		ComboBox<String> gradeBox = new ComboBox<>();
		gradeBox.getItems().addAll("A", "B", "C", "D", "F", "W");
		Button updateGrade = new Button("Update Grade");
		
		Button resetBtn = new Button("Reset");
		
		gridPane.add(enrollmentIDLabel, 0, 0); 
	    gridPane.add(enrollmentIDField, 1, 0);
	    gridPane.add(searchEnrollmentID, 2, 0);
	    gridPane.add(studentIDLabel, 0, 1);       
	    gridPane.add(studentID, 1, 1);
	    gridPane.add(studentNameLabel, 0, 2);
	    gridPane.add(studentName, 1, 2);
	    gridPane.add(courseIDLabel, 0, 3);
	    gridPane.add(courseID, 1, 3);
	    gridPane.add(courseNumberLabel, 0, 4);
	    gridPane.add(courseNumber, 1, 4);
	    gridPane.add(courseNameLabel, 0, 5);
	    gridPane.add(courseName, 1, 5);
	    
	    HBox hBox = new HBox(10);
	    hBox.setStyle("-fx-padding: 10px;");
	    hBox.setAlignment(Pos.CENTER);
	    hBox.getChildren().addAll(semesterLabel, semesterField, yearLabel, yearField);
	    
	    HBox gradeLine = new HBox(10);
	    gradeLine.setStyle("-fx-padding: 10px;");
	    gradeLine.setAlignment(Pos.CENTER);
	    gradeLine.getChildren().addAll(gradeLabel, gradeBox, updateGrade);
	    //SEARCh ENROLLMENT
	    EventHandler<ActionEvent> buttonHandlerView = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = enrollmentIDField.getText();

	            if (idText == null) {
	                showAlert("Error", "ID field can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);
	                 
	                    file();
	                    Enrollment enrollment = enrollmentManager.getEnrollment(id); 
	                   if (enrollment != null) {
	                	   String stID = String.valueOf(enrollment.studentID);
	                	   String coID = String.valueOf(enrollment.courseID);
	                	   
	                	   enrollmentIDField.setEditable(false);
	                	   enrollmentIDField.setStyle("-fx-opacity: 0.5;");
	                	   studentID.setText(stID);
	                	   studentID.setEditable(false);
	                	   studentName.setText(enrollment.name);
	                	   studentName.setEditable(false);
	                	   courseID.setText(coID);
	                	   courseID.setEditable(false);
	                	   courseNumber.setText(enrollment.courseNum);
	                	   courseNumber.setEditable(false);
	                	   courseName.setText(enrollment.courseName);
	                	   courseName.setEditable(false);
	                	   semesterField.setText(enrollment.semester);
	                	   semesterField.setEditable(false);
	                	   yearField.setText(enrollment.year);
	                	   yearField.setEditable(false);
	                	   gradeBox.setValue(enrollment.grade);
	                        
	                    } else {
	                        showAlert("Error", "Enrollment with this ID doesn't exists");
	                    }	                    
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Enrollment ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	   
	    
	    EventHandler<ActionEvent> buttonHandlerUpdate = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent arg0) {
				String grade = (String) gradeBox.getValue();
				if (grade == null) {
	                showAlert("Error", "Grade field can't be empty");
				}else {
					String enText = enrollmentIDField.getText();
					int enID = Integer.parseInt(enText);
					
					try {
						enrollmentManager.editEnrollment(enID, grade);
						showConfirmation("Success", "Enrollment updated successfully!");
						
						enrollmentIDField.setEditable(true);
						enrollmentIDField.setStyle(null);
						enrollmentIDField.setText("");
						studentID.setText("");
						studentName.setText("");
		                courseID.setText("");
		                courseNumber.setText("");
		                courseName.setText("");
		                semesterField.setText("");
		                yearField.setText("");
		                gradeBox.setValue(null);
					} catch (IOException e) {
						e.printStackTrace();
					}					
				}				
			}	    	
	    };
	    
	    EventHandler<ActionEvent> buttonHandlerReset = new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {
				enrollmentIDField.setEditable(true);
				enrollmentIDField.setStyle(null);
				enrollmentIDField.setText("");
				studentID.setText("");
				studentName.setText("");
                courseID.setText("");
                courseNumber.setText("");
                courseName.setText("");
                semesterField.setText("");
                yearField.setText("");
                gradeBox.setValue(null);
                
			}	    	
	    };
	    		
	    searchEnrollmentID.setOnAction(buttonHandlerView);
	    updateGrade.setOnAction(buttonHandlerUpdate);
	    resetBtn.setOnAction(buttonHandlerReset);
	    	
	    viewOrEditEnrollmentItem.getChildren().addAll(viewOrEditEnrollmentLabel, gridPane, hBox, gradeLine, resetBtn);
	    borderPane.setCenter(viewOrEditEnrollmentItem);
	}
	
 	 	
 	private void buildReportsMenu() {
		reportsMenu = new Menu("Reports");
		
		MenuItem generateReportItem = new MenuItem("Generate Report");
		reportsMenu.getItems().add(generateReportItem);
		generateReportItem.setOnAction(e -> generateReport());
		
		menuBar.getMenus().add(reportsMenu);
	}
 	
 	
 	private void generateReport() {
 		borderPane.setCenter(null);
		gridPane.getChildren().clear();
 		
		VBox generateReportItem = new VBox(10);
		generateReportItem.setStyle("-fx-padding: 10px;");
		generateReportItem.setAlignment(Pos.TOP_LEFT);
		
		gridPane.setMinSize(600, 50);
		gridPane.setPadding(new Insets(10, 10, 10, 10));		
		gridPane.setVgap(5); 
	    gridPane.setHgap(5);
	    gridPane.setAlignment(Pos.TOP_LEFT);
	    
	    Text courseIDLabel = new Text("Course ID ");
	    TextField courseID = new TextField();
	    
	    Text semesterLabel = new Text("Semester");
	    ComboBox<String> semesterBox = new ComboBox<>();
	    semesterBox.getItems().addAll("Fall", "Winter", "Spring", "Summer");
	    
	    Text yearLabel = new Text("Year");
	    ComboBox<String> yearBox = new ComboBox<>();
	    yearBox.getItems().addAll("2026", "2025", "2024", "2023");
	    
	    Button generateButton = new Button("Generate Report");
	    Button clearAreaBtn = new Button("Clear");
	    clearAreaBtn.setMinWidth(100);	    
	    
	    TextArea reportArea = new TextArea();
	    reportArea.setPrefRowCount(15);
	    reportArea.setMaxWidth(500);
	    reportArea.setWrapText(true);
	    
	    Image image = new Image(getClass().getResourceAsStream("sleepy.png"));
	    ImageView imageView = new ImageView(image);
	    imageView.setFitHeight(50);
        imageView.setPreserveRatio(true);
        
	    Button surpriseBtn = new Button("Click me! ");
	    surpriseBtn.setMinWidth(50);
	    surpriseBtn.setGraphic(imageView);
	    surpriseBtn.setFocusTraversable(true);
	    surpriseBtn.getStyleClass().add("btnClick"); 
	    surpriseBtn.setOnAction(e -> surprise());
	    
	    HBox hbox = new HBox(280);
	    hbox.getChildren().addAll(surpriseBtn, clearAreaBtn);
	    
	    gridPane.add(courseIDLabel, 0, 0); 
	    gridPane.add(semesterLabel, 1, 0);
	    gridPane.add(yearLabel, 2, 0);
	    gridPane.add(courseID, 0, 1); 
	    gridPane.add(semesterBox, 1, 1);
	    gridPane.add(yearBox, 2, 1);
	    gridPane.add(generateButton, 3, 1);
	    
	    EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
	        public void handle(ActionEvent evt) {
	            String idText = courseID.getText();
	            String semester = (String) semesterBox.getValue();
	            String year = (String) yearBox.getValue();
	            
	            if (idText == null || idText.isEmpty() || semester == null || year == null) {
	            	showAlert("Error", "Fields can't be empty");
	            } else {
	                try {
	                    int id = Integer.parseInt(idText);
	                 
	                    file();
	                    Course course = courseManager.getCourse(id);
	                    
	                   ArrayList<String> students = enrollmentManager.getStudents(id, semester, year); 
	                   if (course == null) {
	                	   showAlert("Error", "Course with ID " + id + " doesn't exists");                	   
	                	   
	                   }else if (students.isEmpty()){
	                	   showAlert("Error", "No enrollments found for this course");
	                	   
	                    } else {
	                    	reportArea.appendText("---------------------------------------------------------------------" + "\n");
	                    	reportArea.appendText(course.department + " " + course.number + " " + course.name + " Report" + "\n");
	                    	reportArea.appendText("---------------------------------------------------------------------" + "\n");
	                    	
	                    	for (String student : students) {
	                    	    reportArea.appendText(student + "\n");	                    	    
	                    	    reportArea.setEditable(false);
	                    	}
	                    }	                   
	                } catch (NumberFormatException e) {
	                	showAlert("Error", "Course ID must be a valid integer");
	                } catch (IOException e) {
						e.printStackTrace();
					}
	            }
	        }
	    };
	    
	    EventHandler<ActionEvent> buttonHandlerClear = new EventHandler<ActionEvent>() {
			
			public void handle(ActionEvent evt) {
				courseID.setText("");
				semesterBox.setValue(null);
				yearBox.setValue(null);				
				reportArea.setText("");
			}	    	
	    };
	    
	    generateButton.setOnAction(buttonHandler);
	    clearAreaBtn.setOnAction(buttonHandlerClear);
	    
	    generateReportItem.getChildren().addAll(gridPane, reportArea, hbox);
		borderPane.setCenter(generateReportItem);
 	}
 	
 	 	
	public static void main(String[] args) throws IOException {
		launch(args);
		
	}
	
	// Student classes 	
	private class Student {
	    int id;
	    String first;
	    String last;
	    String address;
	    String city;
	    String state;
	    String zip;

	    public Student(int id, String first, String last, String address, String city, String state, String zip) {
	        this.id = id;
	        this.first = first;
	        this.last = last;
	        this.address = address;
	        this.city = city;
	        this.state = state;
	        this.zip = zip;
	    }

	    public String toFileFormat() {
	        return id + "," + first + "," + last + "," + address + "," + city + "," + state + "," + zip;
	    }
	}
	
	private class StudentFileManager {
	    private ArrayList<Student> studentList = new ArrayList<>();
	    private String filename;

	    public StudentFileManager(String filename) throws IOException {
	        this.filename = filename;
	        File file = new File(filename);
	        if (file.exists()) {
	        	loadFromFile();
	        } else {
	        	file.createNewFile();
	        }
	    }
	    

	    private void loadFromFile() throws IOException {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] fields = line.split(",");
	            int id = Integer.parseInt(fields[0]); 
	            String first = fields[1];
	            String last = fields[2];
	            String address = fields[3];
	            String city = fields[4];
	            String state = fields[5];
	            String zip = fields[6];
	            studentList.add(new Student(id, first, last, address, city, state, zip));
	        }
	        br.close();
	    }

	    private void saveToFile() throws IOException {
	        PrintWriter pw = new PrintWriter(new FileWriter(filename));
	        for (Student student : studentList) {
	            pw.println(student.toFileFormat());
	        }
	        pw.close();
	    }

	    public boolean addStudent(int id, String first, String last, String address, String city, String state, String zip) throws IOException {
	        if (getStudent(id) != null) {
	            return false;
	        }
	        studentList.add(new Student(id, first, last, address, city, state, zip));
	        saveToFile();
	        return true;
	    }

	    public Student getStudent(int id) {
	        for (Student student : studentList) {
	            if (student.id == id) {
	                return student;
	            }
	        }
	        return null;
	    }

	    public void editStudent(int id, String first, String last, String address, String city, String state, String zip) throws IOException {
	        Student student = getStudent(id);
	        if (student != null) {
	            student.first = first;
	            student.last = last;
	            student.address = address;
	            student.city = city;
	            student.state = state;
	            student.zip = zip;
	            saveToFile();
	        }
	    }
	}

	// Course classes
	private class Course {
	    int id;
	    String name;
	    String department;
	    String number;
	    String instructor;

	    public Course(int id, String name, String department, String number, String instructor) {
	        this.id = id;
	        this.name = name;
	        this.department = department;
	        this.number = number;
	        this.instructor = instructor;
	    }

	    public String toFileFormat() {
	        return id + "," + name + "," + department + "," + number + "," + instructor;
	    }
	}
	
	private class CourseFileManager {
	    private ArrayList<Course> courseList = new ArrayList<>();
	    private String filename;

	    public CourseFileManager(String filename) throws IOException {
	        this.filename = filename;
	        File file = new File(filename);
	        if (file.exists()) {
	        	loadFromFile();
	        } else {
	        	file.createNewFile();
	        }
	    }

	    private void loadFromFile() throws IOException {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] fields = line.split(",");
	            int id = Integer.parseInt(fields[0]);
	            String name = fields[1];
	            String department = fields[2];
	            String number = fields[3];
	            String instructor = fields[4];
	            courseList.add(new Course(id, name, department, number, instructor));
	        }
	        br.close();
	    }

	    private void saveToFile() throws IOException {
	        PrintWriter pw = new PrintWriter(new FileWriter(filename));
	        for (Course course : courseList) {
	            pw.println(course.toFileFormat());
	        }
	        pw.close();
	    }

	    public boolean addCourse(int id, String name, String department, String number, String instructor) throws IOException {
	        if (getCourse(id) != null) {
	            return false;
	        }
	        courseList.add(new Course(id, name, department, number, instructor));
	        saveToFile();
	        return true;
	    }

	    public Course getCourse(int id) {
	        for (Course course : courseList) {
	            if (course.id == id) {
	                return course;
	            }
	        }
	        return null;
	    }

	    public void editCourse(int id, String name, String department, String number, String instructor) throws IOException {
	        Course course = getCourse(id);
	        if (course != null) {
	            course.name = name;
	            course.department = department;
	            course.number = number;
	            course.instructor = instructor;
	            saveToFile();
	        }
	    }
	}
	
	// Enrollment classes
	private class Enrollment {
	    int enrollmentID;
	    int studentID;
	    String name;
	    int courseID;
	    String courseNum;
	    String courseName;
	    String semester;
	    String year;
	    String grade;

	    public Enrollment(int enrollmentID, int studentID, String name, int courseID, String courseNum, String courseName, String year, String semester, String grade) {
	        this.enrollmentID = enrollmentID;
	        this.studentID = studentID;
	        this.name = name;
	        this.courseID = courseID;
	        this.courseNum =courseNum;
	        this.courseName =courseName;
	        this.year = year;
	        this.semester = semester;
	        this.grade = grade;
	    }

	    public String toFileFormat() {
	        return enrollmentID + "," + studentID + "," + name + "," + courseID + "," + courseNum + "," + courseName + "," + year + "," + semester + "," + grade;
	    }
	}
	
	class EnrollmentFileManager {
	    private ArrayList<Enrollment> enrollmentList = new ArrayList<>();
	    private String filename;

	    public EnrollmentFileManager(String filename) throws IOException {
	        this.filename = filename;
	        File file = new File(filename);
	        if (file.exists()) {
	        	loadFromFile();
	        } else {
	        	file.createNewFile();
	        }
	    }

	    private void loadFromFile() throws IOException {
	        BufferedReader br = new BufferedReader(new FileReader(filename));
	        String line;
	        while ((line = br.readLine()) != null) {
	            String[] fields = line.split(",");
	            int enrollmentID = Integer.parseInt(fields[0]);
	            int studentID = Integer.parseInt(fields[1]);
	            String name = fields[2];            
	            int courseID = Integer.parseInt(fields[3]);
	            String courseNum = fields[4];
	            String courseName = fields[5];
	            String year = fields[6];
	            String semester = fields[7];
	            String grade = fields[8];
	            enrollmentList.add(new Enrollment(enrollmentID, studentID, name, courseID, courseNum, courseName, year, semester, grade));
	        }
	        br.close();
	    }

	    private void saveToFile() throws IOException {
	        PrintWriter pw = new PrintWriter(new FileWriter(filename));
	        for (Enrollment enrollment : enrollmentList) {
	            pw.println(enrollment.toFileFormat());
	        }
	        pw.close();
	    }

	    public boolean addEnrollment(int enrollmentID, int studentID, String name, int courseID,  String courseNum, String courseName, String year, String semester, String grade) throws IOException {
	        if (getEnrollment(enrollmentID) != null) {
	            return false;
	        }
	        enrollmentList.add(new Enrollment(enrollmentID, studentID, name, courseID, courseNum, courseName, year, semester, grade));
	        saveToFile();
	        return true;
	    }

	    public Enrollment getEnrollment(int enrollmentID) {
	        for (Enrollment enrollment : enrollmentList) {
	            if (enrollment.enrollmentID == enrollmentID) {
	                return enrollment;
	            }
	        }
	        return null;
	    }
	    

	    public void editEnrollment(int enrollmentID, String grade) throws IOException {
	        Enrollment enrollment = getEnrollment(enrollmentID);
	        if (enrollment != null) {
	            enrollment.grade = grade;
	            saveToFile();
	        }
	    }
	    
	    public ArrayList<String> getStudents(int courseID, String semester, String year) {
	        ArrayList<String> studentsInCourse = new ArrayList<>();
	        for (Enrollment enrollment : enrollmentList) {
	            if (enrollment.courseID == courseID && enrollment.semester.equals(semester) && enrollment.year.equals(year)) {
	                studentsInCourse.add(enrollment.name + ":   " + enrollment.grade);
	            }            
	        }
	        return studentsInCourse;
	    }
	    
	    //update student information for all enrollments under this student id when it's been updated in student menu
	    public void updateStudent(int studentID, String first, String last) throws IOException {
	    	StudentFileManager studentManager = new StudentFileManager("students.txt");
	    	Student student = studentManager.getStudent(studentID); 

	        if (student != null) {
	            if (first != null && !first.isEmpty()) student.first = first;
	            if (last != null && !last.isEmpty()) student.last = last;

	            for (Enrollment enrollment : enrollmentList) {
	                if (enrollment.studentID == studentID) {
	                    enrollment.name = first + " " + last;
	                }
	            }
	            saveToFile();
	        }
	    }
	    
	    public void updateCourse(int courseID, String department, String number, String name) throws IOException {
	    	CourseFileManager courseManager = new CourseFileManager("courses.txt");
	    	Course course = courseManager.getCourse(courseID); 

	        if (course != null) {
	            if (department != null && !department.isEmpty()) course.department = department;  //course.department
	            if (number != null && !number.isEmpty()) course.number = number;
	            if (name != null && !name.isEmpty()) course.name = name;

	            for (Enrollment enrollment : enrollmentList) {
	                if (enrollment.courseID == courseID) {
	                    enrollment.courseNum = department + " " + number;
	                    enrollment.courseName = name;
	                }
	            }
	            saveToFile();
	        }
	    }
	    
	}
	
	
	private void surprise() {
	    borderPane.setCenter(null);

	    StackPane stackPane = new StackPane();
	    stackPane.getStyleClass().add("pane");
	    
	    Pane partyPane = new Pane();	    
	    easterEgg.startParty(partyPane, 600, 400);	    
	    stackPane.getChildren().add(partyPane);
	    
	    VBox vbox = new VBox(10);
	    vbox.setAlignment(Pos.CENTER);
	    
	    Text text = new Text("You found Lulu!");
	    text.getStyleClass().add("Lulu-label"); 
	    Glow glow = new Glow();
	    text.setEffect(glow);
	    
	    Button mainPageBtn = new Button("Main Page");
	    mainPageBtn.getStyleClass().add("btn");
	    mainPageBtn.setOnAction(e -> {
	    	easterEgg.stopParty();
	    	borderPane.setCenter(mainPage);
	    	});
	    mainPageBtn.setFocusTraversable(false);
	    
	    vbox.getChildren().addAll(text, mainPageBtn);
	    stackPane.getChildren().add(vbox);
	    borderPane.setCenter(stackPane);	    
	}
	
	
	public class EasterEgg {
		
	    private Image image1 = new Image(getClass().getResourceAsStream("loaf.png"));
	    private Image image2 = new Image(getClass().getResourceAsStream("bunny.png"));
	    private Image image3 = new Image(getClass().getResourceAsStream("breakdance.png"));
	    private Image image4 = new Image(getClass().getResourceAsStream("cloud.png"));
	    private Image image5 = new Image(getClass().getResourceAsStream("stretch.png"));
	    private Image image6 = new Image(getClass().getResourceAsStream("roar.png"));
	    
	    public void getImage(ImageView imageView) {
	        Random random = new Random();
	        int n = random.nextInt(6);

	        switch (n) {
	            case 0:
	                imageView.setImage(image1);
	                break;
	            case 1:
	                imageView.setImage(image2);
	                break;
	            case 2:
	                imageView.setImage(image3);
	                break;
	            case 3:
	                imageView.setImage(image4);
	                break;
	            case 4:
	                imageView.setImage(image5);
	                break;
	            case 5:
	                imageView.setImage(image6);
	                break;
	        }
	    }
	    private Timeline generator; 
	    
	    public void startParty(Pane pane, int width, int height) {
	        Random random = new Random();

	        generator = new Timeline(
	                new KeyFrame(Duration.seconds(0.5), e -> {
	                    ImageView imageView = new ImageView();
	                    getImage(imageView);

	                    imageView.setFitWidth(random.nextInt(200));
	                    imageView.setPreserveRatio(true);
	                    imageView.setX(random.nextInt(width));
	                    imageView.setY(-200);

	                    pane.getChildren().add(imageView);

	                    Timeline fallingTimeline = new Timeline(
	                            new KeyFrame(Duration.ZERO,
	                                    new KeyValue(imageView.yProperty(), -200),
	                                    new KeyValue(imageView.rotateProperty(), 0)
	                            ),
	                            new KeyFrame(Duration.seconds(5),
	                                    new KeyValue(imageView.yProperty(), height + 10),
	                                    new KeyValue(imageView.rotateProperty(), 360)
	                            )
	                    );

	                    fallingTimeline.setCycleCount(1);
	                    fallingTimeline.setDelay(Duration.millis(70));
	                    fallingTimeline.setOnFinished(e1 -> pane.getChildren().remove(imageView));
	                    fallingTimeline.play();
	                })
	        );

	        generator.setCycleCount(Timeline.INDEFINITE);
	        generator.setRate(2);
	        generator.play();
	    }
	    
	    public void stopParty() {  
	        if (generator != null) {  
	        	generator.stop();
	        }  
	     } 
	}	
	
}
