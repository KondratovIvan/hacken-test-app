package org.example.hackentestapp.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(description = "CSV field id in DB")
    private Long id;

    @Schema(description = "Name of CSV field.", example = "Color", requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @Schema(description = "Value of CSV field.", example = "Red", requiredMode = Schema.RequiredMode.REQUIRED)
    private String value;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "record_id")
    @Schema(description = "Id of field's record.")
    private CSVRecord record;

    @Override
    public int hashCode() {
        return Objects.hash(id, name, value);
    }
}
