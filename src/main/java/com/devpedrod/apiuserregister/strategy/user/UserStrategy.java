package com.devpedrod.apiuserregister.strategy.user;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.dto.response.FieldMessage;
import com.devpedrod.apiuserregister.exceptions.MethodArgumentNotValidException;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import com.devpedrod.apiuserregister.utils.CpfValidator;
import com.devpedrod.apiuserregister.utils.NameValidator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserConstraints implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        List<FieldMessage> fieldMessages = new ArrayList<>();
        if (domainEntity instanceof User user) {
            user.setName(user.getName().trim());
            user.setCpf(user.getCpf().trim().replaceAll("[.-]", ""));
            if(user.getAddress() != null){
                user.getAddress().setUser(user);
            }
            if (!CpfValidator.isValidCPF(user.getCpf())){
                fieldMessages.add(new FieldMessage("CPF","CPF ínvalido"));
            }
            if (!NameValidator.validateName(user.getName())) {
                fieldMessages.add(new FieldMessage("Nome","Nome ínvalido, use somente letras."));
            }
            if (!fieldMessages.isEmpty()){
                throw new MethodArgumentNotValidException(fieldMessages);
            }
            return user;
        }
        return null;
    }
}
