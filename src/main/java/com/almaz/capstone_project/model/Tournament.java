package com.almaz.capstone_project.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "Please provide the name.")
    private String name;
    @NotEmpty(message = "Please provide the description.")
    private String description;
    @NotEmpty(message = "Please select the start date.")
    private LocalDate startDate;
    @NotEmpty(message = "Please select the end date.")
    private LocalDate endDate;
    @NotEmpty(message = "Please provide the location.")
    private String location;

    @ManyToMany(cascade={CascadeType.PERSIST ,CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinTable(
            name="tournament_category",
            joinColumns = @JoinColumn(name="tournament_id"),
            inverseJoinColumns = @JoinColumn(name="category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy="tournament", cascade= CascadeType.ALL, orphanRemoval = true)
    private List<Registration> registrations = new ArrayList<>();

    public void addCategory(Category category) {
        categories.add(category);
    }

    public void addRegistration(Registration reg) {
        registrations.add(reg);
    }

    public void removeRegistration(Registration reg) {
        if (registrations != null)
            registrations.remove(reg);
    }
}
