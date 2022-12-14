package com.devpedrod.apiuserregister.strategy.user;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.dto.response.FieldMessage;
import com.devpedrod.apiuserregister.exceptions.DataIntegrityException;
import com.devpedrod.apiuserregister.exceptions.MethodArgumentNotValidException;
import com.devpedrod.apiuserregister.repositories.UserRepository;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import com.devpedrod.apiuserregister.utils.CpfValidator;
import com.devpedrod.apiuserregister.utils.NameValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserStrategy implements IStrategy {

    @Autowired
    private UserRepository userRepository;

    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        List<FieldMessage> fieldMessages = new ArrayList<>();

        if (domainEntity instanceof User user) {
            if(user.getAddress() != null){
                user.getAddress().setUser(user);
            }
            user.setName(user.getName().trim());
            user.setCpf(user.getCpf().trim().replaceAll("[.-]", ""));

            if (!CpfValidator.isValidCPF(user.getCpf())){
                fieldMessages.add(new FieldMessage("CPF","CPF ínvalido"));
            }
            if (!NameValidator.isValidName(user.getName())) {
                fieldMessages.add(new FieldMessage("Nome","Nome ínvalido, use somente letras."));
            }
            if (userRepository.findByCpf(user.getCpf()).isPresent()) {
                if (!Objects.equals(user.getId(), userRepository.findByCpf(user.getCpf()).get().getId())){
                    throw new DataIntegrityException("Este CPF já está cadastrado");
                }
            }
            if (!fieldMessages.isEmpty()){
                throw new MethodArgumentNotValidException(fieldMessages);
            }
            return user;
        }
        return null;
    }
}
