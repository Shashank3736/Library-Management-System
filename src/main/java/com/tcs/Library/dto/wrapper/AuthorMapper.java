package com.tcs.Library.dto.wrapper;

import com.tcs.Library.dto.AuthorSignUp;
import com.tcs.Library.entity.Author;

public class AuthorMapper {
    private AuthorMapper() {}

    public static Author toEntity(AuthorSignUp dto) {
        if (dto == null)
            return null;
        Author user = new Author();
        user.setName(dto.getName());
        return user;
    }

}
