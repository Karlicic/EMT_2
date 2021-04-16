package emt.finki.ukim.mk.demo.service.impl;

import emt.finki.ukim.mk.demo.model.Author;
import emt.finki.ukim.mk.demo.model.Book;
import emt.finki.ukim.mk.demo.model.dto.BookDto;
import emt.finki.ukim.mk.demo.model.enumerations.Category;
import emt.finki.ukim.mk.demo.model.exceptions.AuthorNotFoundException;
import emt.finki.ukim.mk.demo.model.exceptions.BookNotFoundException;
import emt.finki.ukim.mk.demo.model.exceptions.NoMoreAvailableCopiesException;
import emt.finki.ukim.mk.demo.repository.AuthorRepository;
import emt.finki.ukim.mk.demo.repository.BookRepository;
import emt.finki.ukim.mk.demo.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public List<Book> findAll() {
        return this.bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return this.bookRepository.findById(id);
    }

    @Override
    public Optional<Book> save(String name, Category category, Author author, Integer availableCopies) {

        this.authorRepository.findById(author.getId())
                .orElseThrow(()->new AuthorNotFoundException(author.getId()));
        //?
        this.bookRepository.deleteByName(name);
        return Optional.of(this.bookRepository.save(new Book(name, category, author, availableCopies)));
    }

    @Override
    public Optional<Book> save(BookDto bookDto) {

        Author author= this.authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(()->new AuthorNotFoundException(bookDto.getAuthorId()));

        this.bookRepository.deleteByName(bookDto.getName());

        Book book = new Book(bookDto.getName(), bookDto.getCategory(), author, bookDto.getAvailableCopies());

        return Optional.of(book);

    }

    @Override
    public Optional<Book> edit(Long id, String name, Category category, Author author, Integer availableCopies) {

        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        book.setName(name);
        book.setCategory(category);
        book.setAuthor(author);
        book.setAvailableCopies(availableCopies);

        this.bookRepository.save(book);

        return Optional.of(book);
    }

    @Override
    public Optional<Book> edit(Long id, BookDto bookDto) {

        Book book = this.bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        Author author= this.authorRepository.findById(bookDto.getAuthorId())
                .orElseThrow(()->new AuthorNotFoundException(bookDto.getAuthorId()));

        book.setName(bookDto.getName());
        book.setCategory(bookDto.getCategory());
        book.setAuthor(author);
        book.setAvailableCopies(bookDto.getAvailableCopies());

        this.bookRepository.save(book);

        return Optional.of(book);
    }

    @Override
    public void deleteById(Long id) {
        this.bookRepository.deleteById(id);
    }

    @Override
    public Optional<Book> markAsTaken(Long id) {

        Book book = this.bookRepository.findById(id).orElseThrow(()->new BookNotFoundException(id));
        Integer availableCopies = book.getAvailableCopies();
        if(availableCopies<1){
            throw new NoMoreAvailableCopiesException(id);
        }
        availableCopies--;
        book.setAvailableCopies(availableCopies);

        return Optional.of(book);
    }

}
