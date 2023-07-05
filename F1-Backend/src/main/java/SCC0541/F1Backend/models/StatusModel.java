package SCC0541.F1Backend.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity(name = "Status")
public class StatusModel {
    @Id
    @Column(name = "statusid")
    private Integer statusId;

    @Column(name = "status")
    private String status;
}
