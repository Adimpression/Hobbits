package ai.ilikeplaces.entities;


import ai.ilikeplaces.entities.etc.*;
import ai.reaver.GetMailAddress;
import ai.scribble.License;
import ai.scribble._bidirectional;
import ai.scribble._note;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: <a href="http://www.ilikeplaces.com"> http://www.ilikeplaces.com </a>
 * Date: Jan 12, 2010
 * Time: 10:26:37 PM
 */

@License(content = "This code is licensed under GNU AFFERO GENERAL PUBLIC LICENSE Version 3")
@Entity
public class HumansPrivateEvent extends HumanEquals implements HumanPkJoinFace,HumansFriend, GetMailAddress, HumanIdFace, Serializable {
// ------------------------------ FIELDS ------------------------------

    public String humanId;

    public Human human;

    public List<PrivateEvent> privateEventsOwned;
    public List<PrivateEvent> privateEventsViewed;
    public List<PrivateEvent> privateEventsInvited;
    public List<PrivateEvent> privateEventsRejected;

// ------------------------ ACCESSORS / MUTATORS ------------------------

    @OneToOne(cascade = CascadeType.REFRESH)
    @PrimaryKeyJoinColumn
    public Human getHuman() {
        return human;
    }

    public void setHuman(final Human human) {
        this.human = human;
    }

    @Id
    public String getHumanId() {
        return humanId;
    }

    public void setHumanId(final String humanId__) {
        this.humanId = humanId__;
    }

    @_bidirectional(ownerside = _bidirectional.OWNING.NOT)
    @ManyToMany(mappedBy = PrivateEvent.privateEventInvitesCOL, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public List<PrivateEvent> getPrivateEventsInvited() {
        return privateEventsInvited;
    }

    public void setPrivateEventsInvited(List<PrivateEvent> privateEventsInvited) {
        this.privateEventsInvited = privateEventsInvited;
    }

    @_bidirectional(ownerside = _bidirectional.OWNING.NOT)
    @ManyToMany(mappedBy = PrivateEvent.privateEventOwnersCOL, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public List<PrivateEvent> getPrivateEventsOwned() {
        return privateEventsOwned;
    }

    public void setPrivateEventsOwned(List<PrivateEvent> privateEventsOwned) {
        this.privateEventsOwned = privateEventsOwned;
    }

    @_bidirectional(ownerside = _bidirectional.OWNING.NOT)
    @ManyToMany(mappedBy = PrivateEvent.privateEventRejectsCOL, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public List<PrivateEvent> getPrivateEventsRejected() {
        return privateEventsRejected;
    }

    public void setPrivateEventsRejected(List<PrivateEvent> privateEventsRejected) {
        this.privateEventsRejected = privateEventsRejected;
    }

    @_bidirectional(ownerside = _bidirectional.OWNING.NOT)
    @ManyToMany(mappedBy = PrivateEvent.privateEventViewersCOL, cascade = CascadeType.REFRESH, fetch = FetchType.LAZY)
    public List<PrivateEvent> getPrivateEventsViewed() {
        return privateEventsViewed;
    }

    public void setPrivateEventsViewed(List<PrivateEvent> privateEventsViewed) {
        this.privateEventsViewed = privateEventsViewed;
    }

// ------------------------ INTERFACE METHODS ------------------------


// --------------------- Interface GetMailAddress ---------------------

    /**
     * @return Email Address
     */
    @Override
    public String email() {
        return this.getHumanId();
    }

// --------------------- Interface HumansFriend ---------------------

    @_note(note = "This implementation will be fast a.l.a the Human entity has lazy in its getters.")
    @Override
    @Transient
    public String getDisplayName() {
        return this.human.getHumansNet().getDisplayName();
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

// ------------------------ CANONICAL METHODS ------------------------

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;

        if (o == null) return false;

        if (getClass() == o.getClass()) {
            final HumansPrivateEvent that = (HumansPrivateEvent) o;
            return (!(this.getHumanId() == null || that.getHumanId() == null)) && this.getHumanId().equals(that.getHumanId());
        } else {
            return matchHumanId(o);
        }
    }

// -------------------------- STATIC METHODS --------------------------
}
