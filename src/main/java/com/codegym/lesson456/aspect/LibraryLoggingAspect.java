package com.codegym.lesson456.aspect;

import com.codegym.lesson456.model.BookLoan;
import com.codegym.lesson456.model.LibraryBook;
import com.codegym.lesson456.service.BookService;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicLong;

@Aspect
@Component
public class LibraryLoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LibraryLoggingAspect.class);
    private final AtomicLong visits = new AtomicLong();

    @After("execution(* com.codegym.lesson456.controller.BookController.*(..)) && !execution(* com.codegym.lesson456.controller.BookController.handleBookError(..))")
    public void logVisit() { log.info("Library visit #{} at {}", visits.incrementAndGet(), LocalDateTime.now()); }

    @AfterReturning(pointcut = "execution(* com.codegym.lesson456.service.BookService.borrow(..))", returning = "loan")
    public void logBorrow(BookLoan loan) {
        log.info("BOOK BORROWED | bookId={} | title={} | code={} | remaining={} | at={}",
                loan.getBook().getId(), loan.getBook().getTitle(), loan.getCode(), loan.getBook().getAvailableCopies(), LocalDateTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.codegym.lesson456.service.BookService.giveBack(..))", returning = "book")
    public void logReturn(LibraryBook book) {
        log.info("BOOK RETURNED | bookId={} | title={} | remaining={} | at={}",
                book.getId(), book.getTitle(), book.getAvailableCopies(), LocalDateTime.now());
    }
}
