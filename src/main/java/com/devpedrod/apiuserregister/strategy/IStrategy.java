package com.devpedrod.apiuserregister.strategy;

import com.devpedrod.apiuserregister.domain.DomainEntity;

public interface IStrategy {
    DomainEntity applyBusinessRule(DomainEntity domainEntity);
}
