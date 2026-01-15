package com.tcs.Library.scheduler;

import com.tcs.Library.entity.IssuedBooks;
import com.tcs.Library.entity.User;
import com.tcs.Library.repository.IssuedBooksRepo;
import com.tcs.Library.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class DefaulterScheduler {

    private final IssuedBooksRepo issuedBooksRepo;
    private final UserRepo userRepo;

    private static final BigDecimal DEFAULTER_FINE_THRESHOLD = new BigDecimal("100.00");
    private static final int DEFAULTER_OVERDUE_DAYS = 30;

    /**
     * Runs daily at midnight to update defaulter statuses
     */
    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    public void updateDefaulterStatuses() {
        log.info("Starting daily defaulter status update job");

        LocalDate today = LocalDate.now();
        LocalDate cutoffDate = today.minusDays(DEFAULTER_OVERDUE_DAYS);

        // Find all users with severely overdue books (30+ days)
        List<IssuedBooks> severelyOverdue = issuedBooksRepo.findSeverelyOverdueBooks(cutoffDate);
        Set<Long> defaulterUserIds = new HashSet<>();

        for (IssuedBooks issuedBook : severelyOverdue) {
            User user = issuedBook.getUser();
            if (!user.isDefaulter()) {
                user.setDefaulter(true);
                userRepo.save(user);
                defaulterUserIds.add(user.getId());
                log.warn("User {} marked as defaulter (book overdue since {})",
                        user.getEmail(), issuedBook.getDueDate());
            }
        }

        // Also mark users with high unpaid fines
        List<User> allUsers = userRepo.findAll();
        for (User user : allUsers) {
            if (!defaulterUserIds.contains(user.getId())) {
                if (user.getTotalUnpaidFine().compareTo(DEFAULTER_FINE_THRESHOLD) > 0) {
                    if (!user.isDefaulter()) {
                        user.setDefaulter(true);
                        userRepo.save(user);
                        log.warn("User {} marked as defaulter (unpaid fines: ₹{})",
                                user.getEmail(), user.getTotalUnpaidFine());
                    }
                } else {
                    // Check if user can be removed from defaulter list
                    boolean hasOverdue = issuedBooksRepo.findByUserIdAndStatus(user.getId(), "BORROWED")
                            .stream()
                            .anyMatch(ib -> ib.getDueDate().isBefore(cutoffDate));

                    if (!hasOverdue && user.isDefaulter()) {
                        user.setDefaulter(false);
                        userRepo.save(user);
                        log.info("User {} removed from defaulter list", user.getEmail());
                    }
                }
            }
        }

        // Mark overdue books status
        List<IssuedBooks> overdueBooks = issuedBooksRepo.findOverdueBooks(today);
        for (IssuedBooks book : overdueBooks) {
            if (!"OVERDUE".equals(book.getStatus())) {
                book.setStatus("OVERDUE");
                issuedBooksRepo.save(book);
            }
        }

        log.info("Defaulter status update job completed. {} new defaulters identified.", defaulterUserIds.size());
    }
}
