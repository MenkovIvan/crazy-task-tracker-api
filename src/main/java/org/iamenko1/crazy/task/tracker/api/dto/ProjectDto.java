package org.iamenko1.crazy.task.tracker.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDto {

    @NonNull
    private Long id;

    @NonNull
    private String name;

    @NonNull
    @JsonProperty("updated_at")
    private Date updatedAt;

    @NonNull
    @JsonProperty("created_at")
    private Date createdAt;
}
