package hu.vk.model;

import com.google.common.collect.Sets;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Set;

/**
 * Represent an actual game state
 */
@Data
public class Game {

    @Id
    private String id;;

    private String name;

    private Integer rows;

    private Integer columns;

    private Set<Point> livePoints = Sets.newHashSet();

    private Integer generation;


    public boolean isLive(int x, int y) {

        return livePoints.contains(new Point() {{
            setX(x);
            setY(y);
        }});
    }

    public void addLivePoint(int x, int y) {
        livePoints.add(new Point(){{
            setX(x);
            setY(y);
        }});
    }


}
