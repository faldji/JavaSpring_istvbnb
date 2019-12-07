package com.istv.progcomp.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.istv.progcomp.model.UserEntity;
import com.istv.progcomp.data.UserRepository;
import com.istv.progcomp.service.imp.UserRegisterImpl;
import com.istv.progcomp.form.exception.UniqueUserException;
import com.istv.progcomp.form.UserForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Errors;
import java.nio.charset.StandardCharsets;

@Service
public class UserRegisterServ implements UserRegisterImpl{
    @Autowired
    private UserRepository repository;
    private boolean emailExists(String email) {
        return repository.findUserEntityByEmail(email) != null;
    }
    private boolean usernameExists(String username) {
        return repository.findUserEntityByUsername(username) != null;
    }
    @Transactional
    @Override
    public UserEntity createUser(UserForm userForm, Errors errors) throws UniqueUserException {
        if (usernameExists(userForm.getUsername()))
            errors.rejectValue("username","message.regUserError");
        if (emailExists(userForm.getEmail()))
            errors.rejectValue("email","message.regEmailError");
        if (errors.hasErrors()) {
            if (errors.hasGlobalErrors())
                errors.rejectValue("matchingPassword", "message.regPasswordError");
            return null;
        }
        UserEntity user = new UserEntity();
        user.setUsername(userForm.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(userForm.getPassword()));
        user.setEmail(userForm.getEmail());
        String[] roleVal;
        if (userForm.getRole().length == 2)
            roleVal = new String[]{"ROLE_ADMIN","ROLE_USER"};
        else
            roleVal = userForm.getRole()[0] == 0 ?  new String[]{"ROLE_ADMIN"}: new String[]{"ROLE_USER"};
        user.setRoles(roleVal);
        return repository.save(user);
    }
    public String awsLambdaConvert(String toConvert) throws JsonProcessingException {
        if (!toConvert.matches("-?\\d+"))
            return null;
        LambdaJsonOutput res;
        Regions region = Regions.fromName(Regions.US_EAST_1.getName());
        ObjectMapper mapper = new ObjectMapper();
        BasicSessionCredentials credentials = new BasicSessionCredentials("ASIA2URZLTAVLTI2GHFL",
                "0uZS8sFTvVyHx/JOzdwOc+NXkfJ5/sui8+7FtkFp",
                "FwoGZXIvYXdzECYaDMgDn/YzexmyZSaBgCLSASeT2NxkBieDi7k3rMlpEZmskhU6KUv6ogd32C38D4tsQM2Wsnls+" +
                        "HOBt99iRxwNW0sP35+wDi2bEIn+CjA9FmUWzI6gAM+jaBpjnmV6kcA6owNIE/Zed2HB2rnOLL1tOd8JuNwOS1Jqfx4/" +
                        "v6lWDz7JcfBscOIiMaV+1248lDYirRHrYwmfKGcTY0bS5ijBDp0kRif7FAdce3YSZfn3Y" +
                        "VuszRiUTPoL2u3KVcPz8MqJE0tNoj2NB/GuTBCDHkngUjckcjQvs5cg8/Hgg+" +
                        "RndVuwECj86aPvBTItliciABURbYvdoW03NjQ8h8gjpIGBBNFNWoFjfs9PrEZCY3MeqyGu9z+lbqCQ");
        AWSLambdaClientBuilder builder = AWSLambdaClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(region);
        AWSLambda client = builder.build();
        InvokeRequest req = new InvokeRequest().withFunctionName("convertEuroXof").withPayload("{\"input\": "+toConvert+"}");
        InvokeResult result = client.invoke(req);
        res = mapper.readValue(StandardCharsets.UTF_8.decode(result.getPayload()).toString(),LambdaJsonOutput.class);
        if (res.statusCode.equals("200"))
            return res.body;
        return null;
    }

    public static class  LambdaJsonOutput{
        private String statusCode;
        private String body;

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }
}
