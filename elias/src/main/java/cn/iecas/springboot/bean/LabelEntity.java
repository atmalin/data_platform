package cn.iecas.springboot.bean;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class LabelEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String labelType;

    @Column(nullable = false, length = 20)
    private String labelInfo;

}
