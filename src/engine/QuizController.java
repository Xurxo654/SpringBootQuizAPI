package engine;

import engine.Entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Date;

@RestController
@RequestMapping
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private CompletionsRepository completionsRepository;

    @PostMapping(value = "/api/quizzes", consumes = "application/json")
    public Quiz newQuiz(@Valid @RequestBody Quiz newQuiz) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((QuizUserDetails)principal).getUsername();
        newQuiz.setCreator(userName);
        return quizRepository.save(newQuiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public Quiz getById(@PathVariable int id) {
        return quizRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found");
        });
    }

    @GetMapping("/api/quizzes")
    public ResponseEntity<Page<Quiz>> getAllQuizzes(@RequestParam(defaultValue = "0") Integer page,
                                    @RequestParam(defaultValue = "10") Integer pageSize,
                                    @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));

        Page<Quiz> pagedResult = quizRepository.findAll(paging);


        return new ResponseEntity<>(pagedResult, new HttpHeaders(), HttpStatus.OK);

    }

    @PostMapping("/api/quizzes/{id}/solve")
    public Answer solveQuiz(@PathVariable int id, @RequestBody UserAnswer answer) {
        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "entity not found");
        });

        Answer result = quiz.solve(answer.getAnswer());
        if (result.isSuccess()) {
            Completions completions = new Completions();
            completions.setId(id);
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            int userId = ((QuizUserDetails)principal).getId();
            completions.setUserId(userId);
            completions.setCompletedAt(new Date());
            completionsRepository.save(completions);
        }
        return quiz.solve(answer.getAnswer());
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity<Integer> deleteQuiz(@PathVariable int id) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userName = ((QuizUserDetails)principal).getUsername();

        Quiz quiz = quizRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "entity not found");
        });

        if (quiz.getCreator().equals(userName)) {
            quizRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/api/quizzes/completed")
    public ResponseEntity<Page<Completions>> getCompletions(@RequestParam(defaultValue = "0") Integer page,
                                                            @RequestParam(defaultValue = "10") Integer pageSize,
                                                            @RequestParam(defaultValue = "id") String sortBy) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        int userId = ((QuizUserDetails)principal).getId();

        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));

        Page<Completions> pagedResult = completionsRepository.findAllByUser(userId, paging);

        return new ResponseEntity<>(pagedResult, HttpStatus.OK);
    }

    @PostMapping(value = "/api/register", consumes = "application/json")
    public User registerUser(@Valid @RequestBody User user) {
        String email = user.getEmail();
        boolean isValidEmail = email.matches("\\w+@\\w+\\.\\w+");
        if(!isValidEmail) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email invalid");
        }
        User repUser = userRepository.findByEmail(email).orElse(null);
        if( repUser != null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "email exists");
        }
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}

