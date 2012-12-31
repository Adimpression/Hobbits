package ai.ilikeplaces.entities;


import ai.ilikeplaces.entities.etc.*;
import ai.scribble.License;
import ai.scribble._note;
import ai.scribble._unidirectional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * These are the people needed for an event
 * <p/>
 * <p/>
 * User: <a href="http://www.ilikeplaces.com"> http://www.ilikeplaces.com </a>
 * Date: Dec 8, 2009
 * Time: 9:54:03 PM
 */

@License(content = "This code is licensed under GNU AFFERO GENERAL PUBLIC LICENSE Version 3")
@Entity
@NamedQueries(
        {
                @NamedQuery(
                        name = "FindHumansNetPeoplesWhoHaveMeAsAFriend",
                        query = "SELECT humansNetPeople FROM HumansNetPeople humansNetPeople, HumansNetPeople humansNetPeople3 WHERE humansNetPeople3.humanId = :humanId AND humansNetPeople3 MEMBER OF humansNetPeople.humansNetPeoples"//Don't ask me how I did it. It took me hours! Weather wasn't ideal either
                )
        }
)
public class HumansNetPeople extends HumanEquals implements HumansFriend, HumanIdFace, Serializable {
    public String humanId;
    public final static String humanIdCOL = "humanId";
    public HumansNet humansNet;
    public List<HumansNetPeople> humansNetPeoples;
    private static final String HUMANS_NET_PEOPLE = "HumansNetPeople{";
    private static final String HUMAN_ID = "humanId='";
    private static final char CHAR = '}';
    private static final char BACKSLASH = '\'';

    public final static String FindHumansNetPeoplesWhoHaveMeAsAFriend = "FindHumansNetPeoplesWhoHaveMeAsAFriend";

    @Id
    public String getHumanId() {
        return humanId;
    }

    public void setHumanId(final String humanId__) {
        this.humanId = humanId__;
    }

    @OneToOne(cascade = CascadeType.REFRESH)
    @PrimaryKeyJoinColumn
    public HumansNet getHumansNet() {
        return humansNet;
    }

    public void setHumansNet(HumansNet humansNet) {
        this.humansNet = humansNet;
    }

    @_note(note = "This implementation will be fast a.l.a the Human entity has lazy in its getters.")
    @Override
    @Transient
    public String getDisplayName() {
        return this.getHumansNet().getDisplayName();
    }
    @Override
    @Transient
    public boolean ifFriend(final String friendsHumanId) {
        return FriendUtil.checkIfFriend(new HumanId(this.humanId), new HumanId(friendsHumanId)).returnValueBadly();
    }

    @Override
    public boolean notFriend(final String friendsHumanId) {
        return !ifFriend(friendsHumanId);
    }

    @_note(note = "MANY IS THE OWNING SIDE, HENCE REFRESH. SINCE THIS IS SELF REFERENTIAL, A REFRESH WITH SELF SHOULD NOT HAPPEN.")
    @_unidirectional(note = "Asymmetric Relationship")
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
    public List<HumansNetPeople> getHumansNetPeoples() {
        return humansNetPeoples;
    }

    public void setHumansNetPeoples(final List<HumansNetPeople> humansNetPeoples) {
        this.humansNetPeoples = humansNetPeoples;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (getClass() == o.getClass()) {
            final HumansNetPeople that = (HumansNetPeople) o;
            return (!(this.getHumanId() == null || that.getHumanId() == null)) && this.getHumanId().equals(that.getHumanId());
        } else {
            return matchHumanId(o);
        }
    }

    @Override
    public int hashCode() {
        return humanId != null ? humanId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return HUMANS_NET_PEOPLE +
                HUMAN_ID + humanId + BACKSLASH +
                CHAR;
    }
}
