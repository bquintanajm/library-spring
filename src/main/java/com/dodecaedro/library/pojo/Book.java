package com.dodecaedro.library.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.List;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

@Data
@EqualsAndHashCode(of = {"isbn"})
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "book")
public class Book implements Serializable {
  @Id
  @NotNull
  @Column(name = "id")
  @SequenceGenerator(name = "bookSeq", sequenceName = "S_BOOK", allocationSize = 1)
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bookSeq")
  private Long bookId;

  @NotNull
  @Column
  private String title;

  @NotNull
  @Column
  private String isbn;

  @NotNull
  @Column(name = "bought_date")
  private ZonedDateTime dateTimeBought;

  @OneToMany
  @JoinColumn(name = "id",insertable = false, updatable = false)
  @JsonIgnoreProperties("book")
  private List<Borrow> borrows;

  @PrePersist
  void createdAt() {
    if (this.dateTimeBought == null) {
      this.dateTimeBought = ZonedDateTime.now();
    }
  }
}
