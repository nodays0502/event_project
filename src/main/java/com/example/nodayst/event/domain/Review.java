package com.example.nodayst.event.domain;

import com.example.nodayst.common.model.Image;
import java.util.Set;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Review {

    @Id
    @Column(name = "REVIEW_ID")
    private String id;
    @Lob
    private String content;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "image", joinColumns = @JoinColumn(name = "REVIEW_ID"))
    private Set<Image> images;

    private String userId;

    private String placeId;

}
