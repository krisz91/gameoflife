package hu.vk.model;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * Represent an actual game state
 */
@Data
public class Game implements Cloneable {

    private Integer rows;

    private Integer columns;

    private Set<LivePoint> points = Sets.newHashSet();

    private Integer generation;


    public boolean isLive(int x, int y) {

        return points.contains(new LivePoint() {{
            setX(x);
            setY(y);
        }});
    }

    public void addLivePoint(int x, int y) {
        points.add(new LivePoint(){{
            setX(x);
            setY(y);
        }});
    }


}
