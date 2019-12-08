package com.doitutpl.doit.Controllers;

import com.doitutpl.doit.Models.Member;
import com.doitutpl.doit.Models.User;
import com.google.firebase.auth.FirebaseUser;

public class MembersController {


    // Metodo para obtener un objeto del tipo miembro a partir de un objeto dedl tipo usuario
    public Member parseMember(User user){
        Member member = new Member();

        member.setEmail(user.getEmail());

        return member;
    }
    public Member parseMember(FirebaseUser user){
        Member member = new Member();

        member.setEmail(user.getEmail());
        return member;

    }


}
