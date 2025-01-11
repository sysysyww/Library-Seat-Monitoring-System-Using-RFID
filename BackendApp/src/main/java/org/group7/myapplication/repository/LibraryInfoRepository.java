package org.group7.myapplication.repository;

import org.group7.myapplication.entity.LibraryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


public interface LibraryInfoRepository extends JpaRepository<LibraryInfo, Long>, JpaSpecificationExecutor<LibraryInfo> {
}
