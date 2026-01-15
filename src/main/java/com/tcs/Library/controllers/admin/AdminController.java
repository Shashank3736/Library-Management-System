package com.tcs.Library.controllers.admin;

import com.tcs.Library.dto.ApiResponse;
import com.tcs.Library.dto.BookDTO;
import com.tcs.Library.dto.DonationApprovalRequest;
import com.tcs.Library.entity.Book;
import com.tcs.Library.entity.BookCopy;
import com.tcs.Library.entity.BookDonation;
import com.tcs.Library.enums.BookStatus;
import com.tcs.Library.repository.BookCopyRepo;
import com.tcs.Library.repository.BookRepo;
import com.tcs.Library.service.DonationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final DonationService donationService;
    private final BookRepo bookRepo;
    private final BookCopyRepo bookCopyRepo;

    // ========== DONATION MANAGEMENT ==========

    @GetMapping("/donations")
    public ResponseEntity<ApiResponse<List<BookDonation>>> getPendingDonations() {
        List<BookDonation> donations = donationService.getPendingDonations();
        return ResponseEntity.ok(ApiResponse.success("Pending donations retrieved", donations));
    }

    @PostMapping("/donations/{donationId}/approve")
    public ResponseEntity<ApiResponse<BookDonation>> approveDonation(
            @PathVariable Long donationId,
            @RequestBody DonationApprovalRequest request) {
        BookDonation approved = donationService.approveDonation(donationId, request);
        return ResponseEntity.ok(ApiResponse.success("Donation approved", approved));
    }

    @PostMapping("/donations/{donationId}/reject")
    public ResponseEntity<ApiResponse<BookDonation>> rejectDonation(
            @PathVariable Long donationId,
            @RequestBody(required = false) String adminNotes) {
        BookDonation rejected = donationService.rejectDonation(donationId, adminNotes);
        return ResponseEntity.ok(ApiResponse.success("Donation rejected", rejected));
    }

    // ========== BOOK MANAGEMENT ==========

    @PostMapping("/books")
    public ResponseEntity<ApiResponse<Book>> addBook(@RequestBody BookDTO dto) {
        Book book = new Book();
        book.setBookTitle(dto.getBookTitle());
        book.setCategory(dto.getCategory());
        book.setCoverUrl(dto.getCoverUrl());
        book.setTotalCopies(dto.getQuantity());

        Book savedBook = bookRepo.save(book);

        // Create copies
        for (int i = 0; i < dto.getQuantity(); i++) {
            BookCopy copy = new BookCopy();
            copy.setBook(savedBook);
            copy.setStatus(BookStatus.AVAILABLE);
            bookCopyRepo.save(copy);
        }

        return ResponseEntity.ok(ApiResponse.success("Book added with " + dto.getQuantity() + " copies", savedBook));
    }

    @GetMapping("/books")
    public ResponseEntity<ApiResponse<List<Book>>> getAllBooks() {
        List<Book> books = bookRepo.findAll();
        return ResponseEntity.ok(ApiResponse.success("Books retrieved", books));
    }
}
