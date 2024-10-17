<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
</head>
<body>

<h1>Task Scheduler API</h1>
<p>This project provides a API for calculating project schedules based on task dependencies. It features processes a project plans and associated tasks.</p>

<h2>Technologies Used</h2>
<ul>
    <li><strong>Spring Boot</strong> for RESTful services</li>
    <li><strong>Java 17</strong></li>
    <li><strong>Maven</strong> for project management</li>
    <li><strong>Postman</strong> for API testing</li>
</ul>

<h2>How to Run the Application</h2>

<ol>
    <li><strong>Clone the repository:</strong></li>
    <pre><code>git clone https://github.com/your-username/task-scheduler.git</code></pre>
    
 <li><strong>Navigate to the project directory:</strong></li>
    <pre><code>cd task-scheduler</code></pre>

<li><strong>Build the project using Maven:</strong></li>
    <pre><code>mvn clean install</code></pre>

<li><strong>Run the application:</strong></li>
    <pre><code>mvn spring-boot:run</code></pre>

<li><strong>Access the API:</strong></li>
    <p>The application will run on <code>http://localhost:8080</code>.</p>
</ol>


<h2>API Endpoints</h2>

<h3>1. Calculate Schedule</h3>

<p><strong>URL</strong>: <code>POST /api/schedule</code></p>
<p>This endpoint takes a project plan and calculates the schedule for its tasks, including start and end dates based on dependencies.</p>

<h4>Request Body</h4>
<pre><code>{
  "projectName": "Project A",
  "tasks": [
    {
      "taskName": "Task 1",
      "duration": 4,
      "dependencies": []
    },
    {
      "taskName": "Task 2",
      "duration": 3,
      "dependencies": ["Task 3"]
    },
    {
      "taskName": "Task 3",
      "duration": 5,
      "dependencies": ["Task 1"]
    },
    {
      "taskName": "Task 4",
      "duration": 4,
      "dependencies": ["Task 2", "Task 3"]
    }
  ]
}</code></pre>

<h4>Response</h4>
<pre><code>{
  "projectName": "Project A",
  "message": "Project plan submitted and schedule generated."
}</code></pre>

<h3>2. Retrieve Generated Schedule</h3>

<p><strong>URL</strong>: <code>GET /api/schedule</code></p>
<p>This endpoint returns the generated schedule for the project, including start and end dates for each task.</p>

<h4>Response</h4>
<pre><code>{
  "projectName": "Project A",
  "schedule": [
    {
      "taskName": "Task 1",
      "startDate": "2024-10-17",
      "endDate": "2024-10-21"
    },
    {
      "taskName": "Task 2",
      "startDate": "2024-10-22",
      "endDate": "2024-10-25"
    },
    {
      "taskName": "Task 3",
      "startDate": "2024-10-21",
      "endDate": "2024-10-26"
    },
    {
      "taskName": "Task 4",
      "startDate": "2024-10-26",
      "endDate": "2024-10-30"
    }
  ]
}</code></pre>

<h2>Error Handling</h2>

<p>Global error handling is implemented to catch exceptions and provide appropriate HTTP responses.</p>

<h4>Examples</h4>

<p><strong>400 Bad Request</strong>: For invalid input data, such as missing project or task details.</p>
<pre><code>{
  "message": "Error processing project plan",
  "error": "Task dependencies not found"
}</code></pre>

<p><strong>500 Internal Server Error</strong>: For unexpected server errors.</p>
<pre><code>{
  "message": "An unexpected error occurred"
}</code></pre>

<h2>Testing with Postman</h2>

<p>You can easily test the API using Postman by following these steps:</p>

<h3>1. Testing Schedule Calculation (<code>POST</code>)</h3>

<ul>
    <li><strong>Request Type</strong>: <code>POST</code></li>
    <li><strong>URL</strong>: <code>http://localhost:8080/api/schedule</code></li>
    <li><strong>Body</strong>: Select <code>raw</code>, and choose <code>JSON</code>, then paste the example JSON request from above.</li>
    <li>Click <strong>Send</strong> to submit the request.</li>
</ul>

<h3>2. Retrieving the Generated Schedule (<code>GET</code>)</h3>

<ul>
    <li><strong>Request Type</strong>: <code>GET</code></li>
    <li><strong>URL</strong>: <code>http://localhost:8080/api/schedule</code></li>
    <li>Click <strong>Send</strong> to retrieve the schedule.</li>
</ul>

<h3>Expected Responses</h3>

<ul>
    <li><strong>POST</strong>: A success message confirming that the project plan was submitted and the schedule was generated.</li>
    <li><strong>GET</strong>: The project schedule, including start and end dates for each task.</li>
</ul>


</body>
</html>
