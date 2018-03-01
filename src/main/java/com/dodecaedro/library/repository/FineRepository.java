package com.dodecaedro.library.repository;

import com.dodecaedro.library.pojo.Borrow;
import com.dodecaedro.library.pojo.Fine;
import com.dodecaedro.library.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface FineRepository extends JpaRepository<Fine, Long> {
  Fine findTopByUserOrderByFineEndDateDesc(User user);
  Fine save(Fine fine);
  @Query("from Fine f where f.user = :user and :time BETWEEN f.fineStartDate and f.fineEndDate")
  List<Fine> findActiveFinesInDate(@Param("user") User user, @Param("time") ZonedDateTime time);
  @Query("from Fine f left join f.user u left join u.borrows b where b = :borrow")
  Fine findByBorrow(@Param("borrow") Borrow borrow);
}
