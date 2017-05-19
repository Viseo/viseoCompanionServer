package com.viseo.companion.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by HEL3666 on 19/05/2017.
 */
public class CommentaireConverter {
    static private long NULL = -1;
    static private long NEW = 0;

    public CommentaireDTO getDTO (Commentaire commentaire) {
        CommentaireDTO dto = new CommentaireDTO();
        dto.setId(commentaire.getId());
        dto.setDatetime(commentaire.getDatetime());
        dto.setEvenement(commentaire.getEvenement());
        dto.setUzer(commentaire.getUzer());
        dto.setCommentaire(commentaire.getCommentaire());
        List<Commentaire> commentaires = new ArrayList<>();
        dto.setCommentaires((Set<Commentaire>) commentaires);

        Uzer uzer = commentaire.getUzer();
        if(uzer.getId() == 0){
            dto.setUzer(null);
        } else {

        }
        return dto;
    }
}
