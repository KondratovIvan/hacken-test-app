package org.example.hackentestapp.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSVRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(description = "CSV record id in DB")
    private Long id;

    @OneToMany(mappedBy = "record", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private Set<CSVField> fields;

}
