package com.codegym.lesson456.service;

import com.codegym.lesson456.model.BookLoan;
import com.codegym.lesson456.model.LibraryBook;
import com.codegym.lesson456.repository.BookLoanRepository;
import com.codegym.lesson456.repository.LibraryBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookService {
    private final LibraryBookRepository books;
    private final BookLoanRepository loans;
    public BookService(LibraryBookRepository books, BookLoanRepository loans) { this.books = books; this.loans = loans; }

    @Transactional public List<LibraryBook> findAll() {
        if (books.count() == 0) {
            books.save(new LibraryBook("Dế Mèn phiêu lưu ký", "Tô Hoài", 3));
            books.save(new LibraryBook("Tắt đèn", "Ngô Tất Tố", 2));
            books.save(new LibraryBook("Nhà giả kim", "Paulo Coelho", 4));
        }
        return books.findAll();
    }
    @Transactional(readOnly = true) public LibraryBook findBook(Long id) {
        LibraryBook book = books.findById(id);
        if (book == null) throw new BookException("Không tìm thấy đầu sách.");
        return book;
    }
    @Transactional public BookLoan borrow(Long id) {
        LibraryBook book = books.findById(id);
        if (book == null) throw new BookException("Không tìm thấy đầu sách.");
        if (book.getAvailableCopies() < 1) throw new BookException("Sách này hiện đã được mượn hết.");
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        String code;
        do { code = String.format("%05d", ThreadLocalRandom.current().nextInt(100000)); }
        while (loans.findActiveByCode(code) != null);
        return loans.save(new BookLoan(book, code));
    }
    @Transactional public LibraryBook giveBack(String code) {
        if (code == null || !code.matches("\\d{5}")) throw new BookException("Mã mượn phải gồm đúng 5 chữ số.");
        BookLoan loan = loans.findActiveByCode(code);
        if (loan == null) throw new BookException("Mã mượn không hợp lệ hoặc sách đã được trả.");
        loan.setReturnedAt(LocalDateTime.now());
        LibraryBook book = loan.getBook();
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        return book;
    }
}
