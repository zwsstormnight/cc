package com.copex.common.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.copex.common.entity.Log;

public interface LogRepository extends PagingAndSortingRepository<Log, Long> {

}
