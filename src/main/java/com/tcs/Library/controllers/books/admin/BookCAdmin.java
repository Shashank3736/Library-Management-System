package com.tcs.Library.controllers.books.admin;

import com.tcs.Library.dto.BookDTO;
import com.tcs.Library.entity.Book;
import com.tcs.Library.error.NoAuthorFoundException;
import com.tcs.Library.service.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/admin")
public class BookCAdmin {
    @Autowired
    private BookService bookSvc;

    @PostMapping("/books/add-book")
    public ResponseEntity<?> addBookController(@RequestBody BookDTO dto) {
        return ResponseEntity.ok(bookSvc.createBook(dto));
    }
}
