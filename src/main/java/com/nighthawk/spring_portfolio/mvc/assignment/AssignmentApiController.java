package com.nighthawk.spring_portfolio.mvc.assignment;

import com.nighthawk.spring_portfolio.mvc.grade.Grade;
import com.nighthawk.spring_portfolio.mvc.grade.GradeJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController // annotation to simplify the creation of RESTful web services
@RequestMapping("/api/assignments")  // all requests in file begin with this URI
public class AssignmentApiController {

    // Autowired enables Control to connect URI request and POJO Object to easily for Database CRUD operations
    @Autowired
    private AssignmentJpaRepository assignmentRepository;

    @Autowired
    private QuizJpaRepository quizRepository;

    @Autowired
    private GradeJpaRepository gradeRepository;


    int idCount = 1; //Needed to prevent id overlap with quizzes and assignments

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/")
    public ResponseEntity<List<Object>> getRepositories() {
        List<Object> combined = new ArrayList<>();
        combined.addAll(assignmentRepository.findAll());
        combined.addAll(quizRepository.findAll());
        return new ResponseEntity<>(combined, HttpStatus.OK);
    }

    

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Assignment> getById(@PathVariable Long id) {
        Optional<Assignment> assignmentOptional = assignmentRepository.findById(id);
        
        return assignmentOptional.map(assignment -> new ResponseEntity<>(assignment, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/post")
    public ResponseEntity<Object> postPerson(
    @RequestParam("title") String title,
    @RequestParam("desc") String desc,
    @RequestParam("link") String link,
    @RequestParam("maxPoints") int maxPoints) {
        if(title.length() < 3 || title.length() > 100) {
            return new ResponseEntity<>("Title is less than 3 or longer than 100 characters", HttpStatus.BAD_REQUEST);
        }
        if(desc.length() < 3 || desc.length() > 50000) {
            return new ResponseEntity<>("Desc is less than 3 or longer than 500000 characters", HttpStatus.BAD_REQUEST);
        }
        if(link.length() < 3 || link.length() > 400) {
            return new ResponseEntity<>("Link is less than 3 or longer than 400 characters", HttpStatus.BAD_REQUEST);
        }
        if(maxPoints <= 0) {
            return new ResponseEntity<>("maxPoints must be positive", HttpStatus.BAD_REQUEST);
        }
        
        assignmentRepository.save(new Assignment(title, desc, link, maxPoints, idCount));
        idCount++;
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/postQuiz")
    public ResponseEntity<Object> postQuiz(
    @RequestParam("title") String title,
    @RequestParam("desc") String desc,
    @RequestParam("maxPoints") int maxPoints,
    @RequestBody final Map<String,Object> questions) {
        if(title.length() < 3 || title.length() > 100) {
            return new ResponseEntity<>("Title is less than 3 or longer than 100 characters", HttpStatus.BAD_REQUEST);
        }
        if(desc.length() < 3 || desc.length() > 50000) {
            return new ResponseEntity<>("Desc is less than 3 or longer than 500000 characters", HttpStatus.BAD_REQUEST);
        }
        if(maxPoints <= 0) {
            return new ResponseEntity<>("maxPoints must be positive", HttpStatus.BAD_REQUEST);
        }
        
        //Check Questions
        // Extract Attributes from JSON

        Map<String, Object> attributeMap = new HashMap<>();
        int questionNum = 1;
        for (Map.Entry<String,Object> questionTemp : questions.entrySet())  {
            if (!(questionTemp.getValue() instanceof HashMap)) {
                return new ResponseEntity<>("Questions should contain hashmaps", HttpStatus.BAD_REQUEST);
            }
            Map<String, Object> question = ((Map<String, Object>)questionTemp.getValue());

            Map<String, Object> subAttributeMap = new HashMap<>();
            

            for (Map.Entry<String,Object> attribute : question.entrySet())  {
                if(attribute.getKey().equals("answers"))
                if (attribute.getValue() instanceof List) {
                    subAttributeMap.put(attribute.getKey(), attribute.getValue());
                } else {
                    return new ResponseEntity<>("Answers attribute should be a list", HttpStatus.BAD_REQUEST);
                }
            
                if(attribute.getKey().equals("correctAnswer"))
                    subAttributeMap.put(attribute.getKey(), attribute.getValue());

                if(attribute.getKey().equals("desc"))
                    subAttributeMap.put(attribute.getKey(), attribute.getValue());
                
            }
            //Does it have all attributes?
            if(!(subAttributeMap.containsKey("answers")  && subAttributeMap.containsKey("correctAnswer") && subAttributeMap.containsKey("desc"))) {
                return new ResponseEntity<>("Missing attributes for question #" + questionNum + "answers, correctAnswer, and desc are required: " + subAttributeMap, HttpStatus.BAD_REQUEST); 
            }   

            //Add question data to main attribute map
            attributeMap.put(Integer.toString(questionNum), subAttributeMap);
            questionNum++;
        }
        
        quizRepository.save(new Quiz(title, desc, maxPoints, idCount, attributeMap));
        idCount++;
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }

    @PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> personStats(@RequestBody final Map<String,Object> request_map) {
        // find ID
        if (!(request_map.get("id") instanceof String)){
            return new ResponseEntity<>("id should be a String", HttpStatus.BAD_REQUEST);
        }
        int id=Integer.parseInt((String)request_map.get("id"));  

        List<Assignment> assignments = assignmentRepository.findByJointId(id);
        Optional<Assignment> optional = assignments.isEmpty() ? Optional.empty() : Optional.of(assignments.get(0));

        if (optional.isPresent()) {  // Good ID
            Assignment assignment = optional.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> attributeMap = new HashMap<>();
            for (Map.Entry<String,Object> submission : request_map.entrySet())  {
                // Add needed attributes to attributeMap

                if(submission.getKey().equals("contributors"))
                    if (submission.getValue() instanceof List) {
                        attributeMap.put(submission.getKey(), submission.getValue());
                    } else {
                        return new ResponseEntity<>("Contributors attribute should be a list", HttpStatus.BAD_REQUEST);
                    }
                
                if(submission.getKey().equals("title"))
                    attributeMap.put(submission.getKey(), submission.getValue());
                
                if(submission.getKey().equals("desc"))
                    attributeMap.put(submission.getKey(), submission.getValue());

                if(submission.getKey().equals("link"))
                    attributeMap.put(submission.getKey(), submission.getValue());

                if(submission.getKey().equals("username"))
                    attributeMap.put(submission.getKey(), submission.getValue());
            }

            //Does it have all attributes?
            if(!(attributeMap.containsKey("contributors")  && attributeMap.containsKey("title") && attributeMap.containsKey("desc") && attributeMap.containsKey("link"))) {
                return new ResponseEntity<>("Missing attributes. username, contributors, title, desc, and link are required" + attributeMap, HttpStatus.BAD_REQUEST); 
            }

            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = assignment.getSubmissions();
            date_map.put( (String) request_map.get("username"), attributeMap);

            assignment.setSubmissions(date_map);
            assignmentRepository.save(assignment);

            //Save grade as well for each contributor
            List<String> contributors = (List<String>) attributeMap.get("contributors");
            for (String contributor : contributors)  {
                Grade grade = new Grade(contributor, "temp", assignment.getTitle(), assignment.getMaxPoints(), -1);
                gradeRepository.save(grade);
            }



            // return Person with update Stats
            return new ResponseEntity<>(assignment, HttpStatus.OK);
        }

        //Check for quiz

        List<Quiz> quizzes = quizRepository.findByJointId(id);
        Optional<Quiz> optional2 = quizzes.isEmpty() ? Optional.empty() : Optional.of(quizzes.get(0));

        if (optional2.isPresent()) {  // Good ID
            Quiz quiz = optional2.get();  // value from findByID

            // Extract Attributes from JSON
            Map<String, Object> attributeMap = new HashMap<>();
            for (Map.Entry<String,Object> submission : request_map.entrySet())  {
                // Add needed attributes to attributeMap

                if(submission.getKey().equals("answers"))
                    if (submission.getValue() instanceof List) {
                        attributeMap.put(submission.getKey(), submission.getValue());
                    } else {
                        return new ResponseEntity<>("Contributors attribute should be a list", HttpStatus.BAD_REQUEST);
                    }
                
                if(submission.getKey().equals("username"))
                    attributeMap.put(submission.getKey(), submission.getValue());
            }

            //Does it have all attributes?
            if(!(attributeMap.containsKey("answers")  && attributeMap.containsKey("username"))) {
                return new ResponseEntity<>("Missing attributes. username and answers are required" + attributeMap, HttpStatus.BAD_REQUEST); 
            }

            // Set Date and Attributes to SQL HashMap
            Map<String, Map<String, Object>> date_map = quiz.getSubmissions();
            date_map.put( (String) request_map.get("username"), attributeMap );

            quiz.setSubmissions(date_map);
            quizRepository.save(quiz);

            //Grade it
            ArrayList<Integer> submittedAnswers = (ArrayList<Integer>)attributeMap.get("answers");
            Map<String, Object> questions = quiz.getQuestions();
            
            int score = 0;
            int i = 0;
            for(Map.Entry<String,Object> question : questions.entrySet()) {
                if(((Map<String, Object>)question.getValue()).get("correctAnswer") == submittedAnswers.get(i)) {
                    score++;
                } else {
                    
                }
                i++;
            }

            Grade grade = new Grade((String)attributeMap.get("username"), "temp", quiz.getTitle(), quiz.getMaxPoints(), score);
            gradeRepository.save(grade);

            // return Person with update Stats
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        }

        // return Bad ID
        return new ResponseEntity<>("Bad ID", HttpStatus.BAD_REQUEST); 
    }
}
