package com.istv.progcomp.service.imp;

import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.UserEntity;
import form.LogementForm;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;

public interface LogementServImpl {
    LogementEntity addNewLogement(LogementForm logementForm, UserEntity userEntity,
                                  Errors errors, MultipartFile file) throws IOException;
    void store(MultipartFile file, Path imageLocation);
    LogementForm handleEdit(long edit);

    LogementEntity editLogement(LogementForm logementForm, long edit, Errors errors);
}
