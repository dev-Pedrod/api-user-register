package com.devpedrod.apiuserregister.strategy.user;

import com.devpedrod.apiuserregister.domain.DomainEntity;
import com.devpedrod.apiuserregister.domain.User;
import com.devpedrod.apiuserregister.strategy.IStrategy;
import com.devpedrod.apiuserregister.utils.CpfValidator;
import com.devpedrod.apiuserregister.utils.NameValidator;
import org.springframework.stereotype.Service;

@Service
public class UserRules implements IStrategy {
    @Override
    public DomainEntity applyBusinessRule(DomainEntity domainEntity) {
        if (domainEntity instanceof User user) {
            user.setName(user.getName().trim());
            user.setCpf(user.getCpf().trim().replaceAll("[.-]", ""));

            if (!CpfValidator.isValidCPF(user.getCpf())){
                // TODO: CPF Exception
                System.out.println("CPF INVALIDO");
            } if (!NameValidator.validateName(user.getName())) {
                // TODO: Name Exception
                System.out.println("NOME INVALIDO");
            }
            return user;
        }
        return null;
    }
}
