package com.keduit.helloworld.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Getter
@Setter
@ToString
public class RoomDTO {

    Long memberNum;
    int roomNumber;
    String roomName;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RoomDTO other = (RoomDTO) obj;
        if (!Objects.equals(this.roomNumber, other.roomNumber)) {
            return false;
        }
        if (!Objects.equals(this.roomName, other.roomName)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 59 * hash + Objects.hashCode(this.roomNumber);
        hash = 59 * hash + Objects.hashCode(this.roomName);
        return hash;
    }

}
