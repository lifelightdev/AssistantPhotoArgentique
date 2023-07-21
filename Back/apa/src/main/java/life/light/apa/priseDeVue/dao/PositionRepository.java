package life.light.apa.priseDeVue.dao;

import life.light.apa.priseDeVue.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PositionRepository extends JpaRepository<Position, Long> {
    @Query("select p from Position p")
    List<Position> findAll();
}
