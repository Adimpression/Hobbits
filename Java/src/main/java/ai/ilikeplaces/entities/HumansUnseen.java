package ai.ilikeplaces.entities;


import ai.scribble.WARNING;
import ai.scribble._unidirectional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Ravindranath Akila
 * Date: 10/12/11
 * Time: 7:47 PM
 */
@WARNING("THIS ENTITY IS NOT GUARANTEED TO 'BE' EVEN THOUGH A HUMAN IS SIGNED UP. SO CREATE IT IF NOT PRESENT!")
@Entity
public class HumansUnseen implements Serializable {
    public String humanId;
    public List<Wall> unseenWalls;

    @Id
    public String getHumanId() {
        return humanId;
    }

    public void setHumanId(final String humanId) {
        this.humanId = humanId;
    }

    @Transient
    public HumansUnseen setHumanIdR(final String humanId) {
        this.humanId = humanId;
        return this;
    }

    @_unidirectional
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    public List<Wall> getUnseenWalls() {
        return unseenWalls;
    }

    public void setUnseenWalls(final List<Wall> unseenWalls) {
        this.unseenWalls = unseenWalls;
    }
}
