package com.nighthawk.spring_portfolio.mvc.assignment;

import com.nighthawk.spring_portfolio.mvc.grade.Grade;
import com.nighthawk.spring_portfolio.mvc.grade.GradeJpaRepository;

import com.nighthawk.spring_portfolio.mvc.assignment.Flashcard;
import com.nighthawk.spring_portfolio.mvc.assignment.FlashcardJpaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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

    @Autowired
    private FlashcardJpaRepository flashcardRepository;

    int idCount = 1; //Needed to prevent id overlap with quizzes and assignments
    private int getNextJointId() {
        int assignmentCount = (int) assignmentRepository.count();
        int quizCount = (int) quizRepository.count();
        int flashcardCount = (int) flashcardRepository.count();
        return assignmentCount + quizCount + flashcardCount + 1;
    }

    

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/post")
    public ResponseEntity<Object> postPerson(
    @RequestParam("title") String title,
    @RequestParam("desc") String desc,
    @RequestParam("link") String link,
    @RequestParam("maxPoints") int maxPoints,
    @RequestParam("due") String due) {
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
        LocalDateTime dueDateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dueDateTime = LocalDateTime.parse(due, formatter);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Invalid due date format. Please provide the date in yyyy-MM-dd HH:mm:ss format.", HttpStatus.BAD_REQUEST);
        }

        idCount = getNextJointId();
        assignmentRepository.save(new Assignment(title, desc, link, maxPoints, idCount, dueDateTime));
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/")
    public ResponseEntity<List<Object>> getRepositories() {
        List<Object> combined = new ArrayList<>();
        combined.addAll(assignmentRepository.findAll());
        combined.addAll(quizRepository.findAll());
        combined.addAll(flashcardRepository.findAll());
        return new ResponseEntity<>(combined, HttpStatus.OK);
    }
    
    private Object getByIdHelper(int id) {
        Object toReturn = null;
        List<Object> combined = new ArrayList<>();
        combined.addAll(assignmentRepository.findAll());
        combined.addAll(quizRepository.findAll());
        combined.addAll(flashcardRepository.findAll());
        for (Object object : combined) {
            if (object.getClass() == Assignment.class) {
                if (((Assignment) object).getJointId() == id) {
                    toReturn = object;
                }
            } else if (object.getClass() == Quiz.class) {
                if (((Quiz) object).getJointId() == id) {
                    toReturn = object;
                }
            } else if (object.getClass() == Flashcard.class) {
                if (((Flashcard) object).getJointId() == id) {
                    toReturn = object;
                }
            }
        }
        return toReturn;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        Object result = getByIdHelper(id.intValue());
        if (result != null) {
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
        return new ResponseEntity<>("Bad ID", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @GetMapping("/{id}/username")
    public ResponseEntity<String> getUsername(@PathVariable Long id) {
        Object object = getByIdHelper(id.intValue());

        if (object.getClass() == Assignment.class) {
            Assignment assignment = (Assignment) object;
            Map<String, Map<String, Object>> submissions = assignment.getSubmissions();
            if (submissions != null && !submissions.isEmpty()) {
                String username = (String) submissions.values().iterator().next().get("username");
                return new ResponseEntity<>(username, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("Bad ID, must be an assignment", HttpStatus.BAD_REQUEST);
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @PostMapping("/postQuiz")
    public ResponseEntity<Object> postQuiz(
    @RequestParam("title") String title,
    @RequestParam("desc") String desc,
    @RequestParam("maxPoints") int maxPoints,
    @RequestBody final Map<String,Object> questions,
    @RequestParam("due") String due) {
        if(title.length() < 3 || title.length() > 100) {
            return new ResponseEntity<>("Title is less than 3 or longer than 100 characters", HttpStatus.BAD_REQUEST);
        }
        if(desc.length() < 3 || desc.length() > 50000) {
            return new ResponseEntity<>("Desc is less than 3 or longer than 500000 characters", HttpStatus.BAD_REQUEST);
        }
        if(maxPoints <= 0) {
            return new ResponseEntity<>("maxPoints must be positive", HttpStatus.BAD_REQUEST);
        }
        LocalDateTime dueDateTime;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dueDateTime = LocalDateTime.parse(due, formatter);
        } catch (DateTimeParseException e) {
            return new ResponseEntity<>("Invalid due date format. Please provide the date in yyyy-MM-dd HH:mm:ss format.", HttpStatus.BAD_REQUEST);
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
        
        idCount = getNextJointId();
        quizRepository.save(new Quiz(title, desc, maxPoints, idCount, attributeMap, dueDateTime));
        return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
    }

   

@PostMapping(value = "/submit", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<Object> personStats(@RequestBody final Map<String, Object> request_map) {
    // find ID
    if (!(request_map.get("id") instanceof String)){
        return new ResponseEntity<>("id should be a String", HttpStatus.BAD_REQUEST);
    }
    int id = Integer.parseInt((String) request_map.get("id"));

    Object object = getByIdHelper(id);

    if (object != null) {  // Good ID
        try {
            if (object instanceof Assignment) {
                Assignment assignment = (Assignment) object;
                // Extract Attributes from JSON
                Map<String, Object> attributeMap = new HashMap<>();
                for (Map.Entry<String, Object> submission : request_map.entrySet()) {
                    // Add needed attributes to attributeMap

                    if (submission.getKey().equals("contributors")) {
                        if (submission.getValue() instanceof List) {
                            attributeMap.put(submission.getKey(), submission.getValue());
                        } else {
                            return new ResponseEntity<>("Contributors attribute should be a list", HttpStatus.BAD_REQUEST);
                        }
                    }

                    if (submission.getKey().equals("title"))
                        attributeMap.put(submission.getKey(), submission.getValue());

                    if (submission.getKey().equals("desc"))
                        attributeMap.put(submission.getKey(), submission.getValue());

                    if (submission.getKey().equals("link"))
                        attributeMap.put(submission.getKey(), submission.getValue());

                    if (submission.getKey().equals("username"))
                        attributeMap.put(submission.getKey(), submission.getValue());
                }

                // Does it have all attributes?
                if (!(attributeMap.containsKey("contributors") && attributeMap.containsKey("title") && attributeMap.containsKey("desc") && attributeMap.containsKey("link"))) {
                    return new ResponseEntity<>("Missing attributes. username, contributors, title, desc, and link are required" + attributeMap, HttpStatus.BAD_REQUEST);
                }

                // Set Date and Attributes to SQL HashMap
                Map<String, Map<String, Object>> date_map = assignment.getSubmissions();
                date_map.put((String) request_map.get("username"), attributeMap);

                assignment.setSubmissions(date_map);
                assignmentRepository.save(assignment);

                // Save grade as well for each contributor
                List<String> contributors = (List<String>) attributeMap.get("contributors");
                for (String contributor : contributors) {
                    Grade grade = new Grade(contributor, "temp", assignment.getTitle(), assignment.getMaxPoints(), -1);
                    gradeRepository.save(grade);
                }

                // return Assignment with updated Stats
                return new ResponseEntity<>(assignment, HttpStatus.OK);
            } else if (object instanceof Quiz) {
                Quiz quiz = (Quiz) object;  // value from findByID

                // Extract Attributes from JSON
                Map<String, Object> attributeMap = new HashMap<>();
                for (Map.Entry<String, Object> submission : request_map.entrySet()) {
                    // Add needed attributes to attributeMap

                    if (submission.getKey().equals("answers")) {
                        if (submission.getValue() instanceof List) {
                            attributeMap.put(submission.getKey(), submission.getValue());
                        } else {
                            return new ResponseEntity<>("Answers attribute should be a list", HttpStatus.BAD_REQUEST);
                        }
                    }

                    if (submission.getKey().equals("username"))
                        attributeMap.put(submission.getKey(), submission.getValue());
                }

                // Does it have all attributes?
                if (!(attributeMap.containsKey("answers") && attributeMap.containsKey("username"))) {
                    return new ResponseEntity<>("Missing attributes. username and answers are required" + attributeMap, HttpStatus.BAD_REQUEST);
                }

                // Set Date and Attributes to SQL HashMap
                Map<String, Map<String, Object>> date_map = quiz.getSubmissions();
                date_map.put((String) request_map.get("username"), attributeMap);

                quiz.setSubmissions(date_map);
                quizRepository.save(quiz);

                // Grade it
                @SuppressWarnings("unchecked")
                ArrayList<Integer> submittedAnswers = (ArrayList<Integer>) attributeMap.get("answers");
                Map<String, Object> questions = quiz.getQuestions();

                int score = 0;
                int i = 0;
                for (Map.Entry<String, Object> question : questions.entrySet()) {
                    if (i < submittedAnswers.size() && submittedAnswers.get(i).equals(((Map<String, Object>) question.getValue()).get("correctAnswer"))) {
                        score++;
                    }
                    i++;
                }

                Grade grade = new Grade((String)request_map.get("username"), "temp", quiz.getTitle(), quiz.getMaxPoints(), score);
                
                gradeRepository.save(grade);

                // return Quiz with updated Stats
                return new ResponseEntity<>(quiz, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while processing the submission: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // return Bad ID
    return new ResponseEntity<>("Bad ID", HttpStatus.BAD_REQUEST);
}



@CrossOrigin(origins = "*", allowedHeaders = "*")
@PostMapping("/postFlashcard")
public ResponseEntity<Object> postFlashcard(
        @RequestParam("title") String title,
        @RequestParam("desc") String desc,
        @RequestParam("maxPoints") int maxPoints,
        @RequestParam("reqCards") int reqCards,
        @RequestBody final Map<String, String> flashcards,
        @RequestParam("due") String due) {

    if (title.length() < 3 || title.length() > 100) {
        return new ResponseEntity<>("Title is less than 3 or longer than 100 characters", HttpStatus.BAD_REQUEST);
    }
    if (desc.length() < 3 || desc.length() > 50000) {
        return new ResponseEntity<>("Desc is less than 3 or longer than 50000 characters", HttpStatus.BAD_REQUEST);
    }
    if (maxPoints <= 0) {
        return new ResponseEntity<>("maxPoints must be positive", HttpStatus.BAD_REQUEST);
    }
    if (reqCards <= 0) {
        return new ResponseEntity<>("reqCards must be positive", HttpStatus.BAD_REQUEST);
    }

    LocalDateTime dueDateTime;
    try {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        dueDateTime = LocalDateTime.parse(due, formatter);
    } catch (DateTimeParseException e) {
        return new ResponseEntity<>("Invalid due date format. Please provide the date in yyyy-MM-dd HH:mm:ss format.", HttpStatus.BAD_REQUEST);
    }

    // Check flashcards
    if (flashcards.isEmpty()) {
        return new ResponseEntity<>("Flashcards should not be empty", HttpStatus.BAD_REQUEST);
    }

    idCount = getNextJointId();
    flashcardRepository.save(new Flashcard(title, desc, maxPoints, idCount, flashcards, dueDateTime, reqCards));
    return new ResponseEntity<>("Created successfully", HttpStatus.CREATED);
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@GetMapping("/getReqFlashcards/{id}")
public ResponseEntity<Object> getCompletedFlashcards(@PathVariable int id) {
    try {
        Flashcard flashcard = (Flashcard)getByIdHelper(id);
        return new ResponseEntity<>( flashcard.getReqCards(), HttpStatus.OK);
    } catch(Exception e) {
        return new ResponseEntity<>("Flashcard not found", HttpStatus.NOT_FOUND);
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@PostMapping("/getCompletedFlashcards/{id}")
public ResponseEntity<Object> getCompletedFlashcards(@PathVariable int id, @RequestParam("username") String username) {
    try {
        Flashcard flashcard = (Flashcard)getByIdHelper(id);
        return new ResponseEntity<>( flashcard.getCompletions().get(username), HttpStatus.OK);
    } catch(Exception e) {
        return new ResponseEntity<>("Username or flashcards not found", HttpStatus.NOT_FOUND);
    }
}

@CrossOrigin(origins = "*", allowedHeaders = "*")
@PostMapping("/completeFlashcard/{id}")
public ResponseEntity<Object> completeFlashcard(@PathVariable int id, @RequestParam("username") String username) {
    try {
        Flashcard flashcard = (Flashcard)getByIdHelper(id);
        Map<String, Integer> completions = flashcard.getCompletions();
        completions.put(username, completions.getOrDefault(username, 0) + 1);
        flashcard.setCompletions(completions);
        flashcardRepository.save(flashcard);
        return new ResponseEntity<>( flashcard.getCompletions(), HttpStatus.OK);
    } catch(Exception e) {
        return new ResponseEntity<>("Flashcard not found", HttpStatus.NOT_FOUND);
    }
}

}
