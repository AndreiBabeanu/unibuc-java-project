package com.unibuc.cinema_booking.controller;

import com.unibuc.cinema_booking.dto.*;
import com.unibuc.cinema_booking.model.EUserCategory;
import com.unibuc.cinema_booking.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

//definim un bean care are rolul de a fi un controller, si nu orice tip de controller si unul de rest
//care nu intoarce neaparat un view ca in MVC-ul clasic
@RestController
//definim url-ul root de la care pleaca toate end point-urile definite in acest controller
//prin conventie punem numele entitatii la plural
@RequestMapping("/users")
@Validated
public class UserController {

    //ca sa injectam o instanta de user service ( ca sa stim ce instanta de user service folosim)
    //daca nu avem anotarea de @Service pe clasa UserService o sa avem eroarea fiindca nu am facut-o been
    //dar nu facem asa fiindca nu e recomandat autowired pe field-uri ( nu putem sa facem clasa imutabila, o sa fie greu la testat)
    //nu putem sa il facem final, nu o sa putem sa mock-uim anumite servicii
    //@Autowired

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ordinea de creare controller - service - repository ( daca mai e nevoie de business logic facem la nivelul service-ului)
    // pentru crearea de date, ar fi bine sa trimitem codul 201 care inseama created ok()
    // pentru asta trebuie sa imbracam tipul returnat (raspunsul ) in ResponsEntity
    // acest lucru ne ajuta sa mai trimitem si alte informatii pe langa entitatea in sine, cum ar fi statusul
    // folosim metoda created din ResponsEntity si ii dam ca parametru url-ul la care se gaseste entitatea
    //@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)

    @PostMapping("/client")
    public ResponseEntity<UserRequest> createClientAccount(@RequestBody @Valid UserRequest userDto) {
        UserRequest savedUserDto = userService.createUser(userDto, EUserCategory.BRONZE);
        return new ResponseEntity<>(savedUserDto, null == savedUserDto ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @PostMapping("/admin")
    public ResponseEntity<UserRequest> createAdminAccount(@RequestBody @Valid UserRequest userDto) {
        UserRequest savedUserDto = userService.createUser(userDto, EUserCategory.ADMIN);
        return new ResponseEntity<>(savedUserDto, null == savedUserDto ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserRequest> getOne(@PathVariable("id") Long id) {
        UserRequest userDto = userService.getOne(id);
        return new ResponseEntity<>(userDto, null == userDto ? HttpStatus.NOT_FOUND : HttpStatus.OK);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserRequest> getAll() {
        return userService.getAll();
    }

    @DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String remove(@PathVariable("id") Long id) {
        boolean result = userService.delete(id);
        return result ? String.format("The user with id : %s was removed", id)
                : String.format("The user with id : %s was not removed", id);
    }

    @PutMapping
    public String updateCategoryUser(@Valid @RequestBody UpdateUserCategoryRequest updateRequest) {
        int numberOfUpdatesRows =  userService.updateCategoryUser(updateRequest);
        return numberOfUpdatesRows == 1 ? String.format("The user with id : %d was updated", updateRequest.getUserId())
                : String.format("The user with id : %d was not updated", updateRequest.getUserId());
    }

    @PostMapping("/login")
    public UserRequest login(@RequestBody @Valid LoginRequest loginRequest) {
        UserRequest loggedUserDTO = userService.login(loginRequest);
        return loggedUserDTO;
    }

    @PostMapping("/client/history")
    public List<HistoryReservationsUserResponse> getHistoryOfUser(@RequestBody @Valid HistoryRequest historyRequest) {
        List<HistoryReservationsUserResponse> history = userService.getUserHistoryOfReservations(historyRequest);
        return history;
    }

//    @PostMapping("/makeReservation")
//    public ResponseEntity<UserRequest> createReservation(@RequestBody @Valid UserRequest userDto) {
//        UserRequest savedUserDto = userService.createReservation(userDto, EUserCategory.BRONZE);
//        return new ResponseEntity<>(savedUserDto, null == savedUserDto ? HttpStatus.EXPECTATION_FAILED : HttpStatus.CREATED);
//    }


}
