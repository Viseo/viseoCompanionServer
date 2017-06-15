package com.viseo.companion.converter;
import com.viseo.companion.domain.Notation;
import com.viseo.companion.dto.NotationDTO;


public class NotationConverter {

    static private long NULL = -1;
    static private long NEW = 0;

    public NotationDTO getDTO(Notation notation) {
        NotationDTO dto = new NotationDTO();
        dto.setId(notation.getId());
        dto.setAvis(notation.getAvis());
        dto.setNotation(notation.getNotation());
        dto.setVersion(notation.getVersion());

        if (notation.getUser() == null) {
            dto.setUserId(NULL);
        } else {
            dto.setUserId(notation.getUser().getId());
        }

        if (notation.getEvent() == null) {
            dto.setEventId(NULL);
        } else {
            dto.setEventId(notation.getEvent().getId());
        }
        return dto;
    }

    public void apply(NotationDTO dto, Notation notation) {
        if (dto.getId() != NEW && notation.getVersion() != dto.getVersion()) {
            throw new RuntimeException("Entity " + notation + " was updated since DTO was built.");
        }
        notation.setAvis(dto.getAvis());
        notation.setNotation(dto.getNotation());

    }
}
