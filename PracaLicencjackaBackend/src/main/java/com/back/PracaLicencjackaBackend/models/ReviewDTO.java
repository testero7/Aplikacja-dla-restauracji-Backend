package com.back.PracaLicencjackaBackend.models;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private Integer userId;
    private Integer productId;
    private String description;
}
