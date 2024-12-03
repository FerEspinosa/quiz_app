Quiz App


Part 1
Creating the database:

Download and Install XAMPP:
Go to the Apache Friends website.
Download the XAMPP installer for your operating system (Windows, macOS, or Linux).
Run the installer and follow the installation steps.
Start Apache and MySQL Servers:
Open the XAMPP Control Panel.
Click Start for both Apache and MySQL.
Access phpMyAdmin:
Open a web browser (e.g., Chrome).
Type 127.0.0.1 or localhost in the address bar and press Enter.
On the XAMPP dashboard, click on phpMyAdmin.
Create a New Database:
In phpMyAdmin, click on the Databases tab.
Enter a name for your new database (e.g., my_quiz_db).
Click Create.
Create a New Table:
In your newly created database, enter a name for the table (e.g., math_table).
Specify the number of columns (e.g., 7 columns).
Click Go or Create.
Define Table Columns:
Define the following columns and their data types:
Column Name
Data Type
Additional Settings
id
INT
Set as Primary Key and Auto Increment
question
VARCHAR
Length: 200
option1
VARCHAR
Length: 200
option2
VARCHAR
Length: 200
option3
VARCHAR
Length: 200
option4
VARCHAR
Length: 200
correct_option
VARCHAR
Length: 200


Save the Table:
Click Save to create the table with the specified columns.




1. Insert a Record into the Table:
   Open phpMyAdmin in your browser (127.0.0.1/phpmyadmin).
   Go to your database (my_quiz_db).
   Select the table (math_table).
   Click on Insert to add a new record.
   Enter the following values:
   ID: 1
   Question: "What is the result of 5 times 6?"
   Option1: "30"
   Option2: "35"
   Option3: "25"
   Option4: "40"
   Correct Option: "30"
   Click Go to save the record.
   Click on “Browse” tab to verify the record has been added successfully.
2. Create a PHP API File:
   Open a text editor (e.g., Notepad).
   Create a new file and name it my_quiz_api.php.
   Save the file in the htdocs folder of your XAMPP installation (e.g., C:\xampp\htdocs\my_quiz_api.php).
3. Write PHP Code for the API:
   Open my_quiz_api.php and add the following PHP code:
<?php
// Establish a connection to the MySQL database
$con = mysqli_connect("localhost", "root", "", "my_quiz_db");
/* here are explained the parameters from the line above:
“localhost” = the hostname of the database server (where the database is stored)
“root” = the username used to connect to the database server
“empty string” = Here it would go the server connection password (now it is empty because we are using a database stored in a local machine with no password, but this is not safe to implement in production environment)
"my_quiz_db" = Name of the database
*/
// Check if the connection was successful
if (!$con) {
    die("Connection failed: " . mysqli_connect_error());
}
// Prepare the SQL statement to fetch data
$stmt = $con->prepare("SELECT ’question’, 
‘option1’, ‘option2’, ‘option3’, ‘option4’, ‘correct_option’ 
FROM ‘math_table’ ");

// Execute the prepared query
$stmt->execute();

// Binding the results to the query
$stmt->bind_result($question,$option1,$option2,$option3,$option4,correct_option);

// Creating an empty Array
$questions_array = array();

// Traversing through all the questions
while($stmt->fetch()){
$temp = array();
	$temp[‘question’]=$question;
	$temp[‘option1’]=$option1;
	$temp[‘option2’]=$option2;
	$temp[‘option3’]=$option3;
	$temp[‘option4’]=$option4;
	$temp[‘correct_option’]=$correct_option;
}

array_push ($questions_array, $temp);

// Displaying the results in JSON format
echo json_encode($questions_array);
?>

4. Test the API:
   Start Apache and MySQL servers from the XAMPP Control Panel.
   Open a browser and type: http://localhost/my_quiz_api.php.
   If everything is set up correctly, you should see a JSON response similar to this:
   [
   {
   "id": "1",
   "question": "What is the result of 5 times 6?",
   "option1": "30",
   "option2": "35",
   "option3": "25",
   "option4": "40",
   "correct_option": "30"
   }
   ]
   Explanation of the Code:
   mysqli_connect(): Establishes a connection to the MySQL database.
   $con->prepare(): Prepares an SQL statement for execution.
   $stmt->execute(): Executes the prepared SQL statement.
   $result->fetch_assoc(): Fetches the result row as an associative array.
   json_encode(): Converts the PHP array to a JSON format.

xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

THE APP
1- start a new project


2- add dependencies in gradle file:
go to: developer.android.com/jetpack/androidx/releases/livecycle
copy the ViewModel and LiveData dependencies
go to: square.github.io/retrofit
click on download on the right panel of the and copy gradle implementation code
check out the latest version number from the download section (in the readme file)
complete the implementation code with the corresponding version number
in the same page, find the gson converter implementation code. copy it and put it in gradle
(use same version number as retrofit)
sync the project




3- create a model package


4- inside the model package create a model java class (example: question.java)


5- define every variable the model class should have. If you are working with API responses, copy an example of a response and "prettify" it using jsonschema2pojo.org.


6- Complete empty fields in jsonschema2pojo.org (put your package path and class name) and select Json (source type), Gson (Annotation style) and keep the rest of settings as default


7- click preview, copy the code and paste it in the model class created in step 3


8- If the API response is an array of json objects, then we should create a response model class that will contain an array of java objects, in order to let retrofit convert the response into a declared class. This new response class (i.e: Response.java or QuestionList.java) will extend from arrayList<Question>
("Question" is the name of the example model class created in previous steps)
This class have no key-value pair as it just an array of objects. It is empty.


9- Create a new package called retrofit
10- inside retrofit package create the API interface (i.e.: QuestionsAPI)


public interface QuestionsAPI {

// Define the endpoint for fetching questions
@GET("my_quiz_api.php") // end point
Call<QuestionList> getQuestions();

}




11- inside retrofit package, create a class RetrofitInstance.java

// when refering to our local system from an emulator we shouldn't use 127.0.0.1 address.
// Instead we use 10.0.2.2

private static final String BASE_URL = "http://10.0.2.2/quiz/";
private static Retrofit retrofit = null;

// Method to create and return the Retrofit instance
public static Retrofit getRetrofitInstance() {
if (retrofit == null) {
retrofit = new Retrofit.Builder()
.baseUrl(BASE_URL)
.addConverterFactory(GsonConverterFactory.create())
.build();
}
return retrofit;
}
12- create new package called repository


13- inside repository package, create a class QuizRepository.java
14- inside QuizRepository:


private QuizApiService questionsAPI;

public QuizRepository() {
// Create an instance of QuestionsAPI using Retrofit
questionsAPI = RetrofitClient.getRetrofitInstance().create(QuizApiService.class);
}


public LiveData<QuestionList> getQuestionsFromAPI(){
MutableLiveData<QuestionList> data = new MutableLiveData<>();

Call<QuestionList> response = questionsAPI.getQuestions();

// in the following line the enqueue method is executed on a background thread
// (if we used .execute method instead of .enqueue, it would be executed on the main thread)

response.enqueue(new Callback<QuestionList>() {
@Override
public void onResponse(Call<QuestionList> call, Response<QuestionList> response) {
QuestionList list = response.body();
data.setValue(list);
}

       @Override
       public void onFailure(Call<QuestionList> call, Throwable throwable) {
 
       }
});

return data;
}


15- create a new package called viewmodel
16- inside, create a class called QuizViewModel that extends ViewModel
//remember that if I need to use context (within the viewModel), I should extend from AndroidViewModel. Otherwise, just extend from ViewModel
private QuizRepository repository= new QuizRepository();

private static LiveData<QuestionList> questionList;

public QuizViewModel() {

// Fetch the list of questions from the repository and store in LiveData
questionList = repository.getQuestionsFromAPI();
}

// Getter method to access the question list LiveData
public static LiveData<QuestionList> getQuestionList() {
return questionList;
}


17- Enable databinding in gradle file:
buildFeatures { databinding = true }
then sync.


18 - in activity_main.xml, encapsulate everuthing within a layout tag:
<layout>
everything
</layout>
and move all the xmls tags to the opening layout tag


19- Add every needed view according to the desire layout design (TextViews, ImageViews, radioButtons, etc)


20- in MainActivity declare these variables:
ActivityMainBinding binding;
QuizViewModel quizViewModel;
List<Question> questionList;

static int result = 0;
static int totalQuestions = 0;
int i = 0;
Initialize them in onCreate method:
binding = DataBindingUtil.setContentView(
this,
R.layout.activity_main
);

// Resetting the scores
result = 0;
totalQuestions = 0;

// Creating an instance of the QuizModel
quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

// Displaying the first question
displayFirstQuestion();

21- In MainActivity implement the displaFirstQuestion method:
public void displayFirstQuestion (){

// Observing Livedate from a viewModel
QuizViewModel.getQuestionList().observe(this, new Observer<QuestionList>() {
@Override
public void onChanged(QuestionList questions) {
questionList = questions;
binding.txtQuestion.setText("Question 1: "+questions.get(0).getQuestion());
binding.option1.setText(questions.get(0).getOption1());
binding.option2.setText(questions.get(0).getOption2());
binding.option3.setText(questions.get(0).getOption3());
binding.option4.setText(questions.get(0).getOption4());

       }
});
22- Change API target to 34 in:
-AndroidManifest file:
tools:targetAPI="34"
- gradle file:
  android scope:
  compileSdk = 34
  default config scope:
  targetSdk= 34


23- Add Internet permission to manifest (right after xmls lines):
<uses-permission android:name="android.permission.INTERNET"/>


24- In the xml package, create a new xml resource called network_security_config
<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
   <base-config cleartextTrafficPermitted="true">
       <trust-anchors>
           <certificates src="system" />
       </trust-anchors>
   </base-config>
</network-security-config>
25- in manifest, inside the application scope:
android:NetworkSecurityConfig="xml/network_security_config"
alternatively, instead of using the created file, we can just add
android:usesCleartextTraffic="true"
(there is no need to use both)


Solving the issue of Quiz not showing the question and options:
Simply change the base URL in the RetrofitInstance class (or RetrofitClient)
when refering to our local system from an emulator we shouldn't use 127.0.0.1 address.
Instead we use 10.0.2.2


Show the results in new Activity
1- Create a new Activity called ResultsActivity
2- in the layout xml file, encapsulate everything within a layout tag
and move the xmls lines into the opening layout tag
3- add 2 textViews and a Button
4- In ResultsActivity:
ActivityResultsBinding binding;
in onCreate method:
binding = DataBindingUtil.setContentView (this, R.layout.activity_results);

binding.txtAnswer.setText("Your score is: "+ MainActivity.result+"/"+MainActivity.totalQuestions);

binding.btnBack.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
Intent intent = new Intent(ResultsActivity.this, MainActivity.class);
startActivity(intent);
}
});

5- In MainActivity, inside DisplayNextQuestion method (right at the begining of it):
// Direct user to Results Activity
if (binding.btnNext.getText().equals("Finish")){
Intent intent = new Intent (MainActivity.this, ResultsActivity.class);
startActivity(intent);
finish();}

