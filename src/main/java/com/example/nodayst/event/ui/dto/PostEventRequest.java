package com.example.nodayst.event.ui.dto;

import com.example.nodayst.event.domain.ReviewEventAction;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;



@Getter
@AllArgsConstructor
public class PostEventRequest {
    @NotBlank
    private String type;
    @NotNull
    private ReviewEventAction action;
    @NotBlank
    private String reviewId;

    private String content;

    private Set<String> attachedPhotoIds;
    @NotBlank
    private String userId;
    @NotBlank
    private String placeId;

}
