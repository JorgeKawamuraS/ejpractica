package upc.edu.pe.ejpractica.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import upc.edu.pe.ejpractica.entities.Category;
import upc.edu.pe.ejpractica.entities.Course;
import upc.edu.pe.ejpractica.services.CategoryService;
import upc.edu.pe.ejpractica.services.CourseService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("courses/categories")
public class CategoriesController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CourseService courseService;

    @GetMapping(produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Category>> getAll(){
        try{
            List<Category> categoryList = categoryService.findAll();
            return new ResponseEntity<List<Category>>(categoryList,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = {"/{categoryId}"},produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> getById(@PathVariable("categoryId") Long categoryId){
        try{
            Category category = categoryService.findById(categoryId);
            if (category!=null){
                return new ResponseEntity<Category>(category,HttpStatus.OK);
            }else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category, BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Category categoryCreated = categoryService.createCategory(category);
        return ResponseEntity.ok(categoryCreated);
    }

    @PostMapping("/{categoryId}/courses")
    public ResponseEntity<Course> createCourse(@Valid @RequestBody Course course, @PathVariable("categoryId") Long categoryId,BindingResult result){
        if (result.hasErrors()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,this.formatMessage(result));
        }
        Course courseCreated = courseService.createCourse(categoryId,course);
        return ResponseEntity.ok(courseCreated);
    }



    private String formatMessage(BindingResult result){

        List<Map<String,String>> errors = result.getFieldErrors().stream()
                .map(err ->{
                    Map<String,String> error =  new HashMap<>();
                    error.put(err.getField(), err.getDefaultMessage());
                    return error;
                }).collect(Collectors.toList());
        ErrorMessage errorMessage = ErrorMessage.builder()
                .code("01")
                .messages(errors).build();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString="";
        try {
            jsonString = mapper.writeValueAsString(errorMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    };

}
