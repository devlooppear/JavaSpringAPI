package com.javaApi.javaApi.repository;

import com.javaApi.javaApi.model.PersonalAccessToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonalAccessTokenRepository extends JpaRepository<PersonalAccessToken, Long> {
    @Modifying
    @Query("DELETE FROM PersonalAccessToken pat WHERE pat.user.id = :userId")
    void deleteByUserId(@Param("userId") Long userId);
    PersonalAccessToken findByToken(String token);
}
