package org.example.hackentestapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Objects;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CSVField {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String value;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "record_id")
    private CSVRecord record;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
