package com.example.nodayst.event.infra;

import com.example.nodayst.event.domain.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, String> {

}
