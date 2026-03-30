# Academic Reports Management System

## System Requirements

The system provides a platform for managing users, projects, and work hours, with customized functionalities for different roles:

## Permissions

### Administrator:
- Creation of new projects.
- Viewing and managing the project list.
- Editing and removing existing projects.
- Adding new users to the system.

### Scientific Supervisor:
- Creation of projects.
- Editing projects under their supervision.

### Researcher:
- Recording attendance and work hours on assigned projects.
- Viewing summaries by project and monthly period.

## Scenarios

### 1. User Registration by Administrator

**Initial Assumption:** An authenticated administrator wants to add a new user to the system. The administrator has access to the new user's initial credentials (e.g., name, surname, email, role).

**Normal Flow:** The administrator accesses the user management interface and selects the "Add User" option. They fill in the required fields (name, surname, role) and confirm the operation.

**What Can Go Wrong:**

1. A user with the same username already exists: the system notifies that the user cannot be duplicated.

**System State at Completion:** The user is added to the database and the system confirms the successful account creation.

---

### 2. Password Change

**Initial Assumption:** An authenticated user wants to change their password for security reasons.

**Normal Flow:** The user selects the "Change Password" option, enters their current password, types and confirms the new password.

**What Can Go Wrong:**

1. The current password does not match: the system displays an error message.

**System State at Completion:** The new password is saved.

---

### 3. Project Creation

**Initial Assumption:** An administrator wants to create a new project and assign users to it.

**Normal Flow:** The administrator selects "Create New Project", enters the name, description, and start date. They add a supervisor and researchers to the project and confirm. The system saves the project.

**What Can Go Wrong:**

1. No users assigned: the system warns that the project cannot be created without members.

**Other Activities:** The project can be edited by the supervisor and administrators.

**System State at Completion:** The project is created and available in the project list.

---

### 4. Project Editing

**Initial Assumption:** An administrator or supervisor wants to update information for an existing project.

**Normal Flow:** The administrator accesses the project list, selects "Edit" on the project to modify, updates the desired information (name, description, members), and saves the changes.

**What Can Go Wrong:**

1. No users assigned: the system warns that the project cannot exist without members.

**System State at Completion:** The changes are saved and visible to all involved users.

---

### 5. Work Hours Entry by Researcher

**Initial Assumption:** An authenticated researcher wants to record work hours on a project.

**Normal Flow:** The researcher accesses the "Enter Hours" section, selects the project, and enters the hours worked on the desired dates (excluding holidays and future dates), then confirms. The system saves the information and updates the work hours summary.

**What Can Go Wrong:**

1. Work hours exceed the daily maximum allowed: the system returns an error message.

**System State at Completion:** The hours are recorded and visible in the summary.

---

### 6. Viewing Work Hours Summary by Project, Month, and Year

**Initial Assumption:** An authenticated researcher wants to view a summary of their work hours.

**Normal Flow:** The researcher accesses the "Summary" section, selects a project and a period (month or year). The system displays a summary table with total and detailed hours.

**What Can Go Wrong:**

1. No data available for the selected period: the system notifies the user.

**System State at Completion:** The summary is displayed or exported correctly.

---

### 7. Project Deletion

**Initial Assumption:** An administrator wants to delete an existing project.

**Normal Flow:** The administrator accesses the project list, reviews the project details, and decides to delete it. The system requests final confirmation before proceeding.

**System State at Completion:** The project is deleted and no longer appears in the admin's active list or for other involved users.

---

### 8. Login, Failed Login, and Logout

**Initial Assumption:** A user wants to log into their account and subsequently log out.

**Normal Flow:**
1. The user enters their username and correct password, then clicks "Login".
2. If the credentials are correct, the user is authenticated and redirected to the home page or dashboard.
3. The user selects the "Logout" option and confirms the exit.

**What Can Go Wrong:**
1. If the user enters incorrect credentials (wrong username or password), the system displays an error message and prompts to retry.
2. The system fails to execute logout: an error message is displayed and the user is prompted to retry.

**System State at Completion:**
- If login succeeds, the user is authenticated and redirected to the home page or dashboard.
- If logout succeeds, the user is disconnected and redirected to the login page or home page.

## Quality Assurance

To ensure the system works correctly, two distinct testing series have been implemented:

1. **Acceptance Testing (Selenium + Page Object Model):**
    - Used to test the scenarios described in the system, such as user registration, project creation, work hours entry, etc. Using the **Page Object Model** pattern, tests are structured modularly to ensure each user interface component is tested separately, reducing the risk of testing errors.
      ![Coverage AcceptanceTests](images/AcceptanceTestsCoverage.PNG)
    - Scenarios from the requirements engineering process are tested, making the process more effective and aligned with users' real needs. The scenarios are **realistic**, reflect actual use cases, and include **multiple requirements**.

2. **Unit Testing (JUnit):**
    - Tests individual code units of the models (Day, Person...) independently. These tests ensure that each system component functions correctly in isolation, without dependencies on other parts of the system.
      ![Coverage unitTests](imageS/UnitTestsCoverage.PNG)

   **DayTest**

   Tests the constructor and getter/setter methods of the `Day` class, which represents a day with a date, number of work hours, and indication of whether it is a holiday.
   
   **PersonTest**

   Verifies the constructors (default, parametric, and minimal) and getter/setter methods of the `Person` class, which models a user with attributes such as name, surname, tax code, username, password, and role (`Role`).

   **ProjectTest**

   Tests the constructor and getter/setter methods of the `Project` class, which represents a project with attributes such as name, description, month, year, funding entity, supervisor, researchers, and work logs. Tests include verification of correct setting and retrieval of these attributes and their modifications.

   **WorkLogTest**
   
   Tests the constructor and getter/setter methods of the `WorkLog` class, which represents a work log associated with a researcher and a project, with attributes such as date, hours worked, researcher, and project. Tests verify the correct setting and retrieval of these attributes and modifications via setter methods.

### Total Coverage

![Total Coverage](images/TotalCoverage.PNG)

## System Initialization

To perform the testing phases, the system has been initialized with a set of predefined users belonging to different roles. Some default login credentials for the various users are as follows:

- **Administrator**:
   - Username: `admin`
   - Password: `admin`

- **Supervisor**:
   - Username: `supervisor`
   - Password: `supervisor`

- **Researcher**:
   - Username: `researcher`
   - Password: `researcher`

The system has been populated with some example projects, each with an assigned supervisor and a dedicated group of researchers.

- **Project 1**: AI Research
   - **Supervisor**: `supervisor`
   - **Researchers**: `researcher1`, `researcher2`, `researcher`

- **Project 2**: Space Exploration
   - **Supervisor**: `supervisor2`
   - **Researchers**: `researcher3`, `researcher4`, `researcher`

- **Project 3**: Renewable Energy Solutions
   - **Supervisor**: `supervisor1`
   - **Researchers**: `researcher1`, `researcher3`, `researcher`

- **Project 4**: Climate Change Analysis
   - **Supervisor**: `supervisor2`
   - **Researchers**: `researcher2`, `researcher4`, `researcher`

## Conclusions

- The system shows excellent coverage for **controllers** and **models**, with almost total coverage for **pages** (test scenarios). This indicates that the main functions are rigorously tested.
- Some improvement areas have been identified in the coverage of **methods** in **ServingWebContentApplication**, where testing could be extended to ensure greater robustness.
- Code quality is kept high thanks to a solid implementation of tests at both unit and acceptance levels.

**Acceptance** tests ensure user flows are correct, while **Unit Tests** ensure backend code correctness.

## Authors

- Bazzotti Edoardo VR518747
- Xiao Simone VR519027

