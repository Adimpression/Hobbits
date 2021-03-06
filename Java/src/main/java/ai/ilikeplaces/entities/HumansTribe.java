package ai.ilikeplaces.entities;


import ai.ilikeplaces.entities.etc.*;
import ai.scribble.WARNING;
import ai.scribble._bidirectional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: Ravindranath Akila
 * Date: 10/12/11
 * Time: 7:47 PM
 */
@WARNING("THIS ENTITY IS NOT GUARANTEED TO 'BE' EVEN THOUGH A HUMAN IS SIGNED UP. SO CREATE IT IF NOT PRESENT!")
@Entity
public class HumansTribe implements HumansFriend, HumanIdFace, HumanEqualsFace , Serializable {
    public String humanId;

    public Set<Tribe> tribes;

    @Id
    public String getHumanId() {
        return humanId;
    }



    public void setHumanId(final String humanId) {
        this.humanId = humanId;
    }


    @_bidirectional(ownerside = _bidirectional.OWNING.NOT)
    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public Set<Tribe> getTribes() {
        return tribes;
    }

    public void setTribes(final Set<Tribe> tribes) {
        this.tribes = tribes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null) {
            return false;
        }

        if (getClass() == o.getClass()) {
            final HumanEqualsFace that = (HumanEqualsFace) o;
            return !(this.getHumanId() == null || that.getHumanId() == null) && this.getHumanId().equals(that.getHumanId());
        } else {
            return HumanEquals.staticMatchHumanId(this, o);
        }
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface HumansFriend ---------------------

    @Override
    @Transient
    public String getDisplayName() {
        return FriendUtil.getCachedHuman(getHumanId()).getHuman().getDisplayName();
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

// -------------------------- OTHER METHODS --------------------------

    @Transient
    public HumansTribe setHumanIdR(final String humanId) {
        this.humanId = humanId;
        return this;
    }
}

