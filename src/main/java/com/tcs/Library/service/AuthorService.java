package com.tcs.Library.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.tcs.Library.entity.Author;
import com.tcs.Library.entity.Book;
import com.tcs.Library.repository.AuthorRepo;
import com.tcs.Library.dto.AuthorSignUp;
import com.tcs.Library.dto.wrapper.AuthorMapper;
import com.tcs.Library.error.NoAuthorFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepo authorRepo;

    public List<Book> getByAuthorId(Long id) {
        Author author = authorRepo.findById(id)
                .orElseThrow(() -> new NoAuthorFoundException("No Author for ID: " + id));
        return author.getBook();
    }

    public Author registerAuthor(AuthorSignUp dto) {
        Author author = AuthorMapper.toEntity(dto);
        return authorRepo.save(author);
    }
}
