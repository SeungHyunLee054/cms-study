package com.zerobase.cms.user.service.customer;

import com.zerobase.cms.user.domain.customer.ChangeBalanceForm;
import com.zerobase.cms.user.domain.model.CustomerBalanceHistory;
import com.zerobase.cms.user.exception.CustomException;
import org.springframework.transaction.annotation.Transactional;

public interface CustomerBalanceService {
    @Transactional(noRollbackFor = CustomException.class)
    CustomerBalanceHistory changeBalance(Long customerId, ChangeBalanceForm form) throws CustomException;
}
