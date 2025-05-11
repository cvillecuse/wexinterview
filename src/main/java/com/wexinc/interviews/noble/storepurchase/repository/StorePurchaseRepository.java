package com.wexinc.interviews.noble.storepurchase.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wexinc.interviews.noble.storepurchase.entities.StorePurchase;

public interface StorePurchaseRepository extends JpaRepository<StorePurchase, Long> {

}
