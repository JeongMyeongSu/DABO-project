package com.ecommerce.application;

import com.ecommerce.domain.repository.entity.Record;

import java.util.List;

public interface IPurchaseRecordContractService {
    List<Record> getHistory(final String escrowContractAddress);
}
