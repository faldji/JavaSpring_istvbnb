package com.istv.progcomp.service;

import com.istv.progcomp.form.SearchForm;
import com.istv.progcomp.model.LogementEntity;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.LogementRepository;
import com.istv.progcomp.service.imp.LogementServImpl;
import com.istv.progcomp.form.LogementForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

@Service
public class LogementServ implements LogementServImpl {
    @Autowired
    private LogementRepository logementRepository;
    @Override
    public LogementEntity addNewLogement(LogementForm logementForm, UserEntity userEntity,
                                         Errors errors, MultipartFile file)  {
        if (errors.hasErrors())
            return null;
        LogementEntity logementEntity = new LogementEntity();
        String ref = "ref-"+userEntity.getUsername()+"-"+new Random(System.currentTimeMillis()).nextInt();
        logementEntity.setAddress(logementForm.getAddress());
        logementEntity.setBailleur(userEntity);
        logementEntity.setDescription(logementForm.getDescription());
        logementEntity.setHouseYear(logementForm.getHouseYear());
        logementEntity.setNbrLoc(logementForm.getNbrLoc());
        logementEntity.setEnabled(true);
        logementEntity.setPrice(logementForm.getPrice());
        logementEntity.setReference(ref);
        logementEntity.setSurface(logementForm.getSurface());
        logementEntity.setType(logementForm.getType());
        return logementRepository.save(logementEntity);

    }
    @Override
    public void store(MultipartFile file,Path imageLocation) {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            if (file.isEmpty()) {
                throw new RuntimeException("Failed to store empty file " + filename);
            }
            if (filename.contains("..")) {
                // This is a security check
                throw new RuntimeException(
                        "Cannot store file with relative path outside current directory "
                                + filename);
            }
            try (InputStream inputStream = file.getInputStream()) {
                if (Files.isWritable(imageLocation.resolve(filename)))
                    Files.copy(inputStream, imageLocation.resolve(filename),
                        StandardCopyOption.REPLACE_EXISTING);
                else {
                    System.out.println("no writtable");
                    Files.write(imageLocation.resolve(filename),file.getBytes());
                }

            }
        }
        catch (IOException e) {
            throw new RuntimeException("Failed to store file " + filename, e);
        }
    }

    public LogementForm handleEdit(long edit) {
        LogementForm logementForm= new LogementForm();
        LogementEntity logementEntity = logementRepository.findLogementEntityById(edit);
        if (logementEntity == null)
            return null;
        logementForm.setAddress(logementEntity.getAddress());
        logementForm.setDescription(logementEntity.getDescription());
        logementForm.setHouseYear(logementEntity.getHouseYear());
        logementForm.setNbrLoc(logementEntity.getNbrLoc());
        logementForm.setPrice(logementEntity.getPrice());
        logementForm.setSurface(logementEntity.getSurface());
        logementForm.setType(logementEntity.getType());
        return logementForm;
    }

    @Override
    public LogementEntity editLogement(LogementForm logementForm, long edit, Errors errors) {
        LogementEntity logementEntity = logementRepository.findLogementEntityById(edit);
        if (logementEntity == null || errors.hasErrors())
            return null;
        logementEntity.setAddress(logementForm.getAddress());
        logementEntity.setDescription(logementForm.getDescription());
        logementEntity.setHouseYear(logementForm.getHouseYear());
        logementEntity.setNbrLoc(logementForm.getNbrLoc());
        logementEntity.setPrice(logementForm.getPrice());
        logementEntity.setSurface(logementForm.getSurface());
        logementEntity.setType(logementForm.getType());
        return logementRepository.save(logementEntity);
    }
    public Collection<LogementEntity> search(SearchForm searchForm){
        Collection<LogementEntity> loadedValue = logementRepository.
                findLogementEntitiesByAddressContainingIgnoreCaseAndPriceIsGreaterThanEqualAndHouseYearIsGreaterThanEqualAndEnabledIsTrue(searchForm.getLocation(),searchForm.getMaxPrice(),searchForm.getAvailableDate());
        if (loadedValue ==null){
            System.out.println("no pe");
            return new  ArrayList<LogementEntity>();}
        System.out.println("not null");
        loadedValue.forEach(logementEntity -> System.out.println(logementEntity.getReference()));
        return loadedValue;
    }
}
