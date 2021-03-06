package com.doitutpl.doit.Controllers;

import android.content.Context;
import android.content.Intent;

import androidx.annotation.NonNull;

import com.doitutpl.doit.Models.Chats;
import com.doitutpl.doit.Models.Group;
import com.doitutpl.doit.Models.Mensaje;
import com.doitutpl.doit.StaticData;
import com.doitutpl.doit.chats.chat;
import com.doitutpl.doit.chats.listGroup;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatsController {

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    public ChatsController (){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }


    public void sendMessage (final Context context, final String keyChat, final Mensaje mensaje, String memberEmail){
        // Obtenemos la conexión
        final DatabaseReference databaseReference = Connection.initializeFirebase(context).child(StaticData.CHATS_NODE_TITLE);

        //Aqui se selecciona el Chat al cual ira el mensaje
        databaseReference.child(keyChat).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("ENTRO");
                DatabaseReference newMemberReference = databaseReference.child(keyChat).push(); // Obtenemos la referencia para agregar el nuevo miembro
                newMemberReference.setValue(mensaje);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void startChatActivity(Context context){

        Intent intent = new Intent(context, chat.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
