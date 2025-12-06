package cl.ufro.dci.cardiocare.dashboard.domain;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "dashboard_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String widgetName;

    @Column(length = 3000)
    private String dataJson;

    private Long ownerId;
}
