package com.as.booklibraryapp.controller;

import com.as.booklibraryapp.domain.Book;
import com.as.booklibraryapp.domain.Loan;
import com.as.booklibraryapp.domain.Users;
import com.as.booklibraryapp.repository.BookRepository;
import com.as.booklibraryapp.repository.LoanRepository;
import com.as.booklibraryapp.service.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Set;


@Controller
@RequestMapping("/user")
public class UserController {

    //private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final LoanRepository loanRepository;

    //private final JpaUserRepository jpaUserRepository;

    private final UserService userService;

   /* public UserController(UserRepository userRepository, BookRepository bookRepository, LoanRepository loanRepository, JpaUserRepository jpaUserRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.jpaUserRepository = jpaUserRepository;
    }*/

    public UserController(BookRepository bookRepository, LoanRepository loanRepository, UserService userService) {
        this.bookRepository = bookRepository;
        this.loanRepository = loanRepository;
        this.userService = userService;
    }

    @GetMapping("new")
    public String registerUser(Model model){
        model.addAttribute("user", new Users());
        return "user/registration-form";
    }

    // eventualno postavit na new kao i prethodni request
    @PostMapping("/entryAfterRegistration")
    //@PostMapping("new")
    public String entryAfterRegistration(@ModelAttribute("user") @Valid Users user, Errors errors){
        if(errors.hasErrors()){
            return "user/registration-form";
        }

        userService.save(user);
        return "/login";
    }

    @GetMapping("entryAfterLogin")
    public String entryAfterLogin(Model model){
        Set<Book> bookSet = bookRepository.findAllBooks();
        Set<Users> userSet = userService.findAllUsers();

        model.addAttribute("bookSet", bookSet);
        model.addAttribute("userSet", userSet);

        return "/user/entry";
    }

    @PostMapping("newLoans")
    public String newLoans(Users user, HttpServletRequest request, Model model){

        Long itemId = Long.valueOf(Integer.parseInt(request.getParameter("btnBorrow")));

        Set<Book> books = bookRepository.findAllBooks();
        Set<Users> users = userService.findAllUsers();

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Loan loan = new Loan();
        String username = "";

        for (Book book:books) {

            if(book.getId().equals(itemId)){
                loan.setTitle(book.getTitle());
            }
        }

        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
            loan.setUsername(username);
            //System.out.println(username);
        }

        for(Users u : users){
           if(u.getUsername().equals(username)){
               loan.setName(u.getName());
               loan.setSurname(u.getSurname());
           }
        }

        loanRepository.saveLoans(loan);

        return "/confirm";
    }

    @GetMapping("loans")
    public String loanList(Model model){

        Set<Loan> loanSet = loanRepository.findAllLoans();

        model.addAttribute("loanSet", loanSet);

        return "/user/loans";
    }

    @GetMapping("search")
    public String searchForm(Model model){
        model.addAttribute("users", new Users());

        return "user/search";
    }

    @PostMapping("search")
    public String searchResults(Users user, Model model){
        Set<Users> userSet = userService.findByEnteredQueryData(user);
        model.addAttribute("userSet", userSet);
        return "user/search";
    }
}
