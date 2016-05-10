package ua.netcracker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.netcracker.model.entity.QuestionForInterview;
import ua.netcracker.model.service.QuestionForInterviewService;

import java.util.Collection;
import java.util.List;

@Controller
public class QuestionForInterviewController {
    @Autowired
    private QuestionForInterviewService questionForInterviewService;

    @RequestMapping(value = "/service/getAllQuestionForInterview", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Collection<QuestionForInterview>> getQuestionsForInterview(){
        List<QuestionForInterview> questionForInterview = (List<QuestionForInterview>)
                questionForInterviewService.getAllSubject();
        if (questionForInterview == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<Collection<QuestionForInterview>>(questionForInterview, HttpStatus.OK);
    }
    @RequestMapping(value = "/service/addQuestion", method = RequestMethod.GET)
    @ResponseBody
    public boolean addQuestion(@RequestParam int subjectId,
                               @RequestParam String questionValue){

        return false;
    }
}
